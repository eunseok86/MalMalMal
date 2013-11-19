package com.DB;

import static com.common.JdbcTemplate.close;
import static com.common.JdbcTemplate.commit;
import static com.common.JdbcTemplate.getConnection;
import static com.common.JdbcTemplate.rollback;

import java.sql.Connection;
import java.util.ArrayList;

public class KDB_Biz {
	Connection conn;
	public KDB_Biz()
	{
		conn = getConnection();
	}
	
	public String insert(Object data)
	{
		String flag="";
		conn=getConnection();
		
		KDB_DAO dao = new KDB_DAO(conn);
		flag=dao.insert(data);
		
		//if(!flag.equals("")) 
			commit(conn);
		//else rollback(conn);
		
		close(conn);
		
		return flag;
	}
}
