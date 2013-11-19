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
		 * �ʱ� ������ �ҿ�� ������ �ε� 
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
				if(s.contains("+")){//����� ���� ������ goS
					StringTokenizer mmaToken = new StringTokenizer(s,"+");
					
					while(mmaToken.hasMoreTokens()){
						String token = mmaToken.nextToken();
						StringTokenizer tagToken = new StringTokenizer(token, "/");
						goSMorphenme.add(new Morpheme(tagToken.nextToken(), tagToken.nextToken()));
					}		
					ptGoS.setPattern(goSMorphenme);
					
					
				}else if(s.contains("/")){//���¼ҿ� �±׷� ������ goS
					StringTokenizer tagToken = new StringTokenizer(s, "/");
					goSMorphenme.add(new Morpheme(tagToken.nextToken(), tagToken.nextToken()));
					
					ptGoS.setPattern(goSMorphenme);
				}else{//�±׷θ� ������ S
					goSMorphenme.add(new Morpheme("",s));
					ptGoS.setPattern(goSMorphenme);
				}
				
				this.goS.add(ptGoS);
				//this.goS.add(s);
			}
			in.close();
		} catch (IOException e) {
			System.err.println(e); // ������ �ִٸ� �޽��� ���
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
		if(targetW.getExp().equals("�����"))return false;
		for(int z=0; z<this.goS.size();z++){
			Pattern pattern = this.goS.get(z);
			ArrayList<Morpheme> goSMorphenme = pattern.getPattern();
			//����� goS�� ��
			int pIdx = goSMorphenme.size()-1;
			for(;pIdx>=0;pIdx--){
				Morpheme checkM =  goSMorphenme.get(pIdx);
				if(goSMorphenme.size()==1){//�ϳ��� ���¼ҷ� ������ �±��ϰ��
					Morpheme targetM = targetW.getWord().get(mIdx);
					if(checkM.getCharacter().equals("")){//�±׸� Ȯ��
						if(checkM.getTag().equals(targetM.getTag()))return true;
					}else{
						if(checkM.getTag().equals(targetM.getTag())&&
								checkM.getCharacter().equals(targetM.getCharacter()))return true;
					}
				}else{//�ϳ��� ���¼ҷ� ������ ������ �ƴҰ�� �޾ƿ� word �� �񱳸� �� ������
					MorphemeLoop:for(;mIdx>=0;mIdx--,pIdx--){
						//���������� goS ó���� �������
						//goS�� ��ġ���� ������ �ٸ� goS ����Ž��ó��..
						Morpheme targetM = targetW.getWord().get(mIdx);
						checkM =  goSMorphenme.get(pIdx);
						//System.out.println(targetM.getCharacter()+" "+checkM.getCharacter());
						if(!checkM.getTag().equals(targetM.getTag())&&//������ ��ġ���������� �ٷ� ����Ž�� ���
								!checkM.getCharacter().equals(targetM.getCharacter()))break MorphemeLoop;
						if(pIdx==0)return true;//������ ����̰� ���±��� ������ ��ġ�ϸ�
						
					}
					
				}
			}	
		}
		
	
	return false;
}


}
