package com.KnowledgeExtractor;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;










import com.Enhanced.CandidatesExtractor;
import com.Enhanced.NamedEntityRecognitionor;
import com.Enhanced.S_Divider;
import com.Enhanced.S_Extractor;
import com.Enhanced.StopWordsChecker;
import com.Entity.CandidatesNQ;
import com.Entity.Knowledge;
import com.Entity.Morpheme;
import com.Entity.Pattern;
import com.Entity.PatternStructure;
import com.Entity.SentenceStructure;
import com.Entity.Word;
import com.common.oesLog;

public class KnowledgeExtractor {
	private PatternStructure patternST;
	private SentenceStructure sentenceST;
	private int defPstatus, defMstatus;
	private int nowWordIdx, nowMorpIdx, findWordIdx, findMorpIdx;	
	private SentenceStructure findO = new SentenceStructure();
	private SentenceStructure findS = new SentenceStructure();
	private Word nowWord; Morpheme nowMorpheme;
	private boolean commentFlag=false;
	private boolean smallFlag = false;
	private S_Extractor s_exExtractor;
	private S_Divider s_Divider;
	private ArrayList<CandidatesNQ> candidatesNQ;
	private int findKnowledgeIdx;
	
	public KnowledgeExtractor() {
		super();
		this.s_Divider = new S_Divider();
		this.findO = new SentenceStructure();
		this.findS = new SentenceStructure();

		//초기화
		this.candidatesNQ = new ArrayList<CandidatesNQ>();
		// TODO Auto-generated constructor stub
	}


	public void KnowledgeExtractorInit(){
		this.findO = new SentenceStructure();
		this.findS = new SentenceStructure();
		//초기화

		this.candidatesNQ = new ArrayList<CandidatesNQ>();
	}

	public Knowledge KnowledgeExtractorSequence(PatternStructure PS, SentenceStructure ST) {
		this.patternST =PS;
		this.sentenceST = ST;
		this.defPstatus = PS.Pstatus;
		this.defMstatus = PS.Mstatus;
		this.s_exExtractor = new S_Extractor();

		CandidatesExtractor candidates = new CandidatesExtractor(this.sentenceST);
		this.candidatesNQ = candidates.S_CandidatesExtract(this.candidatesNQ);





		if(this.patternST.isReverseSerch()){//역방향 탐색
			//System.out.println("역방향 탐색 시작");
			//System.out.println("처음");
			Knowledge knowledge = knowlegeReverseSerch();
			knowledge = s_Divider.S_DividerSequence(knowledge, this.candidatesNQ);
			
			if(knowledge.isDieFlag()){//후보군중 성과 매칭되는 이름이 둘 이상이면 해당 뉴스 버림
				SentenceStructure clearST = new SentenceStructure();
				knowledge.setS(clearST);
				knowledge.setO(clearST);
			}
			//knowledge.getCandidateString().
			/*S후보들 로그*/
			String log = "";
			for(int i=0; i< this.candidatesNQ.size(); i++){
				log+= this.candidatesNQ.get(i).getOrganization()+" "+this.candidatesNQ.get(i).getName()+" "+this.candidatesNQ.get(i).getJob()+", ";
			}
			log+="\r\n\r\n";
			knowledge.setCandidateString(log);	

			return knowledge;
		}else{//정방향 탐색
			return null;
		}


	}

	//패턴파일 읽어오기
	public ArrayList<String> PatternFileReader(){
		ArrayList<String> patternList = new ArrayList<String>();
		try {
			InputStream ins = getClass().getResourceAsStream("/com/File/Pattern.txt");
			Reader fr = new InputStreamReader(ins, "utf-8");
			BufferedReader in = new BufferedReader(fr);
			String s;
			while ((s = in.readLine()) != null) {
				patternList.add(s);
			}
			in.close();
		} catch (IOException e) {
			System.err.println(e); // 에러가 있다면 메시지 출력
		}
		return patternList;
	}

