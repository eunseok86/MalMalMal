package com.Enhanced;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.StringTokenizer;

import com.Entity.Morpheme;

public class NamedEntityRecognitionor {
	private ArrayList<Morpheme> namedEntity_name = new ArrayList<Morpheme>();
	private ArrayList<Morpheme> namedEntity_job = new ArrayList<Morpheme>();
	private ArrayList<Morpheme> namedEntity_organization = new ArrayList<Morpheme>();
	private ArrayList<String> namedEntity_firstName = new ArrayList<String>();
	
	public NamedEntityRecognitionor() {
		super();
		/**
		 * 초기 생성시 개체명 사전들 로드 
		 **/
		try {
			InputStream ins = getClass().getResourceAsStream("/com/File/NamedEntity/name.txt");
			Reader fr = new InputStreamReader(ins, "utf-8");
			BufferedReader in = new BufferedReader(fr);
			String s;
			while ((s = in.readLine()) != null) {
				
				StringTokenizer tagToken = new StringTokenizer(s, "/");
				if(tagToken.countTokens()==2)this.namedEntity_name.add(new Morpheme(tagToken.nextToken(), tagToken.nextToken()));
				
			}
			
			ins = getClass().getResourceAsStream("/com/File/NamedEntity/job.txt");
			fr = new InputStreamReader(ins, "utf-8");
			in = new BufferedReader(fr);
			while ((s = in.readLine()) != null) {
				StringTokenizer tagToken = new StringTokenizer(s, "/");
				if(tagToken.countTokens()==2)this.namedEntity_job.add(new Morpheme(tagToken.nextToken(), tagToken.nextToken()));
			}
			
			ins = getClass().getResourceAsStream("/com/File/NamedEntity/organization.txt");
			fr = new InputStreamReader(ins, "utf-8");
			in = new BufferedReader(fr);
			while ((s = in.readLine()) != null) {
				StringTokenizer tagToken = new StringTokenizer(s, "/");
				if(tagToken.countTokens()==2)this.namedEntity_organization.add(new Morpheme(tagToken.nextToken(), tagToken.nextToken()));
			}
			
			ins = getClass().getResourceAsStream("/com/File/NamedEntity/firstName.txt");
			fr = new InputStreamReader(ins, "utf-8");
			in = new BufferedReader(fr);
			while ((s = in.readLine()) != null) {
				this.namedEntity_firstName.add(s);
			}
			in.close();
		} catch (IOException e) {
			System.err.println(e); // 에러가 있다면 메시지 출력
		}
	}

	public boolean matchFirstName(String firstName){
		for(int i=0; i < this.namedEntity_firstName.size(); i++){
			if(firstName.equals(this.namedEntity_firstName.get(i)))return true;
		}
		
		return false;
	}
	
}
