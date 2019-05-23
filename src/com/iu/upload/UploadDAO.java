package com.iu.upload;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.iu.util.DBConnector;

public class UploadDAO {
	
	//select
	public UploadDTO selectOne(int num) throws Exception{
		UploadDTO uploadDTO = null;
		Connection con = DBConnector.getConnect();
		String sql = "select * from upload where no=?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setInt(1, num);
		ResultSet rs = st.executeQuery();
		if(rs.next()) {
			uploadDTO = new UploadDTO();
			uploadDTO.setPnum(rs.getInt("pnum"));
			uploadDTO.setNo(rs.getInt("no"));
			uploadDTO.setoName(rs.getString("oname"));
			uploadDTO.setfName(rs.getString("fname"));
		}
		DBConnector.disConnect(con, st, rs);
		return uploadDTO;
	}
	
	//update
	
	//delete
	
	//insert
	public int insert(UploadDTO uploadDTO, Connection con) throws Exception{
		int result=0;
		String sql = "insert into upload values(num_seq.nextval, ?, ?, ?)";
		PreparedStatement st = con.prepareStatement(sql);
		st.setInt(1, uploadDTO.getNo());
		st.setString(2, uploadDTO.getoName());
		st.setString(3, uploadDTO.getfName());
		result = st.executeUpdate();
		st.close();
		return result;
	}
	
}
