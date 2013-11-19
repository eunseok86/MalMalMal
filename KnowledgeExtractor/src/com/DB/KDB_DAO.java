package com.DB;

import static com.common.JdbcTemplate.close;
import static com.common.JdbcTemplate.commit;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;






import com.Entity.ResultKnowledge;

public class KDB_DAO {
	PreparedStatement pstmt;
	ResultSet rs;
	Connection conn;
	
	public KDB_DAO()
	{
		
	}
	
	public KDB_DAO(Connection conn)
	{
		this.conn=conn;
	}
	
	public String insert(Object data) {
		String flag="";
		ArrayList<ResultKnowledge> resultKarr = (ArrayList<ResultKnowledge>)data;
		
		
				
			
		
		for(int i=0; i < resultKarr.size();i++){
			ResultKnowledge resultK = resultKarr.get(i);
			
			try {
				CallableStatement cs = conn.prepareCall("{call sp_AddKnowledgeForSpeaking(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
				cs.setInt(1, Integer.parseInt(resultK.getN_idx()));
				cs.setInt(2, Integer.parseInt(resultK.getP_idx()));
				cs.setInt(3, Integer.parseInt(resultK.getLsp_idx()));
				cs.setInt(4, Integer.parseInt(resultK.getK_idx()));
				cs.setString(5, resultK.getS());
				cs.setString(6, resultK.getP());
				cs.setString(7, resultK.getO());
				cs.setString(8, resultK.getO2());
				cs.setString(9, resultK.getName());
				cs.setString(10, resultK.getJob());
				cs.setString(11, "");
				cs.setString(12, resultK.getOrganization());
				cs.setString(13, "");
				cs.setString(14, resultK.getUnknown());
				cs.execute();
				
				if((i%100) == 0) commit(conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				close(pstmt);
			}
		}
		
		
		return flag;
		
	}
	
}
