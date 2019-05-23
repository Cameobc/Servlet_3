package com.iu.notice;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.iu.action.Action;
import com.iu.action.ActionForward;
import com.iu.page.SearchMakePage;
import com.iu.page.SearchPager;
import com.iu.page.SearchRow;
import com.iu.upload.UploadDAO;
import com.iu.upload.UploadDTO;
import com.iu.util.DBConnector;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class NoticeService implements Action {
	private NoticeDAO noticeDAO;
	private UploadDAO uploadDAO;
	public NoticeService() {
		noticeDAO = new NoticeDAO();
		uploadDAO = new UploadDAO();
	}
	@Override
	public ActionForward list(HttpServletRequest request, HttpServletResponse response) {
		ActionForward actionForward = new ActionForward();
		int curPage =1;
		try {
		curPage = Integer.parseInt(request.getParameter("curPage"));
		}catch (Exception e) {
		
		}
		String kind = request.getParameter("kind");
		String search = request.getParameter("search");
		SearchMakePage s = new SearchMakePage(curPage, kind, search);
		//1.Row
		SearchRow searchRow = s.makeRow();
		ArrayList<NoticeDTO> ar = new ArrayList<NoticeDTO>();
		try {
			ar =noticeDAO.selectList(searchRow);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//2.Page
		
		try {
			int totalCount = noticeDAO.getCountNum(searchRow);
			SearchPager searchPager =s.makePage(totalCount);
			request.setAttribute("pager", searchPager);
			request.setAttribute("list", ar);
			actionForward.setCheck(true);
			actionForward.setPath("../WEB-INF/views/notice/noticeList.jsp");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("message", "Sever Error");
			request.setAttribute("path", "../index.do");
			actionForward.setCheck(true);
			actionForward.setPath("../WEB-INF/views/common/result.jsp");

		}
		
		return actionForward;
	}
	@Override
	public ActionForward select(HttpServletRequest request, HttpServletResponse response) {  //조회를 해오는 것이라 transaction에서 제외된다. db를 수정삭제안해서
		ActionForward actionForward = new ActionForward();
		//글이 있으면 출력
		//글이 없으면 삭제되었거나 없는 글입니다.<-alert 띄우고 List로 돌아가기
		NoticeDTO noticeDTO = null;
		try {
			int num = Integer.parseInt(request.getParameter("no"));
			noticeDTO = noticeDAO.seletOne(num);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String path="";
		if(noticeDTO!=null) {
			request.setAttribute("select", noticeDTO);
			path="../WEB-INF/views/notice/noticeSelect.jsp";
		}else {
			request.setAttribute("message", "삭제되었거나 없는 글입니다.");
			request.setAttribute("path", "./noticeList");
			path="../WEB-INF/views/common/result.jsp";
		}
		actionForward.setCheck(true);
		actionForward.setPath(path);
		return actionForward;
	}
	@Override
	public ActionForward insert(HttpServletRequest request, HttpServletResponse response) {
		ActionForward actionForward = new ActionForward();
		String method = request.getMethod(); //GET, POST
		String path ="../WEB-INF/views/notice/noticeWrite.jsp";
		boolean check = true;
		
		if(method.equals("POST")) {
			NoticeDTO noticeDTO = new NoticeDTO();
			//1.request를 하나로 합치기
			//파일을저장할디스크경로C
			String saveDirectory =request.getServletContext().getRealPath("upload"); // application
			System.out.println(saveDirectory);
			int maxPostSize = 1024*1024*10; //10MB//byte단위
			String encoding ="utf-8";
			MultipartRequest multi = null;
			try {
				multi = new MultipartRequest(request, saveDirectory, maxPostSize, encoding, new DefaultFileRenamePolicy());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//파일저장이 된다.
			
			//hdd(server)에 저장된 이름
			UploadDTO uploadDTO = new UploadDTO();
			String fileName =multi.getFilesystemName("f1"); // 파일의 파라미터 이름
			String oName = multi.getOriginalFileName("f1"); // 파일의 파라미터 이름
			noticeDTO.setTitle(multi.getParameter("title"));
			noticeDTO.setContents(multi.getParameter("contents"));
			noticeDTO.setWriter(multi.getParameter("writer"));
			uploadDTO.setfName(fileName);
			uploadDTO.setoName(oName);

			int result =0;
			Connection con = null;
			try {
				int num = noticeDAO.getNum();
				con = DBConnector.getConnect();
				//auto commit 해제 -> 트랜잭션내에서는 모두 성공해야 커밋
				con.setAutoCommit(false);
				noticeDTO.setNo(num);
				result = noticeDAO.insert(noticeDTO, con);
				
				uploadDTO.setNo(num);
				result=uploadDAO.insert(uploadDTO, con);
				if(result<1) {
					throw new Exception();
				}
				con.commit();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				result=0;
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				e.printStackTrace();
			}finally {
				try {
					con.setAutoCommit(true);
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(result>0) {
				check=false;
				path="./noticeList";
			}else {
				request.setAttribute("message", "Insert Fail");
				request.setAttribute("path", "./noticeWrite");
				check=true;
				path="../WEB-INF/views/common/result.jsp";
			}//post end
		}
		actionForward.setCheck(check);
		actionForward.setPath(path);
		return actionForward;
	}
	@Override
	public ActionForward update(HttpServletRequest request, HttpServletResponse response) {
		ActionForward actionForward = new ActionForward();
		String method = request.getMethod();
		String path="../WEB-INF/views/notice/noticeUpdate.jsp";
		String num = request.getParameter("no");
		boolean check=true;
		if(method.equals("POST")) {
			NoticeDTO noticeDTO = new NoticeDTO();
			//1.request하나로 합치기
			MultipartRequest multi = null;
			String saveDirectory = request.getServletContext().getRealPath("upload");
			int maxPostSize = 1024*1024*10;
			String encoding ="utf-8";
			
			try {
				multi = new MultipartRequest(request, saveDirectory, maxPostSize, encoding, new DefaultFileRenamePolicy());
				
			} catch (Exception e) {
				// TODO: handle exception
			}
			noticeDTO.setNo(Integer.parseInt(multi.getParameter("no")));
			noticeDTO.setTitle(multi.getParameter("title"));
			noticeDTO.setContents(multi.getParameter("contents"));
			noticeDTO.setWriter(multi.getParameter("writer"));
			int result = 0;
			try {
				result = noticeDAO.update(noticeDTO);
					
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(result>0) {
				check=false;
				path="./noticeSelect?no="+num;
			}else {
				request.setAttribute("message", "Update Fail");
				request.setAttribute("path", "./noticeUpdate");
				path="../WEB-INF/views/common/result.jsp";
				check=true;
			}
		}
		
		actionForward.setCheck(check);
		actionForward.setPath(path);
		return actionForward;
	}
	@Override
	public ActionForward delete(HttpServletRequest request, HttpServletResponse response) {
		ActionForward actionForward = new ActionForward();
		int num = Integer.parseInt(request.getParameter("no"));
		System.out.println("Num : "+num);
		int result=0;
		boolean check = true;
		String path="./noticeSelect?no="+num;
		try {
			result =noticeDAO.delete(num);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(result>1) {
			check= false;
			path="./noticeList";
		}
		actionForward.setCheck(check);
		actionForward.setPath(path);
		return actionForward;
	}
	
}
