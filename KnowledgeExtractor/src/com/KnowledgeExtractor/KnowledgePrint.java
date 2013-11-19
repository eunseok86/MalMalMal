package com.KnowledgeExtractor;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


import com.DB.Entity.NewsSentence;
import com.Enhanced.CommentChecker;
import com.Entity.Knowledge;
import com.Entity.Morpheme;
import com.Entity.ResultKnowledge;
import com.Entity.SentenceStructure;
import com.common.oesLog;

public class KnowledgePrint {

	public KnowledgePrint() {
		super();
	}

	//knowledge 객체 출력 메서드 및 정렬
	public void knowledgeSYSO(Knowledge knowledge, CommentChecker cmChk, String logFolder){

		ArrayList<SentenceStructure> arrS = knowledge.getS();
		ArrayList<SentenceStructure> arrO = knowledge.getO();

		SentenceStructure S;
		SentenceStructure O;
		//!(nowMorpheme.getCharacter().equals("그")&&nowMorpheme.getTag().equals("NP"))

		for(int b=0;b <arrS.size();b++){		
			if(arrS.size()>b&&arrO.size()>b){
				S = arrS.get(b);
				O = arrO.get(b);
				String resultS = "";
				String resultO = "";
				String resultOM ="";
				for(int i=0;i < S.getSentenceStructure().size(); i++){
					//resultS+=S.getSentenceStructure().get(i).getExp();//원형 출력
					//형태소 출력
					ArrayList<Morpheme> morphemeList = S.getSentenceStructure().get(i).getWord();
					for(int x=0; x < morphemeList.size();x++){
						resultS+=morphemeList.get(x).getCharacter();
					}
					//형태소 출력
					resultS+=" ";
				}

				for(int i=0;i < O.getSentenceStructure().size(); i++){
					resultO+=O.getSentenceStructure().get(i).getExp();

					ArrayList<Morpheme> morphemeList = O.getSentenceStructure().get(i).getWord();
					for(int x=0; x < morphemeList.size();x++){
						if(!morphemeList.get(x).getTag().equals("")){
							if(String.valueOf(morphemeList.get(x).getTag().charAt(0)).equals("n")//||String.valueOf(morphemeList.get(x).getTag().charAt(0)).equals("p")
									||String.valueOf(morphemeList.get(x).getTag().charAt(0)).equals("f"))resultOM+=morphemeList.get(x).getCharacter();
						}

					}
					resultO+=" ";
				}

				//resultOM = (new StringBuffer(resultOM) ).reverse().toString();
				for(int i=1;i<=cmChk.getTotal();i++){
					String resultC = cmChk.getComment(i).replace (" ", "");
					String resultT = resultO.replace (" ", "");

					if(resultC.equals(resultT))resultO=cmChk.getComment(i);
				}
				//StopwordsLog
				String log = "문장: "+knowledge.getExpData();
				log+="\r\n\r\n";
				log+="형태소 분석결과: "+knowledge.getMopData();
				log+="\r\n\r\n\r\n";
				log += "S: 이름-"+S.getKnowledgeS_name()+", 직책-"+
						S.getKnowledgeS_job()+", 소속-"+S.getKnowledgeS_organization()+", 미분류-"+S.getKnowledgeS_unknown();
				log+="\r\n";
				log+="S원형: "+resultS;
				log+="\r\n\r\n";
				log+="O: "+resultO;
				log+="\r\n\r\n\r\n";
				
				oesLog.TraceLog(logFolder, log);
				
				/*
				System.out.println();
				System.out.println(resultS);
				System.out.println("O: "+resultO);
				System.out.println(resultOM);
				*/

			}


			

		}
		
		oesLog.TraceLog(logFolder, knowledge.getCandidateString());


	}



public ArrayList<ResultKnowledge> knowledgeParser(ArrayList<ResultKnowledge> arrResult, 
		Knowledge knowledge, CommentChecker cmChk, ArrayList<NewsSentence> newsResult, int newsIdx, String lspIdx){

	ArrayList<SentenceStructure> arrS = knowledge.getS();
	ArrayList<SentenceStructure> arrO = knowledge.getO();

	SentenceStructure S;
	SentenceStructure O;
	//!(nowMorpheme.getCharacter().equals("그")&&nowMorpheme.getTag().equals("NP"))

	ResultKnowledge resKnowledge;

	for(int b=0;b <arrS.size();b++){		
		if(arrS.size()>b&&arrO.size()>b){
			S = arrS.get(b);
			O = arrO.get(b);
			String resultS = "";
			String resultO = "";
			String resultOM ="";
			for(int i=0;i < S.getSentenceStructure().size(); i++){
				//resultS+=S.getSentenceStructure().get(i).getExp();//원형 출력
				//형태소 출력
				ArrayList<Morpheme> morphemeList = S.getSentenceStructure().get(i).getWord();
				for(int x=0; x < morphemeList.size();x++){
					resultS+=morphemeList.get(x).getCharacter();
				}
				//형태소 출력
				resultS+=" ";
			}

			for(int i=0;i < O.getSentenceStructure().size(); i++){
				resultO+=O.getSentenceStructure().get(i).getExp();

				ArrayList<Morpheme> morphemeList = O.getSentenceStructure().get(i).getWord();
				for(int x=0; x < morphemeList.size();x++){
					if(!morphemeList.get(x).getTag().equals("")){
						if(String.valueOf(morphemeList.get(x).getTag().charAt(0)).equals("n")//||String.valueOf(morphemeList.get(x).getTag().charAt(0)).equals("p")
								||String.valueOf(morphemeList.get(x).getTag().charAt(0)).equals("f"))resultOM+=morphemeList.get(x).getCharacter();
					}

				}
				resultO+=" ";
			}

			//resultOM = (new StringBuffer(resultOM) ).reverse().toString();
			for(int i=1;i<=cmChk.getTotal();i++){
				String resultC = cmChk.getComment(i).replace (" ", "");
				String resultT = resultO.replace (" ", "");
				
				if(resultC.equals(resultT))resultO=cmChk.getComment(i);
			}
			
			
			
			resKnowledge = new ResultKnowledge();
			resKnowledge.setN_idx(newsResult.get(newsIdx).getNewsId());
			resKnowledge.setP_idx(newsResult.get(newsIdx).getSentenceId());
			resKnowledge.setLsp_idx(lspIdx);
			resKnowledge.setName(S.getKnowledgeS_name());
			resKnowledge.setJob(S.getKnowledgeS_job());
			resKnowledge.setOrganization(S.getKnowledgeS_organization());
			resKnowledge.setUnknown(S.getKnowledgeS_unknown());
			resKnowledge.setS(resultS);
			resKnowledge.setP("");
			resKnowledge.setK_idx(Integer.toString(b+1));
			resKnowledge.setO(resultO);
			
			resKnowledge.setO2(resultOM);
			arrResult.add(resKnowledge);
		}	

	}

	return arrResult;
}

}
