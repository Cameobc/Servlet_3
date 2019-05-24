package com.iu.qna;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.iu.action.Action;
import com.iu.action.ActionForward;
import com.iu.page.Search;
import com.iu.page.SearchMakePage;
import com.iu.page.SearchPager;
import com.iu.page.SearchRow;
import com.iu.upload.UploadDAO;
import com.iu.upload.UploadDTO;
import com.iu.util.DBConnector;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class QnaService implements Action {

	private QnaDAO qnaDAO;
	private UploadDAO uploadDAO;
	
	public QnaService() {
		qnaDAO = new QnaDAO();
		uploadDAO = new UploadDAO();
	}
	
	@Override
	public ActionForward list(HttpServletRequest request, HttpServletResponse response) {
		ActionForward actionForward = new ActionForward();
		int curPage = 1;
		try {
			curPage = Integer.parseInt(request.getParameter("no"));
		} catch (Exception e) {
			// TODO: handle exception
		}
		String kind = request.getParameter("kind");
		String search = request.getParameter("search");
		SearchMakePage s = new SearchMakePage(curPage, kind, search);
		//1. row
		SearchRow searchRow = s.makeRow();		
		//2.page
		int totalCount = 0;
		try {
			totalCount=qnaDAO.getCountNum(searchRow);
			ArrayList<QnaDTO> ar = qnaDAO.list(searchRow);
			request.setAttribute("list", ar);
		} catch (Exception e) {
		}
		SearchPager searchPager = s.makePage(totalCount);
		request.setAttribute("pager", searchPager);
		actionForward.setCheck(true);
		actionForward.setPath("../WEB-INF/views/qna/qnaList.jsp");
		return actionForward;
	}

	@Override
	public ActionForward select(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionForward insert(HttpServletRequest request, HttpServletResponse response) {
		ActionForward actionForward = new ActionForward();
		String method = request.getMethod();
		String path ="../WEB-INF/views/qna/qnaWrite.jsp";
		boolean check = true;
		if(method.equals("POST")) {
			String saveDirectory = request.getServletContext().getRealPath("upload");
			int maxPostSize = 1024*1024*10;
			String encoding ="utf-8";
			MultipartRequest multi = null;
			Connection con = null;
			try {
				 multi = new MultipartRequest(request, saveDirectory, maxPostSize, encoding, new DefaultFileRenamePolicy());
				 Enumeration<String> e = multi.getFileNames();  // 파라미터 이름들
				 ArrayList<UploadDTO> ar = new ArrayList<UploadDTO>();
				 while(e.hasMoreElements()) {
					 UploadDTO uploadDTO = new UploadDTO();
					 String  s = e.nextElement();
					 String fname = multi.getFilesystemName(s);
					 String oname = multi.getOriginalFileName(s);
					 uploadDTO.setfName(fname);
					 uploadDTO.setoName(oname);
					 ar.add(uploadDTO);
				 }
				 QnaDTO qnaDTO = new QnaDTO();
				 qnaDTO.setTitle(multi.getParameter("title"));
				 qnaDTO.setWriter(multi.getParameter("writer"));
				 qnaDTO.setContents(multi.getParameter("contents"));
				 //1.se
				 int num = qnaDAO.getNum();
				 qnaDTO.setNo(num);
				 con = DBConnector.getConnect();
				 con.setAutoCommit(false);
				 //2.qna insert
				 num = qnaDAO.insert(qnaDTO, con);
				 
				 System.out.println(num);
				 //3.upload insert
				 for(UploadDTO uploadDTO : ar) {
					 uploadDTO.setNo(qnaDTO.getNo());
					 num = uploadDAO.insert(uploadDTO, con);
					 if(num<1) {
						 throw new Exception();
					 }
				 }
				 con.commit();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}finally {
				try {
					con.setAutoCommit(true);
					con.close();
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}//post끝
			
			check = false;
			path="./qnaList";
		}
		actionForward.setCheck(check);
		actionForward.setPath(path);
		return actionForward;
	}

	@Override
	public ActionForward update(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionForward delete(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}
}