package com.Enhanced;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.StringTokenizer;

import com.Entity.Morpheme;

public class StopWordsChecker {
	private ArrayList<String> stopWordArray = new ArrayList<String>();
	public StopWordsChecker() {
		super();
		/**
		 * 초기 생성시 개체명 사전들 로드 
		 **/
		try {
			InputStream ins = getClass().getResourceAsStream("/com/File/Stopwords.txt");
			Reader fr = new InputStreamReader(ins, "utf-8");
			BufferedReader in = new BufferedReader(fr);
			String s;
			while ((s = in.readLine()) != null) {
				
				this.stopWordArray.add(s);
				
			}
		in.close();
		} catch (IOException e) {
			System.err.println(e); // 에러가 있다면 메시지 출력
		}
	}
	
	public boolean checkSeq(String name, String job, String reco){
		
		if(name.length()==1)return true;//성만 검색된경우[2글자 성은 체크가 안됨]
		else{
			for(int i=0; i < this.stopWordArray.size(); i++){
				
				if(name.equals(this.stopWordArray.get(i)))return true;
				if(job.equals(this.stopWordArray.get(i)))return true;
				if(reco.equals(this.stopWordArray.get(i)))return true;
				
			}
		}
		
		
		return false;
	}
	
}
