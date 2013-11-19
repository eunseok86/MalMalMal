package com.Sequence;

import java.util.ArrayList;

import com.DB.KDB_Biz;
import com.DB.LspDB_Biz;
import com.DB.NewsDB_Biz;
import com.DB.Entity.LSP;
import com.DB.Entity.NewsSentence;
import com.Enhanced.CommentChecker;
import com.Enhanced.StopWordsChecker;
import com.Entity.Knowledge;
import com.Entity.PatternStructure;
import com.Entity.ResultKnowledge;
import com.Entity.SentenceStructure;
import com.KnowledgeExtractor.KnowledgeExtractor;
import com.KnowledgeExtractor.KnowledgePrint;
import com.Parser.PatternParser;
import com.Parser.SentenceParser;

public class knowledgeExtSeq {
	private ArrayList<NewsSentence> newsResult;
	private boolean dieFlag = false;
	private ArrayList<LSP> lspData;
	private ArrayList<ResultKnowledge> resultKarr = new ArrayList<ResultKnowledge>();
	private KnowledgeExtractor KE;
	private ArrayList<ResultKnowledge> arrResult = new ArrayList<ResultKnowledge>();
	
	
	public void callDB(){
		//뉴스 로딩
		NewsDB_Biz dbBiz = new NewsDB_Biz();
		this.newsResult = dbBiz.selectAll();
		//lsp 로딩
		LspDB_Biz lspBiz = new LspDB_Biz();
		this.lspData = lspBiz.selectAll();
	}
	
	public void callDB(String month){
		//뉴스 로딩
		NewsDB_Biz dbBiz = new NewsDB_Biz();
		this.newsResult = dbBiz.selectAll(month);
		//lsp 로딩
		LspDB_Biz lspBiz = new LspDB_Biz();
		this.lspData = lspBiz.selectAll();
	}
	
	public void callDebugDB(ArrayList<NewsSentence> data){
		//뉴스 로딩
		//NewsDB_Biz dbBiz = new NewsDB_Biz();
		this.newsResult = data;
		//lsp 로딩
		LspDB_Biz lspBiz = new LspDB_Biz();
		this.lspData = lspBiz.selectAll();
		
		
	}

	public void callCandidatesExtractingSeq(boolean flag){
		String ExpData ="";
		String prevId="0";
		String NewsData="";
		int countSameFirstName = 0;
		KE = new KnowledgeExtractor();
		
		StopWordsChecker stCheker = new StopWordsChecker();
		
		newsLoop:for(int z = 0; z < this.newsResult.size();z++){
			//if((z%1000) == 0)System.out.println(this.newsResult.size()+":"+z);
			ArrayList<Knowledge> resultKnowledge = new ArrayList<Knowledge>();
			//원문에서 발언부분 뽑아오기 위한 객체 생성
			CommentChecker cmChk = new CommentChecker(this.newsResult.get(z).getSentence());
			if(this.newsResult.get(z).getNewsId().equals(prevId)){//이전 뉴스아이디와 같으면 같은뉴스
				prevId = this.newsResult.get(z).getNewsId();
				//탐색
				this.knowledgeExtSeq(newsResult.get(z).getExp(), newsResult.get(z).getSentence(), z,flag, stCheker);
			//	KE.KnowledgeExtractorSequence(PS, ST);
				
			}else if(z==(this.newsResult.size()-1)){//마지막 노드
				//탐색
				//KE.KnowledgeExtractorSequence(PS, ST);
				//초기화
				KE.KnowledgeExtractorInit();
				
				this.knowledgeExtSeq(newsResult.get(z).getExp(), newsResult.get(z).getSentence(), z,flag, stCheker);
				
			}else{//다른 뉴스
				//초기화
				KE.KnowledgeExtractorInit();
				//System.out.println(newsResult.get(z).getNewsId());
				//탐색
				this.knowledgeExtSeq(newsResult.get(z).getExp(), newsResult.get(z).getSentence(), z,flag, stCheker);
				prevId = newsResult.get(z).getNewsId();
			}
			
			if(dieFlag){
				//System.out.println(this.newsResult.get(z).getNewsId());
				//arrResult.clear();
				dieFlag=false;
				countSameFirstName++;
				
				//break newsLoop;
			}
			
				//countSingleJob+=
			
			
		
		}
		
		//if(!dieFlag){
		System.out.println("전체 문장의 갯수 : "+this.newsResult.size());
		System.out.println("같은 성을 가진 후보군과 성이 발견된 문장수 : "+countSameFirstName);
			KDB_Biz kdb_biz = new KDB_Biz();
			kdb_biz.insert(arrResult);
		//}
		

	}

	private void knowledgeExtSeq(String newsExp, String newsSentence, int newsIdx, boolean flag,StopWordsChecker stCheker){
		//원문에서 발언부분 뽑아오기 위한 객체 생성
		CommentChecker cmChk = new CommentChecker(newsExp);
			
			/***/
			lspLoop:for(int i=0;i<lspData.size();i++){
				//System.out.println(flag);
				PatternParser PP = new PatternParser();
				SentenceParser SP = new SentenceParser();
				PatternStructure PS =  PP.PatternSubstitution(lspData.get(i).getPattern());
				SentenceStructure SS = SP.SentenceSubstitution(newsExp,newsSentence);

				Knowledge knowledge = new Knowledge();
				knowledge = KE.KnowledgeExtractorSequence(PS,SS);
				if(knowledge.isDieFlag()){
					dieFlag = true;
					break lspLoop;
				}
				boolean checkStopBoolean = false;
				if(knowledge.getO().size()!=0&&knowledge.getS().size()!=0){
					//System.out.println("패턴(pattern)-"+PS.getCategory()+"   후진탐색:"+PS.isReverseSerch());
					
					
					arrResult =	new KnowledgePrint().knowledgeParser(arrResult, knowledge, cmChk, newsResult, newsIdx, lspData.get(i).getIdx());
					
					stopWordCheck:for(int ti=0; ti < arrResult.size(); ti++){
						String job = arrResult.get(ti).getJob();
						String name = arrResult.get(ti).getName();
						String organization = arrResult.get(ti).getOrganization();
						if(stCheker.checkSeq(name,job,organization)){
							
							new KnowledgePrint().knowledgeSYSO(knowledge, cmChk,"StopwordsLog");
							arrResult.remove(ti);
							checkStopBoolean = true;
							break stopWordCheck;
						}
						
					}
					
					
					//System.out.println(resKnowArr.size());
					if(flag&&!checkStopBoolean) new KnowledgePrint().knowledgeSYSO(knowledge, cmChk,"DebugLog");
					
					checkStopBoolean = false;
				}
				
				
			}
			
			
			
		
		
	}
	



		
		
		
		
		
	
}
