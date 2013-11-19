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
		//���� �ε�
		NewsDB_Biz dbBiz = new NewsDB_Biz();
		this.newsResult = dbBiz.selectAll();
		//lsp �ε�
		LspDB_Biz lspBiz = new LspDB_Biz();
		this.lspData = lspBiz.selectAll();
	}
	
	public void callDB(String month){
		//���� �ε�
		NewsDB_Biz dbBiz = new NewsDB_Biz();
		this.newsResult = dbBiz.selectAll(month);
		//lsp �ε�
		LspDB_Biz lspBiz = new LspDB_Biz();
		this.lspData = lspBiz.selectAll();
	}
	
	public void callDebugDB(ArrayList<NewsSentence> data){
		//���� �ε�
		//NewsDB_Biz dbBiz = new NewsDB_Biz();
		this.newsResult = data;
		//lsp �ε�
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
			//�������� �߾�κ� �̾ƿ��� ���� ��ü ����
			CommentChecker cmChk = new CommentChecker(this.newsResult.get(z).getSentence());
			if(this.newsResult.get(z).getNewsId().equals(prevId)){//���� �������̵�� ������ ��������
				prevId = this.newsResult.get(z).getNewsId();
				//Ž��
				this.knowledgeExtSeq(newsResult.get(z).getExp(), newsResult.get(z).getSentence(), z,flag, stCheker);
			//	KE.KnowledgeExtractorSequence(PS, ST);
				
			}else if(z==(this.newsResult.size()-1)){//������ ���
				//Ž��
				//KE.KnowledgeExtractorSequence(PS, ST);
				//�ʱ�ȭ
				KE.KnowledgeExtractorInit();
				
				this.knowledgeExtSeq(newsResult.get(z).getExp(), newsResult.get(z).getSentence(), z,flag, stCheker);
				
			}else{//�ٸ� ����
				//�ʱ�ȭ
				KE.KnowledgeExtractorInit();
				//System.out.println(newsResult.get(z).getNewsId());
				//Ž��
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
		System.out.println("��ü ������ ���� : "+this.newsResult.size());
		System.out.println("���� ���� ���� �ĺ����� ���� �߰ߵ� ����� : "+countSameFirstName);
			KDB_Biz kdb_biz = new KDB_Biz();
			kdb_biz.insert(arrResult);
		//}
		

	}

	private void knowledgeExtSeq(String newsExp, String newsSentence, int newsIdx, boolean flag,StopWordsChecker stCheker){
		//�������� �߾�κ� �̾ƿ��� ���� ��ü ����
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
					//System.out.println("����(pattern)-"+PS.getCategory()+"   ����Ž��:"+PS.isReverseSerch());
					
					
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
