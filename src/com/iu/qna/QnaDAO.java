package com.iu.qna;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.iu.page.SearchRow;
import com.iu.util.DBConnector;

public class QnaDAO {
	
	//sequence
		public int getNum() throws Exception {
			int result =0;
			Connection con = DBConnector.getConnect();
			String sql ="select qna_seq.nextval from dual";
			PreparedStatement st = con.prepareStatement(sql);
			ResultSet rs= st.executeQuery();
			rs.next();
			result = rs.getInt(1);
			DBConnector.disConnect(con, st, rs);
			return result;
		}
	
	public int getCountNum(SearchRow searchRow) throws Exception {
		Connection con = DBConnector.getConnect();
		String sql="select count(no) from qna where "+searchRow.getSearch().getKind()+" like ?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, "%"+searchRow.getSearch().getSearch()+"%");
		ResultSet rs = st.executeQuery();
		rs.next();
		int result = Integer.parseInt(rs.getString(1));
		DBConnector.disConnect(con, st, rs);
		return result;
	}
	
	public int insert(QnaDTO qnaDTO, Connection con) throws Exception{
		int result =0;
		String sql = "insert into qna values(?, ?, ?, ?, sysdate, 0, ?, 0, 0)";
		PreparedStatement st = con.prepareStatement(sql);
		st.setInt(1, qnaDTO.getNo());
		st.setString(2, qnaDTO.getTitle());
		st.setString(3, qnaDTO.getContents());
		st.setString(4, qnaDTO.getWriter());
		st.setInt(5, qnaDTO.getNo());
		result = st.executeUpdate();
		st.close();
		return result;
	}
	
	//list
	public ArrayList<QnaDTO> list(SearchRow searchRow) throws Exception{
		ArrayList<QnaDTO> ar = new ArrayList<QnaDTO>();
		Connection con = DBConnector.getConnect();
		String sql =" select * from (select rownum r, q.* from "
				+ " (select * from qna where "+ searchRow.getSearch().getKind()+ " like ? order by ref desc, step asc) q) "
						+ " where r between ? and ? ";
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, "%"+searchRow.getSearch().getSearch()+"%");
		st.setInt(2, searchRow.getStartRow());
		st.setInt(3, searchRow.getLastRow());
		ResultSet rs = st.executeQuery();
		while(rs.next()) {
			QnaDTO qnaDTO = new QnaDTO();
			qnaDTO.setNo(rs.getInt("no"));
			qnaDTO.setTitle(rs.getString("title"));
			qnaDTO.setContents(rs.getString("contents"));
			qnaDTO.setWriter(rs.getString("writer"));
			qnaDTO.setReg_date(rs.getString("reg_date"));
			qnaDTO.setHit(rs.getInt("hit"));
			qnaDTO.setRef(rs.getInt("ref"));
			qnaDTO.setStep(rs.getInt("step"));
			qnaDTO.setDepth(rs.getInt("depth"));
			ar.add(qnaDTO);
		}
		DBConnector.disConnect(con, st, rs);
		return ar;
	}
	
}
