package com.NewsDBWork;

import static com.common.JdbcTemplate.getConnection;
import static com.common.JdbcTemplate.close;
import static com.common.JdbcTemplate.commit;

import java.sql.Connection;
import java.util.ArrayList;






import com.Entity.NewsMorphemeSentence;
import com.Entity.NewsSentence;
import com.Entity.IssueData;
import com.main.Han_Scheduler;

public class NewsHan_Biz {
	Connection conn;
	public NewsHan_Biz()
	{
		conn = getConnection();
	}
	
	public ArrayList<IssueData> selectIssueCheck(){
		ArrayList<IssueData> result;
		conn=getConnection();
		NewsHan_DAO dao = new NewsHan_DAO(conn);
		
		result = dao.selectIssueCheck();
		
		close(conn);
		
		return result;
	}
	
	
	public ArrayList<NewsSentence> selectNewsCheck() {
		ArrayList<NewsSentence> result;
		conn=getConnection();
		NewsHan_DAO dao = new NewsHan_DAO(conn);
		
		result = dao.selectNewsCheck();
		
		close(conn);
		
		return result;
	}
	
	public boolean insertIssusentence(ArrayList<IssueData> data){
		conn=getConnection();
		NewsHan_DAO dao = new NewsHan_DAO(conn);
		
		dao.insertIssusentence(data);
		commit(conn);
		close(conn);
		
		return true;
		
	}
	
	
	public boolean insertNewsSentence(String n_idx,String p_idx,String morpheme, String exp){
		
		conn=getConnection();
		NewsHan_DAO dao = new NewsHan_DAO(conn);
		
		dao.insertNewsSentence(n_idx,p_idx,morpheme,exp);
		commit(conn);
		close(conn);
		
		return true;
	}
	public boolean insertNewsSentence(ArrayList<NewsMorphemeSentence> arr){
		
		conn=getConnection();
		NewsHan_DAO dao = new NewsHan_DAO(conn);
		
		dao.insertNewsSentence(arr);
		commit(conn);
		close(conn);
		
		return true;
	}
}
