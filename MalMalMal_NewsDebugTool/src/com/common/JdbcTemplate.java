package com.common;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class JdbcTemplate {
	public JdbcTemplate(){
		
	}
	//Connection
	public static Connection getConnection(){
		Connection conn = null;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			
			String url = "";
			String id = "";   
			String pass = "";       
			conn = DriverManager.getConnection(url, id, pass);   		
			conn.setAutoCommit(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public static boolean isConnection(Connection conn){
		boolean valid=true;
		try{
			if(conn==null || conn.isClosed()){
				valid=false;
			}
		}catch(SQLException e){
			valid=true;
			e.printStackTrace();
		}
		
		return valid;
	}
	
	public static void close(Connection conn){
		if(isConnection(conn)){
			try{
				conn.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
	}
	
	public static void close(Statement stmt){
		try{
			if(stmt != null) stmt.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public static void close(ResultSet rs){
		try{
			if(rs != null) rs.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public static void commit(Connection conn){
		if(isConnection(conn)){
			try{
				conn.commit();
				//System.out.println("commit OK");
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
	}
	
	public static void rollback(Connection conn){
		if(isConnection(conn)){
			try{
				conn.rollback();
				System.out.println("rollback OK");
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
	}
	
}
