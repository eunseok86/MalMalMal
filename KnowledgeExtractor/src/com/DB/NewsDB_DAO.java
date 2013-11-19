package com.DB;
import java.sql.*;
import java.util.ArrayList;

import com.DB.Entity.NewsSentence;

import static com.common.JdbcTemplate.*;


public class NewsDB_DAO{
	PreparedStatement pstmt;
	ResultSet rs;
	Connection conn;
	
	public NewsDB_DAO()
	{
	}
	
	public NewsDB_DAO(Connection conn)
	{
		this.conn=conn;
	}
	
	
	public ArrayList<NewsSentence> selectAll() {
		ArrayList<NewsSentence> arr = new ArrayList<NewsSentence>();
		String sql="exec sp_GetNewsMorphemeByDate '20130701', '20130799'";
		
		
		try {
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			
			while(rs.next()){
				NewsSentence sentenceData = new NewsSentence(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
				arr.add(sentenceData);
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

	public ArrayList<NewsSentence> selectAll(String month) {
		ArrayList<NewsSentence> arr = new ArrayList<NewsSentence>();
		if(month.length()==1)month = "0"+month;
		String sql="exec sp_GetNewsMorphemeByDate '2013"+month+"01', '2013"+month+"99'";
		
		System.out.println(sql);
		try {
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			
			while(rs.next()){
				NewsSentence sentenceData = new NewsSentence(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
				arr.add(sentenceData);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			close(rs);
			close(pstmt);
		}
		System.out.println(arr.size());
		return arr;
	}

}
