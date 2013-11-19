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
	 * 발언자 후보 추출
	 * - 이름, 직책 추출[태그기반]
	 * - 현 문장에서 기존 후보군으로
	 * - 이름, 직책으로 추정되는 단어 추출[이름 또는 직책기준으로 앞 뒤 탐색]
	 **/
	public ArrayList<CandidatesNQ> S_CandidatesExtract( ArrayList<CandidatesNQ> candidatesNQarr){
		nowWordIdx = this.sentenceST.getSentenceStructure().size()-1;//마지막 인덱스 역방향 탐색
		for(;nowWordIdx>=0;nowWordIdx--){//워드[어절] 단위 탐색
			nowWord = this.sentenceST.getSentenceStructure().get(nowWordIdx);
			nowMorpIdx = nowWord.getWord().size()-1;
			for(;nowMorpIdx>=0;nowMorpIdx--){//형태소 단위 탐색
				nowMorpheme = nowWord.getWord().get(nowMorpIdx);

				//이름탐색
				if(nowMorpheme.getTag().equals("nq")&&nowMorpheme.getCharacter().length()>=2&&nowMorpheme.getCharacter().length()<=4){
					candidatesNQarr = serchName_Job(candidatesNQarr);
				}

			}	
		}

		return candidatesNQarr;
	}

	/**이름을 바탕으로 S후보 추출**/
	public ArrayList<CandidatesNQ> serchName_Job(ArrayList<CandidatesNQ> candidatesNQarr){


		boolean findFlag=false;


		/***
		 * 앞+형태소+뒤 확인[morp단위] 
		 * -> 이름과 직책이 공백없이 붙어있는케이스
		 * -> 朴대통령 같은 케이스 말고는 거의 없는듯
		 * -> 이 방식은 보류
		 * 앞 형태소 뒤 확인[word단위] 
		 * -> 박근혜 대통령 같은 케이스를 박근혜라는 이름이 등록되어 있으면 미등록된 직책을 추출
		 * -> 직책의 경우 이름처럼 길이로 자를수 없기때문에 일단은 등록된 애들 기준으로 탐색
		 ***/
		//기존 데이터 확인
		findArr:for(int i=0; i < candidatesNQarr.size();i++){
			if(candidatesNQarr.get(i).getName().equals(nowMorpheme.getCharacter())){
				if(candidatesNQarr.get(i).getJob()==""){//job이 입력 안되있고 동일한 이름 탐색
					candidatesNQarr.remove(i);
				}else{
					findFlag = true;	
				}
				break findArr;
			}
		}

		if(!findFlag){//기존 데이터에 없으면 추가
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
				if((nextMorpheme.getCharacter().equals("는")&&nextMorpheme.getTag().equals("jxc"))
						||(nextMorpheme.getCharacter().equals("가")&&nextMorpheme.getTag().equals("jcs"))
						||(nextMorpheme.getCharacter().equals("도")&&nextMorpheme.getTag().equals("jxc"))
						||(nextMorpheme.getCharacter().equals("")&&nextMorpheme.getTag().equals(""))
						){
					loopFlag = true;
				}
				if(nextMorpheme.getTag().equals("ncr")){//탐색된 애가 직책이면;
					cadidateData.setJob(nextMorpheme.getCharacter());
					/**if(cadidateData.getOrganization().equals("")&&
							(nextWordIdx+1)!=this.sentenceST.getSentenceStructure().size()&&  //직책은 탐색되었지만 소속 탐색 안됬으면 한번 더 탐색
							!loopFlag){//끝에 은, 는, 이, 가 등이 들어가있으면 루프종료
						두번째 인덱스 탐색  [직책 뒤에는 소속이 오지 않음]
						nextWordIdx++;

						nextWord = this.sentenceST.getSentenceStructure().get(nextWordIdx);
						nextMorpIdx = nextWord.getWord().size()-1;
						for(;nextMorpIdx>=0;nextMorpIdx--){
							nextMorpheme = nextWord.getWord().get(nextMorpIdx);
							if(nextMorpheme.getTag().equals("f")&&nextMorpheme.getCharacter().length()!=1){//탐색된 애가 소속이면;
								cadidateData.setOrganization(nextMorpheme.getCharacter());
								break MorphemeLoop;
							}else if(((nextMorpheme.getCharacter().equals("전")&&nextMorpheme.getTag().equals("nbn")))&&
									(nextWordIdx+1)!=this.sentenceST.getSentenceStructure().size()){**/
								/**최대 3번째 인덱스까지 탐색
								nextWordIdx++;
								nextWord = this.sentenceST.getSentenceStructure().get(nextWordIdx);
								nextMorpIdx = nextWord.getWord().size()-1;
								for(;nextMorpIdx>=0;nextMorpIdx--){
									nextMorpheme = nextWord.getWord().get(nextMorpIdx);
									if(nextMorpheme.getTag().equals("f")&&nextMorpheme.getCharacter().length()!=1){
										cadidateData.setOrganization(nextMorpheme.getCharacter());
										break MorphemeLoop;
									}
								}
							}
						}
					}else{**/
						break MorphemeLoop;
					//}
				}else if(nextMorpheme.getTag().equals("f")&&!loopFlag&&nextMorpheme.getCharacter().length()!=1){
					cadidateData.setOrganization(nextMorpheme.getCharacter());
					/**소속은 탐색되었지만 직책이 탐색 안되었으면**/
					if(cadidateData.getJob().equals("")&&(nextWordIdx+1)!=this.sentenceST.getSentenceStructure().size()){
						/**두번째 인덱스 탐색**/
						nextWordIdx++;
						nextWord = this.sentenceST.getSentenceStructure().get(nextWordIdx);
						nextMorpIdx = nextWord.getWord().size()-1;
						for(;nextMorpIdx>=0;nextMorpIdx--){
							nextMorpheme = nextWord.getWord().get(nextMorpIdx);
							if(nextMorpheme.getTag().equals("ncr")){//탐색된 애가 소속이면;
								cadidateData.setJob(nextMorpheme.getCharacter());
								break MorphemeLoop;
							}else if(((nextMorpheme.getCharacter().equals("전")&&nextMorpheme.getTag().equals("nbn")))&&
									(nextWordIdx+1)!=this.sentenceST.getSentenceStructure().size()){
								/**최대 3번째 인덱스까지 탐색**/
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

				/**직책, 소속 전에 "전"이 먼저 나오면**/
				else if((nextMorpheme.getCharacter().equals("전")&&nextMorpheme.getTag().equals("nbn"))&&
						(nextWordIdx+1)!=this.sentenceST.getSentenceStructure().size()){//전 대통령, 전 국저원장 같은 예외처리
					nextWordIdx++;
					nextWord = this.sentenceST.getSentenceStructure().get(nextWordIdx);
					nextMorpIdx = nextWord.getWord().size()-1;
					for(;nextMorpIdx>=0;nextMorpIdx--){
						nextMorpheme = nextWord.getWord().get(nextMorpIdx);
						if(nextMorpheme.getTag().equals("ncr")){//탐색된 애가 직책이면;

							cadidateData.setJob(nextMorpheme.getCharacter());
							/**if(cadidateData.getOrganization().equals("")&&  직책 다음에 소속이 오지 않음[의원 민주당]
									(nextWordIdx+1)!=this.sentenceST.getSentenceStructure().size()){//직책은 탐색되었지만 소속 탐색 안됬으면 한번 더 탐색
								두번째 인덱스 탐색
								nextWordIdx++;
								nextWord = this.sentenceST.getSentenceStructure().get(nextWordIdx);
								nextMorpIdx = nextWord.getWord().size()-1;
								for(;nextMorpIdx>=0;nextMorpIdx--){
									nextMorpheme = nextWord.getWord().get(nextMorpIdx);
									if(nextMorpheme.getTag().equals("f")&&nextMorpheme.getCharacter().length()!=1){//탐색된 애가 소속이면;
										cadidateData.setOrganization(nextMorpheme.getCharacter());
										break MorphemeLoop;
									}
								}
							}else{**/
								break MorphemeLoop;
							//}
						}else if(nextMorpheme.getTag().equals("f")&&nextMorpheme.getCharacter().length()!=1){
							cadidateData.setOrganization(nextMorpheme.getCharacter());
							/**소속은 탐색되었지만 직책이 탐색 안되었으면**/
							if(cadidateData.getJob().equals("")&&(nextWordIdx+1)!=this.sentenceST.getSentenceStructure().size()){
								/**두번째 인덱스 탐색**/
								nextWordIdx++;
								nextWord = this.sentenceST.getSentenceStructure().get(nextWordIdx);
								nextMorpIdx = nextWord.getWord().size()-1;
								for(;nextMorpIdx>=0;nextMorpIdx--){
									nextMorpheme = nextWord.getWord().get(nextMorpIdx);
									if(nextMorpheme.getTag().equals("ncr")){//탐색된 애가 소속이면;
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
				if((prevMorpheme.getCharacter().equals("는")&&prevMorpheme.getTag().equals("jxc"))
						||(prevMorpheme.getCharacter().equals("가")&&prevMorpheme.getTag().equals("jcs"))
						||(prevMorpheme.getCharacter().equals("도")&&prevMorpheme.getTag().equals("jxc"))
						||(prevMorpheme.getCharacter().equals("")&&prevMorpheme.getTag().equals(""))
						){
					loopFlag = true;
				}else if(prevMorpheme.getTag().equals("ncr")&&cadidateData.getJob()==""&&!loopFlag){//탐색된 애가 직책이면;
					cadidateData.setJob(prevMorpheme.getCharacter());
					if(cadidateData.getOrganization().equals("")&&
							(prevWordIdx-1)!=-1){//직책은 탐색되었지만 소속 탐색 안됬으면 한번 더 탐색
						/**두번째 인덱스 탐색**/
						prevWordIdx--;
						prevWord = this.sentenceST.getSentenceStructure().get(prevWordIdx);
						prevMorpIdx = prevWord.getWord().size()-1;
						for(;prevMorpIdx>=0;prevMorpIdx--){
							prevMorpheme = prevWord.getWord().get(prevMorpIdx);
							if(prevMorpheme.getTag().equals("f")&&prevMorpheme.getCharacter().length()!=1){//탐색된 애가 소속이면;
								cadidateData.setOrganization(prevMorpheme.getCharacter());
								break MorphemeLoop;
							}else if(((prevMorpheme.getCharacter().equals("전")&&prevMorpheme.getTag().equals("nbn")))&&
									(prevWordIdx-1)!=-1){
								/**최대 3번째 인덱스까지 탐색**/
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
					/**소속은 탐색되었지만 직책이 탐색 안되었으면  [의원 민주당 오은석] 이런식으로 오진 않음
					if(cadidateData.getJob().equals("")&&(prevWordIdx-1)!=-1){**/
						/**두번째 인덱스 탐색
						prevWordIdx--;
						prevWord = this.sentenceST.getSentenceStructure().get(prevWordIdx);
						prevMorpIdx = prevWord.getWord().size()-1;
						for(;prevMorpIdx>=0;prevMorpIdx--){
							prevMorpheme = prevWord.getWord().get(prevMorpIdx);
							if(prevMorpheme.getTag().equals("ncr")){//탐색된 애가 소속이면;
								cadidateData.setJob(prevMorpheme.getCharacter());
								break MorphemeLoop;
							}else if(((prevMorpheme.getCharacter().equals("전")&&prevMorpheme.getTag().equals("nbn")))&&
									(prevWordIdx-1)!=-1){**/
								/**최대 3번째 인덱스까지 탐색
								prevWordIdx--;
								prevWord = this.sentenceST.getSentenceStructure().get(prevWordIdx);
								prevMorpIdx = prevWord.getWord().size()-1;
								for(;prevMorpIdx>=0;prevMorpIdx--){
									prevMorpheme = prevWord.getWord().get(prevMorpIdx);
									if(prevMorpheme.getTag().equals("ncr")){
										cadidateData.setJob(prevMorpheme.getCharacter());
										break MorphemeLoop;
									}
								}
							}
						}
					}else{**/
						break MorphemeLoop;
					//}



				}else if((
						((prevMorpheme.getCharacter().equals("전")&&prevMorpheme.getTag().equals("nbn"))||
								(prevMorpheme.getCharacter().equals("소속")&&prevMorpheme.getTag().equals("ncn")))
								&&
								(cadidateData.getOrganization()==""||cadidateData.getJob()==""))
								&&(prevWordIdx-1)!=-1){

					prevWordIdx--;
					prevWord = this.sentenceST.getSentenceStructure().get(prevWordIdx);
					prevMorpIdx = prevWord.getWord().size()-1;
					for(;prevMorpIdx>=0;prevMorpIdx--){
						prevMorpheme = prevWord.getWord().get(prevMorpIdx);
						if(prevMorpheme.getTag().equals("ncr")&&cadidateData.getJob()==""){//탐색된 애가 직책이면;

							cadidateData.setJob(prevMorpheme.getCharacter());
							if(cadidateData.getOrganization().equals("")&&
									(prevWordIdx-1)!=-1){//직책은 탐색되었지만 소속 탐색 안됬으면 한번 더 탐색
								/**두번째 인덱스 탐색**/
								prevWordIdx--;
								prevWord = this.sentenceST.getSentenceStructure().get(prevWordIdx);
								prevMorpIdx = prevWord.getWord().size()-1;
								for(;prevMorpIdx>=0;prevMorpIdx--){
									prevMorpheme = prevWord.getWord().get(prevMorpIdx);
									if(prevMorpheme.getTag().equals("f")&&prevMorpheme.getCharacter().length()!=1){//탐색된 애가 소속이면;
										cadidateData.setOrganization(prevMorpheme.getCharacter());
										break MorphemeLoop;
									}
								}
							}else{
								break MorphemeLoop;
							}
						}else if(prevMorpheme.getTag().equals("f")&&cadidateData.getOrganization()==""&&prevMorpheme.getCharacter().length()!=1){
							cadidateData.setOrganization(prevMorpheme.getCharacter());
							/**소속은 탐색되었지만 직책이 탐색 안되었으면
							if(cadidateData.getJob().equals("")&&(prevWordIdx-1)!=-1){**/
								/**두번째 인덱스 탐색
								prevWordIdx--;
								prevWord = this.sentenceST.getSentenceStructure().get(prevWordIdx);
								prevMorpIdx = prevWord.getWord().size()-1;
								for(;prevMorpIdx>=0;prevMorpIdx--){
									prevMorpheme = prevWord.getWord().get(prevMorpIdx);
									if(prevMorpheme.getTag().equals("ncr")){//탐색된 애가 소속이면;
										cadidateData.setJob(prevMorpheme.getCharacter());
										break MorphemeLoop;
									}
								}
							}else{**/
								break MorphemeLoop;
							//}
						}
					}
				}
			}
		}

		return cadidateData;
	}
}