	//역방향 탐색
	private Knowledge knowlegeReverseSerch() {	
		Knowledge knowledge = new Knowledge();
		//System.out.println(knowledge.getS().size());
		nowWordIdx = this.sentenceST.getSentenceStructure().size()-1;//마지막 인덱스 역방향 탐색

		for(;nowWordIdx>=0;nowWordIdx--){//워드[어절] 단위 탐색

			nowWord = this.sentenceST.getSentenceStructure().get(nowWordIdx);
			nowMorpIdx = nowWord.getWord().size()-1;

			for(;nowMorpIdx>=0;nowMorpIdx--){//형태소 단위 탐색
				nowMorpheme = nowWord.getWord().get(nowMorpIdx);
				String resultPatternMatch = patternMatchSequence(nowMorpheme);
				/***
				System.out.println(nowMorpheme.getCharacter());
				System.out.println(resultPatternMatch);
				Pattern pattern = this.patternST.getPatternStructure().get(this.patternST.Pstatus);
				Morpheme Pmorpheme = pattern.getPattern().get(this.patternST.Mstatus);
				System.out.println(Pmorpheme.getCharacter());
				 ***/

				if(resultPatternMatch=="O"){
					findO.setSentenceStructure(nowWord);
					//O 탐색 메서드 호출
					KnowledgeSeeking("O");
					Collections.reverse(findO.getSentenceStructure());
					knowledge.setO(findO);
					//초기화
					findO = new SentenceStructure();
				}else if(resultPatternMatch=="S"){
					findS.setSentenceStructure(nowWord);
					//S 탐색 메서드 호출
					KnowledgeSeeking("S");
					Collections.reverse(findS.getSentenceStructure());
					knowledge.setS(findS);
					String stString = "";
					String expString ="";
					for(int xx=0; xx<this.sentenceST.getSentenceStructure().size();xx++){
						Word wd = this.sentenceST.getSentenceStructure().get(xx);
						expString+= wd.getExp();
						for(int yy=0; yy<wd.getWord().size();yy++){
							stString +=wd.getWord().get(yy).getCharacter()+"/"+wd.getWord().get(yy).getTag();

							if(yy!=(wd.getWord().size()-1))stString+="+";
						}
						expString+=" ";
						stString +=" ";
					}
					//stString= new StringBuffer(stString).reverse().toString();
					//expString = new StringBuffer(expString).reverse().toString();
					knowledge.setMopData(stString);
					knowledge.setExpData(expString);
					//초기화
					findS = new SentenceStructure();
					//System.out.println(nowWord.getExp()+this.sentenceST.getSentenceStructure().get(findWordIdx).getExp());
					nowWordIdx = findKnowledgeIdx;
				}				
			}
		}

		return knowledge;
	}

