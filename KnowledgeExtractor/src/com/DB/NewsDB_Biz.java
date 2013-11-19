package com.DB;
import static com.common.JdbcTemplate.*;

import java.sql.Connection;
import java.util.ArrayList;

import com.DB.Entity.NewsSentence;

public class NewsDB_Biz 
{
	Connection conn;
	public NewsDB_Biz()
	{
		conn = getConnection();
	}
	


	public ArrayList<NewsSentence> selectAll() {
		ArrayList<NewsSentence> result;
		conn=getConnection();
		NewsDB_DAO dao = new NewsDB_DAO(conn);
		
		result = dao.selectAll();
		
		close(conn);
		
		return result;
	}
	
	public ArrayList<NewsSentence> selectAll(String month) {
		ArrayList<NewsSentence> result;
		conn=getConnection();
		NewsDB_DAO dao = new NewsDB_DAO(conn);
		
		result = dao.selectAll(month);
		
		close(conn);
		
		return result;
	}
	
}
