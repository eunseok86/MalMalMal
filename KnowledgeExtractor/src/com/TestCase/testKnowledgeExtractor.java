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


		

		String testExp= "박 의원은 또한 같은 당 신경민 의원이 \" 이중희 청와대 민정비서관이 김광수 서울중앙지검 공안2부장에게 ' NLL 대화록 수사는 청와대에 직보하라 ' 고 얘기했다 \" 고 주장한 것과 관련, \" 이 사건은 고발 당일인 7월25일 바로 김 부장에게 배당됐다 \" 며 \" 황 장관이 김 부장을 콕 찍어 배당하도록 했다는 의혹이 있다 \" 고 말했다.";
		String testSentence = "박/nbu 의원/ncr+은/jxc 또한/maj 같/paa+은/etm 당/ncn 신경민/nq 의원/ncr+이/jcc \"/sl 이중희/nq 청와대/f 민정비서관/f+이/jcc 김광수/nq 서울중앙지검/f 공안2부장에/ncn+이/jp+게/ecs '/sr NLL/f 대화록/ncn 수사/ncpa+는/jxc 청와대/f+에/jca 직보하/ncn+이/jp+라/ecs '/sr 고/ncn 얘기했다/ncn \"/sl 고/ncn 주장/ncpa+한/ncn 것/nbn+과/jcj 관련/ncpa+,/sp \"/sl 이/ncn 사건/ncn+은/jxc 고발/ncpa 당일/ncn+이/jp+ㄴ/etm 7월25/ncn+이/jp+ㄹ/etm 바/nbn+로/jca 김/nbn 부장/ncr+에게/jca 배당됐다/ncn \"/sl 며/ncn \"/sl 황/ncn 장관/ncr+이/jcc 김/nbn 부장/ncr+을/jco 콕/ncn 찍/pvg+어/ecx 배당하도록/ncn 하/pvg+었/ep+다/ef+는/etm 의혹/ncpa+이/jcc 있/px+다/ef \"/sl 고/ncn 말했다/ncn+./sf";
		
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
				System.out.println("패턴(pattern)-"+PS.getCategory()+"   후진탐색:"+PS.isReverseSerch());
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
