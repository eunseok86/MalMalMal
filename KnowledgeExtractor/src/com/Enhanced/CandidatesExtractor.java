package com.Enhanced;

import java.util.ArrayList;

import com.Entity.Morpheme;
import com.Entity.SentenceStructure;
import com.Entity.Word;
import com.Entity.CandidatesNQ;
import com.common.oesLog;

public class CandidatesExtractor {
	private SentenceStructure sentenceST;
	private int nowWordIdx, nowMorpIdx, findWordIdx, findMorpIdx;	
	private Word nowWord; Morpheme nowMorpheme;


	public CandidatesExtractor() {
		super();
	}

	public CandidatesExtractor(SentenceStructure ST) {
		super();
		this.sentenceST = ST;

	}


	/**
	 * �߾��� �ĺ� ����
	 * - �̸�, ��å ����[�±ױ���]
	 * - �� ���忡�� ���� �ĺ�������
	 * - �̸�, ��å���� �����Ǵ� �ܾ� ����[�̸� �Ǵ� ��å�������� �� �� Ž��]
	 **/
	public ArrayList<CandidatesNQ> S_CandidatesExtract( ArrayList<CandidatesNQ> candidatesNQarr){
		nowWordIdx = this.sentenceST.getSentenceStructure().size()-1;//������ �ε��� ������ Ž��
		for(;nowWordIdx>=0;nowWordIdx--){//����[����] ���� Ž��
			nowWord = this.sentenceST.getSentenceStructure().get(nowWordIdx);
			nowMorpIdx = nowWord.getWord().size()-1;
			for(;nowMorpIdx>=0;nowMorpIdx--){//���¼� ���� Ž��
				nowMorpheme = nowWord.getWord().get(nowMorpIdx);

				//�̸�Ž��
				if(nowMorpheme.getTag().equals("nq")&&nowMorpheme.getCharacter().length()>=2&&nowMorpheme.getCharacter().length()<=4){
					candidatesNQarr = serchName_Job(candidatesNQarr);
				}

			}	
		}

		return candidatesNQarr;
	}

	/**�̸��� �������� S�ĺ� ����**/
	public ArrayList<CandidatesNQ> serchName_Job(ArrayList<CandidatesNQ> candidatesNQarr){


		boolean findFlag=false;


		/***
		 * ��+���¼�+�� Ȯ��[morp����] 
		 * -> �̸��� ��å�� �������� �پ��ִ����̽�
		 * -> �Ӵ����� ���� ���̽� ������ ���� ���µ�
		 * -> �� ������ ����
		 * �� ���¼� �� Ȯ��[word����] 
		 * -> �ڱ��� ������ ���� ���̽��� �ڱ������� �̸��� ���ϵǾ� ������ �̵��ϵ� ��å�� ����
		 * -> ��å�� ���� �̸�ó�� ���̷� �ڸ��� ���⶧���� �ϴ��� ���ϵ� �ֵ� �������� Ž��
		 ***/
		//���� ������ Ȯ��
		findArr:for(int i=0; i < candidatesNQarr.size();i++){
			if(candidatesNQarr.get(i).getName().equals(nowMorpheme.getCharacter())){
				if(candidatesNQarr.get(i).getJob()==""){//job�� �Է� �ȵ��ְ� ������ �̸� Ž��
					candidatesNQarr.remove(i);
				}else{
					findFlag = true;	
				}
				break findArr;
			}
		}

		if(!findFlag){//���� �����Ϳ� ������ �߰�
			CandidatesNQ cadidateData = new CandidatesNQ(nowMorpheme.getCharacter(), nowWord.getExpIdx());
			cadidateData = findSeq(cadidateData);

			candidatesNQarr.add(cadidateData);

		}

		return candidatesNQarr;
	}