	//패턴 매치 시퀀스
	private String patternMatchSequence(Morpheme morpheme) {
		String resultPatternMatch = "notFound";

		Pattern pattern = this.patternST.getPatternStructure().get(this.patternST.Pstatus);
		Morpheme Pmorpheme = pattern.getPattern().get(this.patternST.Mstatus);
		//System.out.println(Pmorpheme.getCharacter());
		if(pattern.getType()=="S"){//S를 탐색할때 "" 안에 있는 내용은 패스하고 탐색
			//System.out.println(morpheme.getCharacter());
			if(morpheme.getCharacter().equals("\"")&&!commentFlag){//발언 부분에 대해서는 패스
				commentFlag = true;
			}else if(morpheme.getCharacter().equals("\"")&&commentFlag){//두번째 큰따옴표 나옴
				commentFlag = false;
			}else if(morpheme.getCharacter().equals("'")&&!smallFlag){
				smallFlag =true;
			}else if(morpheme.getCharacter().equals("'")&&smallFlag){
				smallFlag = false;
			}else if(!commentFlag&&!smallFlag){//현재 탐색하는 부분이 발언 부분이 아닐때

				if((morpheme.getCharacter().equals("는")&&morpheme.getTag().equals("jxc"))||
						(morpheme.getCharacter().equals("도")&&morpheme.getTag().equals("jxc"))){
					//if(morpheme.getCharacter().equals(Pmorpheme.getCharacter())&&morpheme.getTag().equals(Pmorpheme.getTag())){
					//탐색된 패턴과 일치하는 문구 삭제
					nowMorpheme.setCharacter("");
					nowMorpheme.setTag("");
					
					resultPatternMatch = pattern.getType();
					if(resultPatternMatch.equals("S")){
						if(nowMorpIdx!=0){
							//Morpheme checkMorpheme = nowWord.getWord().get(nowMorpIdx-1);
							if(!s_exExtractor.checkGoS(nowWord,nowMorpIdx-1)) resultPatternMatch="notFound";
						}else{
							resultPatternMatch="notFound";
						}
					}

					if(resultPatternMatch.equals("S")){//S를 찾았을 때..
						this.patternST.Pstatus = this.defPstatus; //디폴트 값으로 원복
						this.patternST.Mstatus = this.defMstatus; //디폴트 값으로 원복
					}

					return resultPatternMatch;

				}else if(morpheme.getCharacter().equals("가")&&morpheme.getTag().equals("jcs")
						&&(nowWordIdx + 1)!=this.sentenceST.getSentenceStructure().size()){
					/**바로 뒤에 발언이 나오거나 앞에 은/도 가 나오지 않으면 추출.**/
					
					if((nowWordIdx + 1)==findWordIdx||!checkJCS()){//바로 다음에 발언부분이 나오거나 문장 맨 앞까지 탐색해서 주격조사가 나오는지 확인
						nowMorpheme.setCharacter("");
						nowMorpheme.setTag("");
						
						resultPatternMatch = pattern.getType();
						if(resultPatternMatch.equals("S")){
							if(nowMorpIdx!=0){
								//Morpheme checkMorpheme = nowWord.getWord().get(nowMorpIdx-1);
								if(!s_exExtractor.checkGoS(nowWord,nowMorpIdx-1)) resultPatternMatch="notFound";
							}else{
								resultPatternMatch="notFound";
							}
						}

						if(resultPatternMatch.equals("S")){//S를 찾았을 때..
							this.patternST.Pstatus = this.defPstatus; //디폴트 값으로 원복
							this.patternST.Mstatus = this.defMstatus; //디폴트 값으로 원복
						}

						return resultPatternMatch;
					}
					
					
				}else{
					
					return resultPatternMatch;
				}
			}
			return resultPatternMatch;
		}else{//O탐색
			if(morpheme.getCharacter().equals(Pmorpheme.getCharacter())&&morpheme.getTag().equals(Pmorpheme.getTag())){
				//탐색된 패턴과 일치하는 문구 삭제
				//발언에 한해서 "며 와 같은 특이한 애들만 예외처리. 추후 수정
				if(nowMorpheme.getCharacter().equals("며")&&nowMorpheme.getTag().equals("JC"))nowWord.setExp(nowWord.getExp().replace("며", ""));
				nowMorpheme.setCharacter("");
				nowMorpheme.setTag("");

				if(this.patternST.Mstatus==0){//패턴의 형태소단위 탐색 끝.

					if(this.patternST.Pstatus==0){
						this.patternST.Pstatus = this.defPstatus; //디폴트 값으로 원복
						this.patternST.Mstatus = this.defMstatus; //디폴트 값으로 원복
					}else{//패턴 포인터 이동, 패턴의 형태소 포인터 설정.
						this.patternST.Pstatus--;
						this.patternST.Mstatus =  this.patternST.getPatternStructure().get(this.patternST.Pstatus)
								.getPattern().size()-1;
					}
					resultPatternMatch = pattern.getType();

					//System.out.println(nowWordIdx+":"+nowMorpIdx);
					//System.out.println(this.sentenceST.getSentenceStructure().get(nowWordIdx).getWord().get(nowMorpIdx).getCharacter());
					//System.out.println(nowMorpheme.getCharacter());
					return resultPatternMatch;
				}else{
					this.patternST.Mstatus--;
					return resultPatternMatch;
				}		
			}else{
				this.patternST.Mstatus =  this.patternST.getPatternStructure().get(this.patternST.Pstatus)
						.getPattern().size()-1;
				return resultPatternMatch;
			}
		}

	}
	private boolean checkJCS(){
		int checkWordIdx = nowWordIdx;
		int checkMorpIdx;
		boolean checkJCScommentFlag = false;
		boolean checkJCSsmallFlag = false;
		Word checkWord;
		Morpheme checkMorp;
		if(checkWordIdx==0)return false;//맨 처음 워드면 바로 리턴
		
		for(;checkWordIdx>=0;checkWordIdx--){//워드[어절] 단위 탐색

			checkWord = this.sentenceST.getSentenceStructure().get(checkWordIdx);
			checkMorpIdx = checkWord.getWord().size()-1;

			for(;checkMorpIdx>=0;checkMorpIdx--){//형태소 단위 탐색
				checkMorp = checkWord.getWord().get(checkMorpIdx);
				
				if(checkMorp.getCharacter().equals("\"")&&!checkJCScommentFlag){//발언 부분에 대해서는 패스
					checkJCScommentFlag = true;
				}else if(checkMorp.getCharacter().equals("\"")&&checkJCScommentFlag){//두번째 큰따옴표 나옴
					checkJCScommentFlag = false;
				}else if(checkMorp.getCharacter().equals("'")&&!checkJCSsmallFlag){
					checkJCSsmallFlag =true;
				}else if(checkMorp.getCharacter().equals("'")&&checkJCSsmallFlag){
					checkJCSsmallFlag = false;
				}else if(!checkJCScommentFlag&&!checkJCSsmallFlag){//현재 탐색하는 부분이 발언 부분이 아닐때
				
				//주격조사 확인
				if((checkMorp.getCharacter().equals("는")&&checkMorp.getTag().equals("jxc"))||
						(checkMorp.getCharacter().equals("도")&&checkMorp.getTag().equals("jxc")))return true;
			
				}
			}
		}
	
		return false;
	}
	//S, O 탐색 메서드
	private void KnowledgeSeeking(String type){
		findWordIdx = nowWordIdx-1;
		findMorpIdx = nowMorpIdx-1;
		Word lnowWord;		Morpheme nowMorpheme;
		boolean seekFlag = false;
		
		//boolean addSeekFlag = false;

		WordLoop:for(;findWordIdx>=0;findWordIdx--){//워드[어절] 단위 탐색
			lnowWord = this.sentenceST.getSentenceStructure().get(findWordIdx);
			//nowMorpIdx = lnowWord.getWord().size()-1;
			findMorpIdx = lnowWord.getWord().size()-1;
			MorphemeLoop:for(;findMorpIdx>=0;findMorpIdx--){//형태소 단위 탐색
				nowMorpheme = lnowWord.getWord().get(findMorpIdx);

				String ctg = this.patternST.getCategory();
				if(ctg.equals("발언")){//발언 추출
					if(type=="O"){
						//System.out.println(findWordIdx+":"+findMorpIdx);
						if(nowMorpheme.getCharacter().equals("\"")){//두번째 "탐색 
							findO.setSentenceStructure(lnowWord);
							//워드 탐색 설정
							nowWordIdx = findWordIdx;
							findKnowledgeIdx = findWordIdx;
							break WordLoop;
						}else if(findWordIdx==0&&findMorpIdx==0){
							//마지막 루프이면서 발언부분 찾지 못했을떄
							nowWordIdx = findWordIdx;
							break WordLoop;
						}else if(findMorpIdx==0){
							findO.setSentenceStructure(lnowWord);
						}

					}else{//"S"추출 아래와 비슷한데....리펙토링 해야함

						if(s_exExtractor.checkGoS(lnowWord,findMorpIdx)&&!s_exExtractor.checkStopS_part(nowMorpheme)){
							findS.setSentenceStructure(lnowWord);
							seekFlag = true;
							//매치되면 형태소 분석 건너뛰고 다음 어절 분석
							break MorphemeLoop;
						}else if(seekFlag){//기존에 탐색된게 있으면서 이후 탐색에 실패했으면 찾은 값을 리턴
							break WordLoop;
						}else{//아무것도 탐색되지 않음
							break WordLoop;
						}	
					}
				}else{//일반적인 카테고리에 대한 지식 추출 과정
					if(s_exExtractor.checkGoS(lnowWord,findMorpIdx)){
						if(type=="S") findS.setSentenceStructure(lnowWord);
						else findO.setSentenceStructure(lnowWord);	
						seekFlag = true;
						//매치되면 형태소 분석 건너뛰고 다음 어절 분석
						break MorphemeLoop;
					}else if(seekFlag){//기존에 탐색된게 있으면서 이후 탐색에 실패했으면 찾은 값을 리턴
						break WordLoop;
					}else{//아무것도 탐색되지 않음
						break WordLoop;
					}	
				}



			}

		}
	}
	

}
