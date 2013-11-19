package com.Parser;

import java.util.StringTokenizer;

import com.Entity.Morpheme;
import com.Entity.Pattern;
import com.Entity.PatternStructure;

public class PatternParser {
	public PatternStructure PatternSubstitution(String patternData){
		PatternStructure result;
		
		patternData = patternData.replaceAll("이/jcc", "가/jcs");
		patternData = patternData.replaceAll("은/jxc", "는/jxc");
		patternData = patternData.replaceAll("을/jco", "를/jco");
		
		result = PatternPaserSequence(patternData);
		
		return result;
	}
	public PatternStructure PatternPaserSequence(String patternData){
		//대표형 치환
		patternData = patternData.replaceAll("이/jcc", "가/jcs");
		patternData = patternData.replaceAll("은/jxc", "는/jxc");
		patternData = patternData.replaceAll("을/jco", "를/jco");

		//책임추궁|S+이/JKS|O+의/JKG|책임/NNG+을/JKO_묻/VV;
		Morpheme morpheme;
		StringTokenizer ptrToken = new StringTokenizer(patternData,"|");
		StringTokenizer unToken,mmaToken,tagToken;
		String token;
		Pattern pattern = new Pattern();
		PatternStructure patternStructure = new PatternStructure();
		int i=0;

		//pattern 설정
		//역방향 확인해봐야함...
		for(patternStructure.setCategory(ptrToken.nextToken());ptrToken.hasMoreElements();i++) {
			token = ptrToken.nextToken();
			pattern = new Pattern();
			if(token.contains("S")&&i!=0){//수정 요함...전진탐색
				//patternStructure.setReverseSerch(false);
			}else{//후진탐색, 패턴의 상태[시작인덱스]를 맨 뒤부터 즉 P O S순으로 탐색
	
			}
			if(token.contains("_")){
				unToken = new StringTokenizer(token,"_");
				while(unToken.hasMoreTokens()){
					token = unToken.nextToken();
					
					if(token.contains("+")){
						mmaToken = new StringTokenizer(token,"+");
					
						while(mmaToken.hasMoreTokens()){
							token = mmaToken.nextToken();
							if(token.equals("S")){
								morpheme =new Morpheme(token,"");
								pattern.setType("S");
							}else if(token.equals("O")){
								morpheme =new Morpheme(token,"");
								pattern.setType("O");
							}
							else{
								tagToken = new StringTokenizer(token, "/");
								morpheme =new Morpheme(tagToken.nextToken(), tagToken.nextToken());
								pattern.setPattern(morpheme);
							}
						}	
					}else{
						if(token.equals("S")){
							morpheme =new Morpheme(token,"");
							pattern.setType("S");
						}else if(token.equals("O")){
							morpheme =new Morpheme(token,"");
							pattern.setType("O");
						}
						else{
							tagToken = new StringTokenizer(token, "/");
							morpheme =new Morpheme(tagToken.nextToken(), tagToken.nextToken());
							pattern.setPattern(morpheme);
						}
					}
				}
			}else{
				if(token.contains("+")){
					mmaToken = new StringTokenizer(token,"+");
					pattern = new Pattern();
					while(mmaToken.hasMoreTokens()){
						token = mmaToken.nextToken();
						if(token.equals("S")){
							morpheme =new Morpheme(token,"");
							pattern.setType("S");
						}else if(token.equals("O")){
							morpheme =new Morpheme(token,"");
							pattern.setType("O");
						}
						else{
							
							tagToken = new StringTokenizer(token, "/");
							morpheme =new Morpheme(tagToken.nextToken(), tagToken.nextToken());
							pattern.setPattern(morpheme);
						}
					}	
				}else{
					if(token.equals("S")){
						morpheme =new Morpheme(token,"");
						pattern.setType("S");
					}else if(token.equals("O")){
						morpheme =new Morpheme(token,"");
						pattern.setType("O");
					}
					else{
						tagToken = new StringTokenizer(token, "/");
						//pattern.setType("P");
						morpheme =new Morpheme(tagToken.nextToken(), tagToken.nextToken());
						pattern.setPattern(morpheme);
					}
				}
			}

			patternStructure.setPatternStructure(pattern);
		}

		
		if(patternStructure.isReverseSerch()){//포인터 설정
			patternStructure.Pstatus = patternStructure.getPatternStructure().size()-1;
			patternStructure.Mstatus = patternStructure.getPatternStructure().get(patternStructure.Pstatus)
										.getPattern().size()-1;
		}
		
		return patternStructure;
	}
}