	public CandidatesNQ findSeq(CandidatesNQ cadidateData){
		int prevWordIdx = nowWordIdx-1;
		int prevMorpIdx;
		int nextWordIdx = nowWordIdx+1;
		int nextMorpIdx;
		Word prevWord;
		Word nextWord;

		if(prevWordIdx!=-1){
			prevWord = this.sentenceST.getSentenceStructure().get(prevWordIdx);
			prevMorpIdx = prevWord.getWord().size()-1;
		}else {
			prevWord = null;
			prevMorpIdx=0;
		}

		if(nextWordIdx!=this.sentenceST.getSentenceStructure().size()){
			nextWord = this.sentenceST.getSentenceStructure().get(nextWordIdx);
			nextMorpIdx = nextWord.getWord().size()-1;
		}else {
			nextWord = null;
			nextMorpIdx=0;
		}

		/**prev now next**/
		Morpheme prevMorpheme, nextMorpheme;
		boolean loopFlag = false;
		if(nextWord!=null){
			MorphemeLoop:for(;nextMorpIdx>=0;nextMorpIdx--){
				nextMorpheme = nextWord.getWord().get(nextMorpIdx);
				if((nextMorpheme.getCharacter().equals("��")&&nextMorpheme.getTag().equals("jxc"))
						||(nextMorpheme.getCharacter().equals("��")&&nextMorpheme.getTag().equals("jcs"))
						||(nextMorpheme.getCharacter().equals("��")&&nextMorpheme.getTag().equals("jxc"))
						||(nextMorpheme.getCharacter().equals("")&&nextMorpheme.getTag().equals(""))
						){
					loopFlag = true;
				}
				if(nextMorpheme.getTag().equals("ncr")){//Ž���� �ְ� ��å�̸�;
					cadidateData.setJob(nextMorpheme.getCharacter());
					break MorphemeLoop;
					
				}else if(nextMorpheme.getTag().equals("f")&&!loopFlag&&nextMorpheme.getCharacter().length()!=1){
					cadidateData.setOrganization(nextMorpheme.getCharacter());
					/**�Ҽ��� Ž���Ǿ����� ��å�� Ž�� �ȵǾ�����**/
					if(cadidateData.getJob().equals("")&&(nextWordIdx+1)!=this.sentenceST.getSentenceStructure().size()){
						/**�ι�° �ε��� Ž��**/
						nextWordIdx++;
						nextWord = this.sentenceST.getSentenceStructure().get(nextWordIdx);
						nextMorpIdx = nextWord.getWord().size()-1;
						for(;nextMorpIdx>=0;nextMorpIdx--){
							nextMorpheme = nextWord.getWord().get(nextMorpIdx);
							if(nextMorpheme.getTag().equals("ncr")){//Ž���� �ְ� �Ҽ��̸�;
								cadidateData.setJob(nextMorpheme.getCharacter());
								break MorphemeLoop;
							}else if(((nextMorpheme.getCharacter().equals("��")&&nextMorpheme.getTag().equals("nbn")))&&
									(nextWordIdx+1)!=this.sentenceST.getSentenceStructure().size()){
								/**�ִ� 3��° �ε������� Ž��**/
								nextWordIdx++;
								nextWord = this.sentenceST.getSentenceStructure().get(nextWordIdx);
								nextMorpIdx = nextWord.getWord().size()-1;
								for(;nextMorpIdx>=0;nextMorpIdx--){
									nextMorpheme = nextWord.getWord().get(nextMorpIdx);
									if(nextMorpheme.getTag().equals("ncr")){
										cadidateData.setJob(nextMorpheme.getCharacter());
										break MorphemeLoop;
									}
								}
							}
						}
					}else{
						break MorphemeLoop;
					}
				}

				/**��å, �Ҽ� ���� "��"�� ���� ������**/
				else if((nextMorpheme.getCharacter().equals("��")&&nextMorpheme.getTag().equals("nbn"))&&
						(nextWordIdx+1)!=this.sentenceST.getSentenceStructure().size()){//�� ������, �� �������� ���� ����ó��
					nextWordIdx++;
					nextWord = this.sentenceST.getSentenceStructure().get(nextWordIdx);
					nextMorpIdx = nextWord.getWord().size()-1;
					for(;nextMorpIdx>=0;nextMorpIdx--){
						nextMorpheme = nextWord.getWord().get(nextMorpIdx);
						if(nextMorpheme.getTag().equals("ncr")){//Ž���� �ְ� ��å�̸�;

							cadidateData.setJob(nextMorpheme.getCharacter());
						
							break MorphemeLoop;
							
						}else if(nextMorpheme.getTag().equals("f")&&nextMorpheme.getCharacter().length()!=1){
							cadidateData.setOrganization(nextMorpheme.getCharacter());
							/**�Ҽ��� Ž���Ǿ����� ��å�� Ž�� �ȵǾ�����**/
							if(cadidateData.getJob().equals("")&&(nextWordIdx+1)!=this.sentenceST.getSentenceStructure().size()){
								/**�ι�° �ε��� Ž��**/
								nextWordIdx++;
								nextWord = this.sentenceST.getSentenceStructure().get(nextWordIdx);
								nextMorpIdx = nextWord.getWord().size()-1;
								for(;nextMorpIdx>=0;nextMorpIdx--){
									nextMorpheme = nextWord.getWord().get(nextMorpIdx);
									if(nextMorpheme.getTag().equals("ncr")){//Ž���� �ְ� �Ҽ��̸�;
										cadidateData.setJob(nextMorpheme.getCharacter());
										break MorphemeLoop;
									}
								}
							}else{
								break MorphemeLoop;
							}
						}
					}
				}
			}
		}

		
		loopFlag = false;
		if((cadidateData.getJob()==""||cadidateData.getOrganization()=="")&&prevWord!=null){
			MorphemeLoop:for(;prevMorpIdx>=0;prevMorpIdx--){
				prevMorpheme = prevWord.getWord().get(prevMorpIdx);
				if((prevMorpheme.getCharacter().equals("��")&&prevMorpheme.getTag().equals("jxc"))
						||(prevMorpheme.getCharacter().equals("��")&&prevMorpheme.getTag().equals("jcs"))
						||(prevMorpheme.getCharacter().equals("��")&&prevMorpheme.getTag().equals("jxc"))
						||(prevMorpheme.getCharacter().equals("")&&prevMorpheme.getTag().equals(""))
						){
					loopFlag = true;
				}else if(prevMorpheme.getTag().equals("ncr")&&cadidateData.getJob()==""&&!loopFlag){//Ž���� �ְ� ��å�̸�;
					cadidateData.setJob(prevMorpheme.getCharacter());
					if(cadidateData.getOrganization().equals("")&&
							(prevWordIdx-1)!=-1){//��å�� Ž���Ǿ����� �Ҽ� Ž�� �ȉ����� �ѹ� �� Ž��
						/**�ι�° �ε��� Ž��**/
						prevWordIdx--;
						prevWord = this.sentenceST.getSentenceStructure().get(prevWordIdx);
						prevMorpIdx = prevWord.getWord().size()-1;
						for(;prevMorpIdx>=0;prevMorpIdx--){
							prevMorpheme = prevWord.getWord().get(prevMorpIdx);
							if(prevMorpheme.getTag().equals("f")&&prevMorpheme.getCharacter().length()!=1){//Ž���� �ְ� �Ҽ��̸�;
								cadidateData.setOrganization(prevMorpheme.getCharacter());
								break MorphemeLoop;
							}else if(((prevMorpheme.getCharacter().equals("��")&&prevMorpheme.getTag().equals("nbn")))&&
									(prevWordIdx-1)!=-1){
								/**�ִ� 3��° �ε������� Ž��**/
								prevWordIdx--;
								prevWord = this.sentenceST.getSentenceStructure().get(prevWordIdx);
								prevMorpIdx = prevWord.getWord().size()-1;
								for(;prevMorpIdx>=0;prevMorpIdx--){
									prevMorpheme = prevWord.getWord().get(prevMorpIdx);
									if(prevMorpheme.getTag().equals("f")&&prevMorpheme.getCharacter().length()!=1){
										cadidateData.setOrganization(prevMorpheme.getCharacter());
										break MorphemeLoop;
									}
								}
							}
						}
					}else{
						break MorphemeLoop;
					}
				}else if(prevMorpheme.getTag().equals("f")&&cadidateData.getOrganization()==""&&!loopFlag&&prevMorpheme.getCharacter().length()!=1){
					cadidateData.setOrganization(prevMorpheme.getCharacter());
				
					break MorphemeLoop;
		
				}else if((
						((prevMorpheme.getCharacter().equals("��")&&prevMorpheme.getTag().equals("nbn"))||
								(prevMorpheme.getCharacter().equals("�Ҽ�")&&prevMorpheme.getTag().equals("ncn")))
								&&
								(cadidateData.getOrganization()==""||cadidateData.getJob()==""))
								&&(prevWordIdx-1)!=-1){

					prevWordIdx--;
					prevWord = this.sentenceST.getSentenceStructure().get(prevWordIdx);
					prevMorpIdx = prevWord.getWord().size()-1;
					for(;prevMorpIdx>=0;prevMorpIdx--){
						prevMorpheme = prevWord.getWord().get(prevMorpIdx);
						if(prevMorpheme.getTag().equals("ncr")&&cadidateData.getJob()==""){//Ž���� �ְ� ��å�̸�;

							cadidateData.setJob(prevMorpheme.getCharacter());
							if(cadidateData.getOrganization().equals("")&&
									(prevWordIdx-1)!=-1){//��å�� Ž���Ǿ����� �Ҽ� Ž�� �ȉ����� �ѹ� �� Ž��
								/**�ι�° �ε��� Ž��**/
								prevWordIdx--;
								prevWord = this.sentenceST.getSentenceStructure().get(prevWordIdx);
								prevMorpIdx = prevWord.getWord().size()-1;
								for(;prevMorpIdx>=0;prevMorpIdx--){
									prevMorpheme = prevWord.getWord().get(prevMorpIdx);
									if(prevMorpheme.getTag().equals("f")&&prevMorpheme.getCharacter().length()!=1){//Ž���� �ְ� �Ҽ��̸�;
										cadidateData.setOrganization(prevMorpheme.getCharacter());
										break MorphemeLoop;
									}
								}
							}else{
								break MorphemeLoop;
							}
						}else if(prevMorpheme.getTag().equals("f")&&cadidateData.getOrganization()==""&&prevMorpheme.getCharacter().length()!=1){
							cadidateData.setOrganization(prevMorpheme.getCharacter());
						
							break MorphemeLoop;
							
						}
					}
				}
			}
		}

		return cadidateData;
	}
}
