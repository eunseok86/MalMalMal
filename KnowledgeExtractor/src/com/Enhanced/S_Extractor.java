package com.Enhanced;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;


import java.util.StringTokenizer;

import com.Entity.Morpheme;
import com.Entity.Pattern;
import com.Entity.Word;

public class S_Extractor {
	private ArrayList<Morpheme> stopS_part = new ArrayList<Morpheme>();
	private ArrayList<Morpheme> stopS_all = new ArrayList<Morpheme>();
	//private ArrayList<Morpheme> goS = new ArrayList<Morpheme>();
	private ArrayList<Pattern> goS = new ArrayList<Pattern>();
	
	//private ArrayList<String> goS = new ArrayList<String>();;
	public S_Extractor() {
		super();
		/**
		 * 초기 생성시 불용어 사전들 로드 
		 **/
		try {
			InputStream ins = getClass().getResourceAsStream("/com/File/stopS-part.txt");
			Reader fr = new InputStreamReader(ins, "utf-8");
			BufferedReader in = new BufferedReader(fr);
			String s;
			while ((s = in.readLine()) != null) {
				StringTokenizer tagToken = new StringTokenizer(s, "/");
				if(tagToken.countTokens()==2)this.stopS_part.add(new Morpheme(tagToken.nextToken(), tagToken.nextToken()));
			}
			ins = getClass().getResourceAsStream("/com/File/stopS-all.txt");
			fr = new InputStreamReader(ins, "utf-8");
			in = new BufferedReader(fr);
			while ((s = in.readLine()) != null) {
				StringTokenizer tagToken = new StringTokenizer(s, "/");
				if(tagToken.countTokens()==2)this.stopS_all.add(new Morpheme(tagToken.nextToken(), tagToken.nextToken()));
			}

			ins = getClass().getResourceAsStream("/com/File/goS.txt");
			fr = new InputStreamReader(ins, "utf-8");
			in = new BufferedReader(fr);
			while ((s = in.readLine()) != null) {
				ArrayList<Morpheme> goSMorphenme = new ArrayList<Morpheme>();
				Pattern ptGoS = new Pattern();
				if(s.contains("+")){//연결된 패턴 형태의 goS
					StringTokenizer mmaToken = new StringTokenizer(s,"+");
					
					while(mmaToken.hasMoreTokens()){
						String token = mmaToken.nextToken();
						StringTokenizer tagToken = new StringTokenizer(token, "/");
						goSMorphenme.add(new Morpheme(tagToken.nextToken(), tagToken.nextToken()));
					}		
					ptGoS.setPattern(goSMorphenme);
					
					
				}else if(s.contains("/")){//형태소와 태그로 구성된 goS
					StringTokenizer tagToken = new StringTokenizer(s, "/");
					goSMorphenme.add(new Morpheme(tagToken.nextToken(), tagToken.nextToken()));
					
					ptGoS.setPattern(goSMorphenme);
				}else{//태그로만 구성된 S
					goSMorphenme.add(new Morpheme("",s));
					ptGoS.setPattern(goSMorphenme);
				}
				
				this.goS.add(ptGoS);
				//this.goS.add(s);
			}
			in.close();
		} catch (IOException e) {
			System.err.println(e); // 에러가 있다면 메시지 출력
		}
	}


	public boolean checkStopS_part(Morpheme targetM){
		for(int i=0;i < this.stopS_part.size(); i++){
			Morpheme checkM =  this.stopS_part.get(i);
			if(checkM.getCharacter().equals(targetM.getCharacter())&&checkM.getTag().equals(targetM.getTag()))return true;
		}
		return false;
	}


	public boolean checkStopS_all(Morpheme targetM){
		for(int i=0;i < this.stopS_part.size(); i++){
			Morpheme checkM =  this.stopS_part.get(i);
			if(checkM.getCharacter().equals(targetM.getCharacter())&&checkM.getTag().equals(targetM.getTag()))return true;
		}
		return false;
	}

	public boolean checkGoS(Word targetW, int mIdx){
		if(targetW.getExp().equals("출신인"))return false;
		for(int z=0; z<this.goS.size();z++){
			Pattern pattern = this.goS.get(z);
			ArrayList<Morpheme> goSMorphenme = pattern.getPattern();
			//저장된 goS와 비교
			int pIdx = goSMorphenme.size()-1;
			for(;pIdx>=0;pIdx--){
				Morpheme checkM =  goSMorphenme.get(pIdx);
				if(goSMorphenme.size()==1){//하나의 형태소로 구성된 태그일경우
					Morpheme targetM = targetW.getWord().get(mIdx);
					if(checkM.getCharacter().equals("")){//태그만 확인
						if(checkM.getTag().equals(targetM.getTag()))return true;
					}else{
						if(checkM.getTag().equals(targetM.getTag())&&
								checkM.getCharacter().equals(targetM.getCharacter()))return true;
					}
				}else{//하나의 형태소로 구성된 패턴이 아닐경우 받아온 word 다 비교를 해 봐야함
					MorphemeLoop:for(;mIdx>=0;mIdx--,pIdx--){
						//패턴형태의 goS 처리를 해줘야함
						//goS가 일치하지 않으면 다른 goS 패턴탐색처럼..
						Morpheme targetM = targetW.getWord().get(mIdx);
						checkM =  goSMorphenme.get(pIdx);
						//System.out.println(targetM.getCharacter()+" "+checkM.getCharacter());
						if(!checkM.getTag().equals(targetM.getTag())&&//패턴이 일치하지않으면 바로 패턴탐색 취소
								!checkM.getCharacter().equals(targetM.getCharacter()))break MorphemeLoop;
						if(pIdx==0)return true;//마지막 노드이고 여태까지 패턴이 일치하면
						
					}
					
				}
			}	
		}
		
	
	return false;
}


}
