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

			int nowWordIdx = S.getSentenceStructure().size()-1;//������ �ε��� ������ Ž��

			for(;nowWordIdx>=0;nowWordIdx--){//����[����] ���� Ž��
				Word nowWord = S.getSentenceStructure().get(nowWordIdx);
				//String jobData = S.getKnowledgeS_job();
				int nowMorpIdx = nowWord.getWord().size()-1;


				for(;nowMorpIdx>=0;nowMorpIdx--){//���¼� ���� Ž��
					Morpheme nowMorpheme = nowWord.getWord().get(nowMorpIdx);
					if(nowMorpheme.getTag().equals("f")&&
							knowledge.getS().get(b).getKnowledgeS_organization().equals("")&&
							nowMorpheme.getCharacter().length()!=1){//�����, 1���ڰ� �ƴ�
						knowledge.getS().get(b).setKnowledgeS_organization(nowMorpheme.getCharacter());
					}else if(nowMorpheme.getTag().equals("nq")&&knowledge.getS().get(b).getKnowledgeS_name().equals("")){//�̸�
						knowledge.getS().get(b).setKnowledgeS_name(nowMorpheme.getCharacter());
						checkName = true;
					}else if(nowMorpheme.getTag().equals("ncr")&&knowledge.getS().get(b).getKnowledgeS_job().equals("")){//��å
						knowledge.getS().get(b).setKnowledgeS_job(nowMorpheme.getCharacter());
					}else{//�̺з�, ������ ���� ���� ����Ͱ��� �ֵ� ����
						if(nowMorpheme.getCharacter().length()==1&&!nowMorpheme.getTag().equals("jcs")&&
								!nowMorpheme.getTag().equals("ecx")&&!nowMorpheme.getTag().equals("pvg")){
							if(this.namedEntityRecognitionor.matchFirstName(nowMorpheme.getCharacter())){
								//�̺κп��� S�ĺ���[�̸�]�� ���� ��ġ
								CandidatesNQ resMFN = matchFirstname(nowMorpheme.getCharacter(),nowWord.getExpIdx(), candidatesNQ,"",true);
								//System.out.println(resMFN);
								if(resMFN.getName().equals("stopSerch")){
									knowledge.setDieFlag(true);
								}else if(!resMFN.getName().equals("none")){
									firstName = resMFN.getName();
									organization = resMFN.getOrganization();
									job = resMFN.getJob();
								}else{//�̸�Ž�� �����ϸ� �׳� ���� �Է�
									firstName = nowMorpheme.getCharacter();
								}

							}else{//���� �ƴ� �ѱ���¥����
								knowledge.getS().get(b).setKnowledgeS_unknown(nowMorpheme.getCharacter());
							}
						}else{
							knowledge.getS().get(b).setKnowledgeS_unknown(nowMorpheme.getCharacter());
						}

					}
				}

				//���� 2���ڵ� �����ؾ���
				
				if(nowWord.getExp().length()==1){//�̸��� 1����¥��
					if(this.namedEntityRecognitionor.matchFirstName(nowWord.getExp())){
						String jobString = S.getKnowledgeS_job();
						CandidatesNQ resMFN = matchFirstname(nowWord.getExp(),nowWord.getExpIdx(), candidatesNQ,jobString,true);
						if(resMFN.getName().equals("stopSerch")){
							knowledge.setDieFlag(true);
						}else if(!resMFN.getName().equals("none")){
							firstName = resMFN.getName();
							organization = resMFN.getOrganization();
							job = resMFN.getJob();
						}else{//�̸�Ž�� �����ϸ� �׳� ���� �Է�
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

				if(firstName!=""&&!checkName){//�̸��� Ž�� �ȵǰ� ���� Ž���� �Ǿ�����...
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
					){//���� �̸�, ��å�� ��
				//�ּ��� ������ �̸��� expIdx�� ���ƾ���[������ ����ؼ� ǥ���ϱ� ���� ���� �̸��� ��޵� �� ���Ǳ� ����]
				prevIdx=resCan.getExpIdx();
				candidateData.setName(resCan.getName());
				candidateData.setJob(resCan.getJob());
				candidateData.setOrganization(resCan.getOrganization());
				/*
				if(fnameIdx >= resCan.getExpIdx()){
					//ù��° Ž��
					if(prevIdx==9999){
						prevIdx=resCan.getExpIdx();
						resName = resCan.getName();
					}else{//������ Ž���� �̸�(prevIdx)�� ������ �ε����� ���ؼ� �� �����ָ� �־���
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
