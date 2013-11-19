package com.Parser;

import java.util.StringTokenizer;

import com.Entity.Morpheme;
import com.Entity.Pattern;
import com.Entity.PatternStructure;

public class PatternParser {
	public PatternStructure PatternSubstitution(String patternData){
		PatternStructure result;
		
		patternData = patternData.replaceAll("��/jcc", "��/jcs");
		patternData = patternData.replaceAll("��/jxc", "��/jxc");
		patternData = patternData.replaceAll("��/jco", "��/jco");
		
		result = PatternPaserSequence(patternData);
		
		return result;
	}
	public PatternStructure PatternPaserSequence(String patternData){
		//��ǥ�� ġȯ
		patternData = patternData.replaceAll("��/jcc", "��/jcs");
		patternData = patternData.replaceAll("��/jxc", "��/jxc");
		patternData = patternData.replaceAll("��/jco", "��/jco");

		//å���߱�|S+��/JKS|O+��/JKG|å��/NNG+��/JKO_��/VV;
		Morpheme morpheme;
		StringTokenizer ptrToken = new StringTokenizer(patternData,"|");
		StringTokenizer unToken,mmaToken,tagToken;
		String token;
		Pattern pattern = new Pattern();
		PatternStructure patternStructure = new PatternStructure();
		int i=0;

		//pattern ����
		//������ Ȯ���غ�����...
		for(patternStructure.setCategory(ptrToken.nextToken());ptrToken.hasMoreElements();i++) {
			token = ptrToken.nextToken();
			pattern = new Pattern();
			if(token.contains("S")&&i!=0){//���� ����...����Ž��
				//patternStructure.setReverseSerch(false);
			}else{//����Ž��, ������ ����[�����ε���]�� �� �ں��� �� P O S������ Ž��
	
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

		
		if(patternStructure.isReverseSerch()){//������ ����
			patternStructure.Pstatus = patternStructure.getPatternStructure().size()-1;
			patternStructure.Mstatus = patternStructure.getPatternStructure().get(patternStructure.Pstatus)
										.getPattern().size()-1;
		}
		
		return patternStructure;
	}
}
