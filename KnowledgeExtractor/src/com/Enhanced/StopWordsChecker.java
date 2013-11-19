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
		 * �ʱ� ������ ��ü�� ������ �ε� 
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
			System.err.println(e); // ������ �ִٸ� �޽��� ���
		}
	}
	
	public boolean checkSeq(String name, String job, String reco){
		
		if(name.length()==1)return true;//���� �˻��Ȱ��[2���� ���� üũ�� �ȵ�]
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
