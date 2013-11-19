package com.Enhanced;

import java.util.ArrayList;
import java.util.Collections;

import com.Entity.CandidatesNQ;
import com.Entity.Knowledge;
import com.Entity.Morpheme;
import com.Entity.SentenceStructure;
import com.Entity.Word;

public class S_Divider {
	private  NamedEntityRecognitionor namedEntityRecognitionor;

	public S_Divider() {
		super();
		this.namedEntityRecognitionor = new NamedEntityRecognitionor();
	}

	public Knowledge S_DividerSequence(Knowledge knowledge, ArrayList<CandidatesNQ> candidatesNQ){
		ArrayList<SentenceStructure> arrS = knowledge.getS();
		String firstName="";
		String job="";
		String organization ="";
		boolean checkName=false;
		for(int b=0;b <arrS.size();b++){
			SentenceStructure S = arrS.get(b);

			int nowWordIdx = S.getSentenceStructure().size()-1;//마지막 인덱스 역방향 탐색

			for(;nowWordIdx>=0;nowWordIdx--){//워드[어절] 단위 탐색
				Word nowWord = S.getSentenceStructure().get(nowWordIdx);
				//String jobData = S.getKnowledgeS_job();
				int nowMorpIdx = nowWord.getWord().size()-1;


				for(;nowMorpIdx>=0;nowMorpIdx--){//형태소 단위 탐색
					Morpheme nowMorpheme = nowWord.getWord().get(nowMorpIdx);
					if(nowMorpheme.getTag().equals("f")&&
							knowledge.getS().get(b).getKnowledgeS_organization().equals("")&&
							nowMorpheme.getCharacter().length()!=1){//기관명, 1글자가 아닌
						knowledge.getS().get(b).setKnowledgeS_organization(nowMorpheme.getCharacter());
					}else if(nowMorpheme.getTag().equals("nq")&&knowledge.getS().get(b).getKnowledgeS_name().equals("")){//이름
						knowledge.getS().get(b).setKnowledgeS_name(nowMorpheme.getCharacter());
						checkName = true;
					}else if(nowMorpheme.getTag().equals("ncr")&&knowledge.getS().get(b).getKnowledgeS_job().equals("")){//직책
						knowledge.getS().get(b).setKnowledgeS_job(nowMorpheme.getCharacter());
					}else{//미분류, 성으로 들어갈수 없는 조사와같은 애들 필터
						if(nowMorpheme.getCharacter().length()==1&&!nowMorpheme.getTag().equals("jcs")&&
								!nowMorpheme.getTag().equals("ecx")&&!nowMorpheme.getTag().equals("pvg")){
							if(this.namedEntityRecognitionor.matchFirstName(nowMorpheme.getCharacter())){
								//이부분에서 S후보군[이름]과 성을 매치
								CandidatesNQ resMFN = matchFirstname(nowMorpheme.getCharacter(),nowWord.getExpIdx(), candidatesNQ,"",true);
								//System.out.println(resMFN);
								if(resMFN.getName().equals("stopSerch")){
									knowledge.setDieFlag(true);
								}else if(!resMFN.getName().equals("none")){
									firstName = resMFN.getName();
									organization = resMFN.getOrganization();
									job = resMFN.getJob();
								}else{//이름탐색 실패하면 그냥 성을 입력
									firstName = nowMorpheme.getCharacter();
								}

							}else{//성이 아닌 한글자짜리임
								knowledge.getS().get(b).setKnowledgeS_unknown(nowMorpheme.getCharacter());
							}
						}else{
							knowledge.getS().get(b).setKnowledgeS_unknown(nowMorpheme.getCharacter());
						}

					}
				}

				//성이 2글자도 생각해야함
				
				if(nowWord.getExp().length()==1){//이름이 1글자짜리
					if(this.namedEntityRecognitionor.matchFirstName(nowWord.getExp())){
						String jobString = S.getKnowledgeS_job();
						CandidatesNQ resMFN = matchFirstname(nowWord.getExp(),nowWord.getExpIdx(), candidatesNQ,jobString,true);
						if(resMFN.getName().equals("stopSerch")){
							knowledge.setDieFlag(true);
						}else if(!resMFN.getName().equals("none")){
							firstName = resMFN.getName();
							organization = resMFN.getOrganization();
							job = resMFN.getJob();
						}else{//이름탐색 실패하면 그냥 성을 입력
							firstName = nowWord.getExp();
						}
					}
				}else{
					String jobString = S.getKnowledgeS_job();
					CandidatesNQ resMFN = matchFirstname(nowWord.getExp(),nowWord.getExpIdx(), candidatesNQ,jobString,false);
					if(resMFN.getName().equals("stopSerch")){
						knowledge.setDieFlag(true);
					}else if(!resMFN.getName().equals("none")){
						firstName = resMFN.getName();
						organization = resMFN.getOrganization();
						job = resMFN.getJob();
					}

				}

				if(firstName!=""&&!checkName){//이름이 탐색 안되고 성이 탐색이 되었으면...
					knowledge.getS().get(b).setKnowledgeS_name(firstName);
					knowledge.getS().get(b).setKnowledgeS_job(job);
					knowledge.getS().get(b).setKnowledgeS_organization(organization);
				}

				//	knowledge.getS().get(b).setKnowledgeS_unknown(
				//	(new StringBuffer(knowledge.getS().get(b).getKnowledgeS_unknown()))
				//		.reverse().toString());

			}
		}
		
		return knowledge;

	}

	private CandidatesNQ matchFirstname(String fname,int fnameIdx, ArrayList<CandidatesNQ> candidatesNQ, String jobString,boolean serchFlag){
		int prevIdx = 9999;
		CandidatesNQ candidateData = new CandidatesNQ();
		candidateData.setName("none");
		int checkNameCount = 0;
		for(int i=0; i<candidatesNQ.size();i++){
			CandidatesNQ resCan = candidatesNQ.get(i);
			if(resCan.getJob()==null)resCan.setJob("");
			
			if((
					(String.valueOf(resCan.getName().charAt(0)).equals(fname)&&serchFlag)
					||resCan.getName().equals(fname)&&!serchFlag)&&//resCan.getJob().equals(jobString)
					resCan.getJob().contains(jobString)
					){//성과 이름, 직책을 비교
				//최소한 성보다 이름이 expIdx가 낮아야함[성으로 축약해서 표현하기 전에 먼저 이름이 언급된 후 축약되기 때문]
				prevIdx=resCan.getExpIdx();
				candidateData.setName(resCan.getName());
				candidateData.setJob(resCan.getJob());
				candidateData.setOrganization(resCan.getOrganization());
				/*
				if(fnameIdx >= resCan.getExpIdx()){
					//첫번째 탐색
					if(prevIdx==9999){
						prevIdx=resCan.getExpIdx();
						resName = resCan.getName();
					}else{//기존에 탐색된 이름(prevIdx)이 있으면 인덱스값 비교해서 더 가까운애를 넣어줌
						if(prevIdx < resCan.getExpIdx()){
							prevIdx=resCan.getExpIdx();
							resName = resCan.getName();
						}
					}
				}*/
			}
			
			if(String.valueOf(resCan.getName().charAt(0)).equals(fname)){
				checkNameCount++;
				if(checkNameCount>=2)candidateData.setName("stopSerch");
			}
		}
		return candidateData;
	}



}
