package com.Parser;

import java.util.StringTokenizer;

import com.Entity.Morpheme;
import com.Entity.Word;
import com.Entity.SentenceStructure;

public class SentenceParser {
	public SentenceStructure SentenceSubstitution(String expData, String sentenceData){
		SentenceStructure result;
		//문장 단위에서 대표형 치환
		sentenceData = sentenceData.replaceAll("이/jcc", "가/jcs");
		sentenceData = sentenceData.replaceAll("은/jxc", "는/jxc");
		sentenceData = sentenceData.replaceAll("을/jco", "를/jco");
		
		result = SentenceParserSequence(expData, sentenceData);

		return result;
	}

	public SentenceStructure SentenceParserSequence(String expData, String sentenceData){
		StringTokenizer expTokenizer = new StringTokenizer(expData," ");

		StringTokenizer strToken = new StringTokenizer(sentenceData," ");
		StringTokenizer mmaToken, tagToken;
		String token, expToken;
		Word word = new Word();
		Morpheme morpheme;
		SentenceStructure sentenceStructure = new SentenceStructure();
		int expIdx=0;
		//sentence 설정
		while(strToken.hasMoreElements()){
			expToken = expTokenizer.nextToken();
			token = strToken.nextToken();
			if(token.contains("+")){
				mmaToken = new StringTokenizer(token,"+");
				word = new Word();
				while(mmaToken.hasMoreTokens()){
					token = mmaToken.nextToken();
					tagToken = new StringTokenizer(token, "/");
					if(tagToken.countTokens()==2){
						morpheme=new Morpheme(tagToken.nextToken(), tagToken.nextToken());
						word.setWord(morpheme);
					}
				}		
			}else{
				tagToken = new StringTokenizer(token, "/");
				if(tagToken.countTokens()==2){
					morpheme=new Morpheme(tagToken.nextToken(), tagToken.nextToken());
					word = new Word();
					word.setWord(morpheme);	
				}
			}
			word.setExp(expToken);
			word.setExpIdx(expIdx++);
			sentenceStructure.setSentenceStructure(word);	
		}

		return sentenceStructure;
	}
}
