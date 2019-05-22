package com.iu.point;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Random;

import com.iu.util.DBConnector;

public class PointDAO {
	
	//getTotalCount  전체 개수 받아오는거 리턴  
	public int getTotalCount(String kind, String search) throws Exception {
		Connection con = DBConnector.getConnect();
		String sql = "select count(num) from point where "+ kind +" like ?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, "%"+search+"%");
		ResultSet rs = st.executeQuery();
		rs.next();
		int result= rs.getInt("count(num)");
		DBConnector.disConnect(con, st, rs);
		return result;
	}
	
	//메서드명 selectList 리턴 ArrayList<PointDTO>
	public ArrayList<PointDTO> selectList(String kind, String search, int startRow, int lastRow) throws Exception{
		ArrayList<PointDTO> ar = new ArrayList<PointDTO>();
		Connection con = DBConnector.getConnect();
		//엔터칠때마다 앞뒤로 띄어쓰기하기
		String sql =" select * from "
				+ "(select rownum r, p.*from "
				+ "(select * from point where "+ kind+" like ? order by num desc) p) "
				+ "where r between ? and ?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, "%"+search+"%");
		st.setInt(2, startRow);
		st.setInt(3, lastRow);
		ResultSet rs = st.executeQuery();
		while(rs.next()) {
			PointDTO pointDTO = new PointDTO();
			pointDTO.setNum(rs.getInt("num"));
			pointDTO.setName(rs.getString("name"));
			pointDTO.setKor(rs.getInt("kor"));
			pointDTO.setEng(rs.getInt("eng"));
			pointDTO.setMath(rs.getInt("math"));
			pointDTO.setSum(rs.getInt("sum"));
			pointDTO.setAvg(rs.getDouble("avg"));
			ar.add(pointDTO);
		}
		DBConnector.disConnect(con, st, rs);
		return ar;
	}
	
	//메서드명 selectOne 리턴 pointdto 매개변수 int 
	public PointDTO selectOne(int num) throws Exception{
		Connection con = DBConnector.getConnect();
		String sql ="select * from point where num=?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setInt(1, num);
		ResultSet rs = st.executeQuery();
		PointDTO pointDTO = null;
		if(rs.next()) {
			pointDTO = new PointDTO();
			pointDTO.setNum(rs.getInt("num"));
			pointDTO.setName(rs.getString("name"));
			pointDTO.setKor(rs.getInt("kor"));
			pointDTO.setEng(rs.getInt("eng"));
			pointDTO.setMath(rs.getInt("math"));
			pointDTO.setSum(rs.getInt("sum"));
			pointDTO.setAvg(rs.getDouble("avg"));
		}
		DBConnector.disConnect(con, st, rs);
		return pointDTO;
	}
	
	//메서드명 delete 리턴 int 매개변수 int 예외는 던지기
	public int delete(int num) throws Exception{
		Connection con = DBConnector.getConnect();
		String sql="delete point where num=?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setInt(1, num);
		int result = st.executeUpdate();
		DBConnector.disConnect(con, st);
		return result;
	}
	
	//메서드명 update 리턴 int 매개변수PointDTO 예외는 던지기
	public int update(PointDTO pointDTO) throws Exception {
		Connection con = DBConnector.getConnect();
		String sql ="update point set name=?, kor=?, eng=?, math=?, sum=?, avg=? where num=?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, pointDTO.getName());
		st.setInt(2, pointDTO.getKor());
		st.setInt(3, pointDTO.getEng());
		st.setInt(4, pointDTO.getMath());
		st.setInt(5, pointDTO.getSum());
		st.setDouble(6, pointDTO.getAvg());
		st.setInt(7, pointDTO.getNum());
		int result = st.executeUpdate();
		DBConnector.disConnect(con, st);
		return result;
	}
	
	//메서드명  INSERT 리턴 int 매개변수 PointDTO 예외는 던지기
	public int insert(PointDTO pointDTO) throws Exception {
		Connection con = DBConnector.getConnect();
		String sql ="insert into point values(num_seq.nextval, ?, ?, ?, ?, ?, ?)";
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, pointDTO.getName());
		st.setInt(2, pointDTO.getKor());
		st.setInt(3, pointDTO.getEng());
		st.setInt(4, pointDTO.getMath());
		st.setInt(5, pointDTO.getSum());
		st.setDouble(6, pointDTO.getAvg());
		int result = st.executeUpdate();
		DBConnector.disConnect(con, st);
		return result;
	}

}
