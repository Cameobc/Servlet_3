package com.iu.mupload;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class MuploadDAO {
	
	
	//insert
	public int insert(MuploadDTO muploadDTO, Connection con) throws Exception {
		int result =0;
		String sql ="insert into mupload values(num_seq.nextval, ?, ?, ?)";
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, muploadDTO.getId());
		st.setString(2, muploadDTO.getOname());
		st.setString(3, muploadDTO.getFname());
		result = st.executeUpdate();
		st.close();
		return result;
	}

}
