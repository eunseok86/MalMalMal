package com.DB;

import static com.common.JdbcTemplate.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.DB.Entity.LSP;




public class LspDB_DAO {
	PreparedStatement pstmt;
	ResultSet rs;
	Connection conn;
	
	public LspDB_DAO()
	{
	}
	
	public LspDB_DAO(Connection conn)
	{
		this.conn=conn;
	}
	
	public ArrayList<LSP> selectAll() {
		ArrayList<LSP> arr = new ArrayList<LSP>();
		String sql="exec sp_GetLSPbyAll";
		
		
		try {
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			
			while(rs.next()){
				LSP LspData = new LSP(rs.getString(1), rs.getString(2));
				arr.add(LspData);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			close(rs);
			close(pstmt);
		}
		
		return arr;
	}

	
	
}
