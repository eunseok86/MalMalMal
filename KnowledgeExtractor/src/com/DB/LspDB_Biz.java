package com.DB;

import static com.common.JdbcTemplate.close;
import static com.common.JdbcTemplate.getConnection;

import java.sql.Connection;
import java.util.ArrayList;

import com.DB.Entity.LSP;



public class LspDB_Biz {
	Connection conn;
	public LspDB_Biz()
	{
		conn = getConnection();
	}
	
	public ArrayList<LSP> selectAll() {
		ArrayList<LSP> result;
		conn=getConnection();
		LspDB_DAO dao = new LspDB_DAO(conn);
		
		result = dao.selectAll();
		
		close(conn);
		
		return result;
	}
	
}
