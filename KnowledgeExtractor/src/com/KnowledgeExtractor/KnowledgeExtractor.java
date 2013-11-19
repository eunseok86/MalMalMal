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

		//�ʱ�ȭ
		this.candidatesNQ = new ArrayList<CandidatesNQ>();
		// TODO Auto-generated constructor stub
	}


	public void KnowledgeExtractorInit(){
		this.findO = new SentenceStructure();
		this.findS = new SentenceStructure();
		//�ʱ�ȭ

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





		if(this.patternST.isReverseSerch()){//������ Ž��
			//System.out.println("������ Ž�� ����");
			//System.out.println("ó��");
			Knowledge knowledge = knowlegeReverseSerch();
			knowledge = s_Divider.S_DividerSequence(knowledge, this.candidatesNQ);
			
			if(knowledge.isDieFlag()){//�ĺ����� ���� ��Ī�Ǵ� �̸��� �� �̻��̸� �ش� ���� ����
				SentenceStructure clearST = new SentenceStructure();
				knowledge.setS(clearST);
				knowledge.setO(clearST);
			}
			//knowledge.getCandidateString().
			/*S�ĺ��� �α�*/
			String log = "";
			for(int i=0; i< this.candidatesNQ.size(); i++){
				log+= this.candidatesNQ.get(i).getOrganization()+" "+this.candidatesNQ.get(i).getName()+" "+this.candidatesNQ.get(i).getJob()+", ";
			}
			log+="\r\n\r\n";
			knowledge.setCandidateString(log);	

			return knowledge;
		}else{//������ Ž��
			return null;
		}


	}

	//�������� �о����
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
			System.err.println(e); // ������ �ִٸ� �޽��� ���
		}
		return patternList;
	}

	//������ Ž��
	private Knowledge knowlegeReverseSerch() {	
		Knowledge knowledge = new Knowledge();
		//System.out.println(knowledge.getS().size());
		nowWordIdx = this.sentenceST.getSentenceStructure().size()-1;//������ �ε��� ������ Ž��

		for(;nowWordIdx>=0;nowWordIdx--){//����[����] ���� Ž��

			nowWord = this.sentenceST.getSentenceStructure().get(nowWordIdx);
			nowMorpIdx = nowWord.getWord().size()-1;

			for(;nowMorpIdx>=0;nowMorpIdx--){//���¼� ���� Ž��
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
					//O Ž�� �޼��� ȣ��
					KnowledgeSeeking("O");
					Collections.reverse(findO.getSentenceStructure());
					knowledge.setO(findO);
					//�ʱ�ȭ
					findO = new SentenceStructure();
				}else if(resultPatternMatch=="S"){
					findS.setSentenceStructure(nowWord);
					//S Ž�� �޼��� ȣ��
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
					//�ʱ�ȭ
					findS = new SentenceStructure();
					//System.out.println(nowWord.getExp()+this.sentenceST.getSentenceStructure().get(findWordIdx).getExp());
					nowWordIdx = findKnowledgeIdx;
				}				
			}
		}

		return knowledge;
	}

	//���� ��ġ ������
	private String patternMatchSequence(Morpheme morpheme) {
		String resultPatternMatch = "notFound";

		Pattern pattern = this.patternST.getPatternStructure().get(this.patternST.Pstatus);
		Morpheme Pmorpheme = pattern.getPattern().get(this.patternST.Mstatus);
		//System.out.println(Pmorpheme.getCharacter());
		if(pattern.getType()=="S"){//S�� Ž���Ҷ� "" �ȿ� �ִ� ������ �н��ϰ� Ž��
			//System.out.println(morpheme.getCharacter());
			if(morpheme.getCharacter().equals("\"")&&!commentFlag){//�߾� �κп� ���ؼ��� �н�
				commentFlag = true;
			}else if(morpheme.getCharacter().equals("\"")&&commentFlag){//�ι�° ū����ǥ ����
				commentFlag = false;
			}else if(morpheme.getCharacter().equals("'")&&!smallFlag){
				smallFlag =true;
			}else if(morpheme.getCharacter().equals("'")&&smallFlag){
				smallFlag = false;
			}else if(!commentFlag&&!smallFlag){//���� Ž���ϴ� �κ��� �߾� �κ��� �ƴҶ�

				if((morpheme.getCharacter().equals("��")&&morpheme.getTag().equals("jxc"))||
						(morpheme.getCharacter().equals("��")&&morpheme.getTag().equals("jxc"))){
					//if(morpheme.getCharacter().equals(Pmorpheme.getCharacter())&&morpheme.getTag().equals(Pmorpheme.getTag())){
					//Ž���� ���ϰ� ��ġ�ϴ� ���� ����
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

					if(resultPatternMatch.equals("S")){//S�� ã���� ��..
						this.patternST.Pstatus = this.defPstatus; //����Ʈ ������ ����
						this.patternST.Mstatus = this.defMstatus; //����Ʈ ������ ����
					}

					return resultPatternMatch;

				}else if(morpheme.getCharacter().equals("��")&&morpheme.getTag().equals("jcs")
						&&(nowWordIdx + 1)!=this.sentenceST.getSentenceStructure().size()){
					/**�ٷ� �ڿ� �߾��� �����ų� �տ� ��/�� �� ������ ������ ����.**/
					
					if((nowWordIdx + 1)==findWordIdx||!checkJCS()){//�ٷ� ������ �߾�κ��� �����ų� ���� �� �ձ��� Ž���ؼ� �ְ����簡 �������� Ȯ��
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

						if(resultPatternMatch.equals("S")){//S�� ã���� ��..
							this.patternST.Pstatus = this.defPstatus; //����Ʈ ������ ����
							this.patternST.Mstatus = this.defMstatus; //����Ʈ ������ ����
						}

						return resultPatternMatch;
					}
					
					
				}else{
					
					return resultPatternMatch;
				}
			}
			return resultPatternMatch;
		}else{//OŽ��
			if(morpheme.getCharacter().equals(Pmorpheme.getCharacter())&&morpheme.getTag().equals(Pmorpheme.getTag())){
				//Ž���� ���ϰ� ��ġ�ϴ� ���� ����
				//�߾� ���ؼ� "�� �� ���� Ư���� �ֵ鸸 ����ó��. ���� ����
				if(nowMorpheme.getCharacter().equals("��")&&nowMorpheme.getTag().equals("JC"))nowWord.setExp(nowWord.getExp().replace("��", ""));
				nowMorpheme.setCharacter("");
				nowMorpheme.setTag("");

				if(this.patternST.Mstatus==0){//������ ���¼Ҵ��� Ž�� ��.

					if(this.patternST.Pstatus==0){
						this.patternST.Pstatus = this.defPstatus; //����Ʈ ������ ����
						this.patternST.Mstatus = this.defMstatus; //����Ʈ ������ ����
					}else{//���� ������ �̵�, ������ ���¼� ������ ����.
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
		if(checkWordIdx==0)return false;//�� ó�� ����� �ٷ� ����
		
		for(;checkWordIdx>=0;checkWordIdx--){//����[����] ���� Ž��

			checkWord = this.sentenceST.getSentenceStructure().get(checkWordIdx);
			checkMorpIdx = checkWord.getWord().size()-1;

			for(;checkMorpIdx>=0;checkMorpIdx--){//���¼� ���� Ž��
				checkMorp = checkWord.getWord().get(checkMorpIdx);
				
				if(checkMorp.getCharacter().equals("\"")&&!checkJCScommentFlag){//�߾� �κп� ���ؼ��� �н�
					checkJCScommentFlag = true;
				}else if(checkMorp.getCharacter().equals("\"")&&checkJCScommentFlag){//�ι�° ū����ǥ ����
					checkJCScommentFlag = false;
				}else if(checkMorp.getCharacter().equals("'")&&!checkJCSsmallFlag){
					checkJCSsmallFlag =true;
				}else if(checkMorp.getCharacter().equals("'")&&checkJCSsmallFlag){
					checkJCSsmallFlag = false;
				}else if(!checkJCScommentFlag&&!checkJCSsmallFlag){//���� Ž���ϴ� �κ��� �߾� �κ��� �ƴҶ�
				
				//�ְ����� Ȯ��
				if((checkMorp.getCharacter().equals("��")&&checkMorp.getTag().equals("jxc"))||
						(checkMorp.getCharacter().equals("��")&&checkMorp.getTag().equals("jxc")))return true;
			
				}
			}
		}
	
		return false;
	}
	//S, O Ž�� �޼���
	private void KnowledgeSeeking(String type){
		findWordIdx = nowWordIdx-1;
		findMorpIdx = nowMorpIdx-1;
		Word lnowWord;		Morpheme nowMorpheme;
		boolean seekFlag = false;
		
		//boolean addSeekFlag = false;

		WordLoop:for(;findWordIdx>=0;findWordIdx--){//����[����] ���� Ž��
			lnowWord = this.sentenceST.getSentenceStructure().get(findWordIdx);
			//nowMorpIdx = lnowWord.getWord().size()-1;
			findMorpIdx = lnowWord.getWord().size()-1;
			MorphemeLoop:for(;findMorpIdx>=0;findMorpIdx--){//���¼� ���� Ž��
				nowMorpheme = lnowWord.getWord().get(findMorpIdx);

				String ctg = this.patternST.getCategory();
				if(ctg.equals("�߾�")){//�߾� ����
					if(type=="O"){
						//System.out.println(findWordIdx+":"+findMorpIdx);
						if(nowMorpheme.getCharacter().equals("\"")){//�ι�° "Ž�� 
							findO.setSentenceStructure(lnowWord);
							//���� Ž�� ����
							nowWordIdx = findWordIdx;
							findKnowledgeIdx = findWordIdx;
							break WordLoop;
						}else if(findWordIdx==0&&findMorpIdx==0){
							//������ �����̸鼭 �߾�κ� ã�� ��������
							nowWordIdx = findWordIdx;
							break WordLoop;
						}else if(findMorpIdx==0){
							findO.setSentenceStructure(lnowWord);
						}

					}else{//"S"���� �Ʒ��� ����ѵ�....�����丵 �ؾ���

						if(s_exExtractor.checkGoS(lnowWord,findMorpIdx)&&!s_exExtractor.checkStopS_part(nowMorpheme)){
							findS.setSentenceStructure(lnowWord);
							seekFlag = true;
							//��ġ�Ǹ� ���¼� �м� �ǳʶٰ� ���� ���� �м�
							break MorphemeLoop;
						}else if(seekFlag){//������ Ž���Ȱ� �����鼭 ���� Ž���� ���������� ã�� ���� ����
							break WordLoop;
						}else{//�ƹ��͵� Ž������ ����
							break WordLoop;
						}	
					}
				}else{//�Ϲ����� ī�װ��� ���� ���� ���� ����
					if(s_exExtractor.checkGoS(lnowWord,findMorpIdx)){
						if(type=="S") findS.setSentenceStructure(lnowWord);
						else findO.setSentenceStructure(lnowWord);	
						seekFlag = true;
						//��ġ�Ǹ� ���¼� �м� �ǳʶٰ� ���� ���� �м�
						break MorphemeLoop;
					}else if(seekFlag){//������ Ž���Ȱ� �����鼭 ���� Ž���� ���������� ã�� ���� ����
						break WordLoop;
					}else{//�ƹ��͵� Ž������ ����
						break WordLoop;
					}	
				}



			}

		}
	}
	

}
