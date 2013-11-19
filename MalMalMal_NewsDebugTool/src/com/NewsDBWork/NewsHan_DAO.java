package com.NewsDBWork;

import static com.common.JdbcTemplate.close;
import static com.common.JdbcTemplate.commit;


import static com.common.JdbcTemplate.getConnection;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.Entity.NewsMorphemeSentence;
import com.Entity.NewsSentence;
import com.Entity.IssueData;

public class NewsHan_DAO {
	PreparedStatement pstmt;
	ResultSet rs;
	Connection conn;

	public NewsHan_DAO()
	{
	}

	public NewsHan_DAO(Connection conn)
	{
		this.conn=conn;
	}
	
	
	
	//뉴스 날리지 및 형태소 분석 결과를 삭제하고 원문을 가져옴
	public ArrayList<NewsSentence> selectNewsCheck(int news_Idx) {
		ArrayList<NewsSentence> arr = new ArrayList<NewsSentence>();
		//String sql="exec sp_GetNewsParagraphByDate '20131216', '20131299'";
		
		//exec sp_GetNewsParagraphByID
		try {
			CallableStatement cs = conn.prepareCall("{call sp_DeleteNewsInfoByID(?)}");
			cs.setInt(1,news_Idx);
			cs.execute();
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			close(pstmt);
			commit(conn);
		}
		
		String sql="exec sp_GetNewsParagraphByID "+news_Idx;
		
		try {
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();

			while(rs.next()){
				NewsSentence sentenceData = new NewsSentence(rs.getString(1), rs.getString(2), rs.getString(3));
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

	
	public boolean insertNewsSentence(String n_idx,String p_idx,String morpheme, String exp) {


		try {
			CallableStatement cs = conn.prepareCall("{call sp_AddNewsMorpheme(?,?,?,?)}");

			cs.setInt(1,Integer.parseInt(n_idx));
			cs.setInt(2, Integer.parseInt(p_idx));
			cs.setString(3, morpheme);
			cs.setString(4, exp);
			cs.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			close(pstmt);
		}

		return true;
	}


	public boolean insertNewsSentence(ArrayList<NewsMorphemeSentence> arr) {
		
		for(int i=0;i < arr.size();i++){

			NewsMorphemeSentence result = arr.get(i);

			try {

				CallableStatement cs = conn.prepareCall("{call sp_AddNewsMorpheme(?,?,?,?)}");

				cs.setInt(1,Integer.parseInt(result.getNewsId()));
				cs.setInt(2, Integer.parseInt(result.getSentenceId()));
				cs.setString(3, result.getMorpheme());
				cs.setString(4, result.getExp());

				cs.execute();

				//if((i%100) == 0){
					//System.out.println(i+" 문장 입력");
					
				//}
				//while(rs.next()){
				//	NewsSentence sentenceData = new NewsSentence(rs.getString(1), rs.getString(2), rs.getString(3));
				//	arr.add(sentenceData);
				//}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				//close(rs);
				
				close(pstmt);
			}
			//System.out.println("입력완료");
			commit(conn);
		}
		return true;
		//

	}
}
