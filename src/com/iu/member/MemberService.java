package com.iu.member;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.iu.action.Action;
import com.iu.action.ActionForward;
import com.iu.mupload.MuploadDAO;
import com.iu.mupload.MuploadDTO;
import com.iu.util.DBConnector;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.sun.org.apache.bcel.internal.util.BCELifier;

public class MemberService implements Action {
	private MemberDAO memberDAO;
	private MuploadDAO muploadDAO;
	
	public MemberService() {
		memberDAO = new MemberDAO();
		muploadDAO = new MuploadDAO();
	}

	@Override
	public ActionForward list(HttpServletRequest request, HttpServletResponse response) {
		ActionForward actionForward = new ActionForward();
		return actionForward;
	}

	@Override
	public ActionForward select(HttpServletRequest request, HttpServletResponse response) {
		ActionForward actionForward = new ActionForward();
		return actionForward;
	}

	@Override
	public ActionForward insert(HttpServletRequest request, HttpServletResponse response) {
		ActionForward actionForward = new ActionForward();
		String method = request.getMethod();
		String path="../WEB-INF/views/member/memberJoin.jsp";
		boolean check= true;
		if(method.equals("POST")) {
			MemberDTO memberDTO = new MemberDTO();
			String saveDirectory = request.getServletContext().getRealPath("upload");
			int maxPostSize = 1024*1024*10;
			String encoding ="utf-8";
			MultipartRequest  multi = null;
			try {
				multi = new MultipartRequest(request, saveDirectory, maxPostSize, encoding, new DefaultFileRenamePolicy());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			MuploadDTO muploadDTO = new MuploadDTO();
			memberDTO.setPw(multi.getParameter("pw"));
			memberDTO.setName(multi.getParameter("name"));
			memberDTO.setPhone(multi.getParameter("phone"));
			memberDTO.setEmail(multi.getParameter("email"));
			memberDTO.setAge(Integer.parseInt(multi.getParameter("age")));
			memberDTO.setId(multi.getParameter("id"));
			
			muploadDTO.setFname(multi.getFilesystemName("photo"));
			muploadDTO.setOname(multi.getOriginalFileName("photo"));
			
			Connection con = null;
			int result =0;
			try {
				con = DBConnector.getConnect();
				con.setAutoCommit(false);
				
				result = memberDAO.memberJoin(memberDTO, con);
				
				muploadDTO.setId(multi.getParameter("id"));
				result = muploadDAO.insert(muploadDTO, con);
				System.out.println("check3");
				if(result<1) {
					throw new Exception();
				}
				con.commit();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				result=0;
				e.printStackTrace();
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}//rollback try catch
			}finally {
				try {
					con.setAutoCommit(true);
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}//finally
			if(result>0) {
				check=false;
				path = "../index.do";
			}else {
				request.setAttribute("message", "Fail");
				request.setAttribute("path", "./memberJoin");
				check = true;
				path="../WEB-INF/views/common/result.jsp";
			}
		}//post if
		
		actionForward.setCheck(check);
		actionForward.setPath(path);
		return actionForward;
	}

	@Override
	public ActionForward update(HttpServletRequest request, HttpServletResponse response) {
		ActionForward actionForward = new ActionForward();
		return actionForward;
	}

	@Override
	public ActionForward delete(HttpServletRequest request, HttpServletResponse response) {
		ActionForward actionForward = new ActionForward();
		return actionForward;
	}

	
}
