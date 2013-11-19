package com.TestCase;

//import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;

import com.Enhanced.CommentChecker;
import com.Enhanced.S_Divider;
import com.Entity.Knowledge;
import com.Entity.Morpheme;
import com.Entity.PatternStructure;
import com.Entity.SentenceStructure;
import com.KnowledgeExtractor.KnowledgeExtractor;
import com.KnowledgeExtractor.KnowledgePrint;
import com.Parser.PatternParser;
import com.Parser.SentenceParser;

public class testKnowledgeExtractor{

	@Before
	public void setUp() {

	}

	@Test
	public void testKnowledgeExtractorSequence(){
		
		long startTime = System.currentTimeMillis();

		KnowledgeExtractor KE = new KnowledgeExtractor();
		ArrayList<String> patterns = KE.PatternFileReader();


		

		String testExp= "�� �ǿ��� ���� ���� �� �Ű�� �ǿ��� \" ������ û�ʹ� �����񼭰��� �豤�� �����߾����� ����2���忡�� ' NLL ��ȭ�� ����� û�ʹ뿡 �����϶� ' �� ����ߴ� \" �� ������ �Ͱ� ����, \" �� ����� ��� ������ 7��25�� �ٷ� �� ���忡�� ���ƴ� \" �� \" Ȳ ����� �� ������ �� ��� ����ϵ��� �ߴٴ� ��Ȥ�� �ִ� \" �� ���ߴ�.";
		String testSentence = "��/nbu �ǿ�/ncr+��/jxc ����/maj ��/paa+��/etm ��/ncn �Ű��/nq �ǿ�/ncr+��/jcc \"/sl ������/nq û�ʹ�/f �����񼭰�/f+��/jcc �豤��/nq �����߾�����/f ����2���忡/ncn+��/jp+��/ecs '/sr NLL/f ��ȭ��/ncn ����/ncpa+��/jxc û�ʹ�/f+��/jca ������/ncn+��/jp+��/ecs '/sr ��/ncn ����ߴ�/ncn \"/sl ��/ncn ����/ncpa+��/ncn ��/nbn+��/jcj ����/ncpa+,/sp \"/sl ��/ncn ���/ncn+��/jxc ���/ncpa ����/ncn+��/jp+��/etm 7��25/ncn+��/jp+��/etm ��/nbn+��/jca ��/nbn ����/ncr+����/jca ���ƴ�/ncn \"/sl ��/ncn \"/sl Ȳ/ncn ���/ncr+��/jcc ��/nbn ����/ncr+��/jco ��/ncn ��/pvg+��/ecx ����ϵ���/ncn ��/pvg+��/ep+��/ef+��/etm ��Ȥ/ncpa+��/jcc ��/px+��/ef \"/sl ��/ncn ���ߴ�/ncn+./sf";
		
		CommentChecker cmChk = new CommentChecker(testExp);
		

		PatternParser PP = new PatternParser();
		SentenceParser SP = new SentenceParser();
		

		for(int i=0;i<patterns.size();i++){
			PatternStructure PS =  PP.PatternSubstitution(patterns.get(i));
			String resultStringChar ="";
			String resultStringTag ="";
			for(int xi =0; xi < PS.getPatternStructure().size(); xi++){
				resultStringChar += PS.getPatternStructure().get(xi).getType()+" ";
				for(int xx =0; xx < PS.getPatternStructure().get(xi).getPattern().size(); xx++){
					resultStringChar +=  PS.getPatternStructure().get(xi).getPattern().get(xx).getCharacter();
					resultStringChar += "\t";
					resultStringTag +=  PS.getPatternStructure().get(xi).getPattern().get(xx).getTag();
					resultStringTag += "\t";
				} 
				resultStringChar += "|\t";
				resultStringTag += "|\t";
			}

			
			SentenceStructure SS = SP.SentenceSubstitution(testExp,testSentence);


			//KnowledgeExtractor KEE = new KnowledgeExtractor();
			Knowledge knowledge = KE.KnowledgeExtractorSequence(PS,SS);
			
			
			if(knowledge.getO().size()!=0&&knowledge.getS().size()!=0){
				System.out.println("����(pattern)-"+PS.getCategory()+"   ����Ž��:"+PS.isReverseSerch());
				System.out.println(resultStringChar);
				System.out.println(resultStringTag);
				new KnowledgePrint().knowledgeSYSO(knowledge, cmChk,"DebugLog");
			}

		}

		long endTime = System.currentTimeMillis();
		long lTime = endTime - startTime;
		System.out.println("TIME : " + lTime + "(ms)");

	}

	



}
