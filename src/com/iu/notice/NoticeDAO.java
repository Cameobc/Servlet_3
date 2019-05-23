package com.iu.notice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.iu.page.SearchRow;
import com.iu.util.DBConnector;

public class NoticeDAO {
	
	//rownum추가해서 조회하는 sql문 작성 메서드명은 getCountNum
	public int getCountNum(SearchRow searchRow) throws Exception {
		Connection con = DBConnector.getConnect();
		String sql="select count(no) from notice where "+searchRow.getSearch().getKind()+" like ?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, "%"+searchRow.getSearch().getSearch()+"%");
		ResultSet rs = st.executeQuery();
		rs.next();
		int result = Integer.parseInt(rs.getString(1));
		DBConnector.disConnect(con, st, rs);
		return result;
	}
	
	/*public static void main(String[] args) {
		NoticeDAO noticeDAO = new NoticeDAO();
		NoticeDTO noticeDTO = new NoticeDTO();
		for(int i=0;i<100;i++) {
		noticeDTO.setTitle("a"+i);
		noticeDTO.setContents("aa"+i);
		noticeDTO.setWriter("abc"+i);
		try {
			noticeDAO.insert(noticeDTO);
			Thread.sleep(100);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}*/

	//selectList() r: ar
	public ArrayList<NoticeDTO> selectList(SearchRow searchRow)throws Exception{
		ArrayList<NoticeDTO> ar = new ArrayList<NoticeDTO>();
		Connection con = DBConnector.getConnect();
		String sql = " select * from "
				+ "(select rownum r, n.* from "
				+ "(select no, title, writer, reg_date, hit from notice where "+ searchRow.getSearch().getKind() +" like ? order by no desc) n) "
				+ "where r between ? and ?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, "%"+searchRow.getSearch().getSearch()+"%");
		st.setInt(2, searchRow.getStartRow());
		st.setInt(3, searchRow.getLastRow());
		ResultSet rs = st.executeQuery();
		while(rs.next()) {
			NoticeDTO noticeDTO = new NoticeDTO();
			noticeDTO.setNo(rs.getInt("no"));
			noticeDTO.setTitle(rs.getString("title"));
			noticeDTO.setWriter(rs.getString("writer"));
			noticeDTO.setReg_date(rs.getString("reg_date"));
			noticeDTO.setHit(rs.getInt("hit"));
			ar.add(noticeDTO);
		}
		DBConnector.disConnect(con, st, rs);
		return ar;
	}
	
	//selectOne() r:noticeDTO 매개:int num
	public NoticeDTO seletOne(int num) throws Exception {
		NoticeDTO noticeDTO = null;
		Connection con = DBConnector.getConnect();
		String sql ="select * from notice where no=? order by no desc";
		PreparedStatement st = con.prepareStatement(sql);
		st.setInt(1, num);
		ResultSet rs = st.executeQuery();
		if(rs.next()) {
			noticeDTO = new NoticeDTO();
			noticeDTO.setNo(rs.getInt("no"));
			noticeDTO.setTitle(rs.getString("title"));
			noticeDTO.setContents(rs.getString("contents"));
			noticeDTO.setWriter(rs.getString("writer"));
			noticeDTO.setReg_date(rs.getString("reg_date"));
			noticeDTO.setHit(rs.getInt("hit"));
		}
		DBConnector.disConnect(con, st, rs);
		return noticeDTO;
	}
	//sequence
	public int getNum() throws Exception {
		int result =0;
		Connection con = DBConnector.getConnect();
		String sql ="select notice_seq.nextval from dual";
		PreparedStatement st = con.prepareStatement(sql);
		ResultSet rs= st.executeQuery();
		rs.next();
		result = rs.getInt(1);
		DBConnector.disConnect(con, st, rs);
		return result;
	}
	
	
	//insert() r:int  매개 : noticeDTO
	public int insert(NoticeDTO noticeDTO, Connection con) throws Exception {
		String sql = "insert into notice (no, title, contents, writer) values(?, ?, ?, ?)";
		PreparedStatement st = con.prepareStatement(sql);
		st.setInt(1, noticeDTO.getNo());
		st.setString(2, noticeDTO.getTitle());
		st.setString(3, noticeDTO.getContents());
		st.setString(4, noticeDTO.getWriter());
		int result = st.executeUpdate();
		st.close();
		return result;
	}
	
	//update() r:int 매개 : noticeDTO
	public int update(NoticeDTO noticeDTO) throws Exception{
		Connection con = DBConnector.getConnect();
		String sql = "update notice set title=?, contents=? where no=?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, noticeDTO.getTitle());
		st.setString(2, noticeDTO.getContents());
		st.setInt(3, noticeDTO.getNo());
		int result = st.executeUpdate();
		DBConnector.disConnect(con, st);
		return result;
	}
	
	//delete() r:int 매개: int num
	public int delete(int num) throws Exception {
		Connection con =DBConnector.getConnect();
		String sql  = "delete notice where no=?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setInt(1, num);
		int result = st.executeUpdate();
		DBConnector.disConnect(con, st);
		return result;
	}
	
}
