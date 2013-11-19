package com.TestCase;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.Entity.Morpheme;
import com.Entity.PatternStructure;
import com.Entity.SentenceStructure;
import com.Entity.Word;
import com.Parser.PatternParser;
import com.Parser.SentenceParser;

public class testPatternParser{
	
	@Before
	public void setUp() {
		
	}
	
	@Test
	public void testPatternPaserSequence() {
		//String testPattern = "방문|S+이/JKS|O+을/JKO|방문/NNG+하/XSV";
		String testPattern =  "발언|S+는/JX|O+\"/SS_이/VCP+면/ECE_서도/NNG";
		//String testPattern =  "발언|S+는/JX|O+\"/SS_며/JC";
		//String testPattern =  "발언|S+는/JX|O+\"/SS+며/JC";
		PatternParser PP = new PatternParser();
		PatternStructure result =  PP.PatternSubstitution(testPattern);
		
		//패턴 출력
		String resultStringChar = "";
		String resultStringTag ="";
		String resultIdx = "";
		for(int i =0; i < result.getPatternStructure().size(); i++){
			
			resultStringChar += result.getPatternStructure().get(i).getType()+" ";
			for(int x =0; x < result.getPatternStructure().get(i).getPattern().size(); x++){
				resultIdx += i;
				resultIdx+=":";
				resultIdx+= x;
				resultIdx+="\t";
				resultStringChar +=  result.getPatternStructure().get(i).getPattern().get(x).getCharacter();
				resultStringChar += "\t";
				resultStringTag +=  result.getPatternStructure().get(i).getPattern().get(x).getTag();
				resultStringTag += "\t";
			} 
			resultIdx += "|\t";
			resultStringChar += "|\t";
			resultStringTag += "|\t";
		}
		
		System.out.println("패턴(pattern)-"+result.getCategory()+"   후진탐색:"+result.isReverseSerch());
		System.out.println(resultIdx);
		System.out.println(resultStringChar);
		System.out.println(resultStringTag);
		
		///////////////////////////////
			
		//assertEquals("방문", result.getCategory());
		assertEquals("S", result.getPatternStructure().get(0).getType());
		assertEquals("O", result.getPatternStructure().get(1).getType());
	}
	
	@Test
	public void testSentenceParser(){
		//String testSentence = "김성태/NNG 의원/NNG+을/JKO 비롯/XR+하/XSA+ㄴ/ETD 새/MDT 누리/NNG 당/NNG 소속/NNG 국회의원/NNG+들/XSN+과/JKO 서울시/NNG+의원/NNG+들/XSN+은/JX 이날/NNG 오전/NNG 10/NR+시/NNM 30/NR+분/NNM 시청/NNG 1/NR 층/NNG 로비/NNG+에서/JKM 기자/NNG+회견/NNG+을/JKO 열/VV+고/ECE \"/SS 박/NNG 시장/NNG+이/JKS 갑자기/MAG 긴급/NNG 현장/NNG 점검/NNG+을/JKO 갈/VV+ㄴ다고/ECE 이/MDT 자리/NNG+를/JKO 피하/VV+었/EPT+다/ECS+\"/SS 고/NNG 목/NNG+소리/NNG+를/JKO 높이/VV+었/EPT+다/EFN+./SF";
		//String testExp = "박원순 의원은 \"ssss\" 면서 \"sssssss\" 고 말했다.";
		//String testSentence = "박원순/NNG 의원/NNG+은/JX \"/SS+ssss/OL+\"/SS 면/NNG+서/JKM \"/SS+sssssss/OL+\"/SS 고/NNG 말하/VV+었/EPT+다/EFN+./SF";
		//String testExp = "민주당 박영선 의원은 \" 박근 혜 정부가 대선 개입 사건에 있어 미련한 대처를 하고 있다\" 면서 \" 박근 혜 대통령은 하루라도 빨리 자신의 입장을 밝히고 국정원 개혁에 대해 신뢰할 수 있는 프로그램을 내놓아야 한다\" 고 말했다.";
		//String testSentence = "민주당/NNG 박영선/NNP 의원/NNG+은/JX \"/SS 박근/NNG 혜/UN 정부/NNG+가/JKS 대선/NNG 개입/NNG 사건/NNG+에/JKM 있/VV+어/ECD 미련/NNG+하/XSV+ㄴ/ETD 대처/NNG+를/JKO 하/VV+고/ECE 있/VXV+다/ECS+\"/SS 면/NNG+서/JKM \"/SS 박근/NNG 혜/UN 대통령/NNG+은/JX 하루/NNG+라도/JX 빨리/MAG 자신/NNG+의/JKG 입장/NNG+을/JKO 밝히/VV+고/ECE 국정원/NNG 개혁/NNG+에/JKM 대하/VV+어/ECS 신뢰/NNG+하/XSV+ㄹ/ETD 수/NNB 있/VV+는/ETD 프로그램/NNG+을/JKO 내놓/VV+아야/ECD 하/VV+ㄴ다/ECS+\"/SS 고/NNG 말하/VV+었/EPT+다/EFN+./SF";
		//String testExp = "한편 전병헌 원내 대표는 전날 새누리당 황우 여 대표가 민주당의 장외투쟁에 대해 입법으로 이를 방지해야 한다는 취지의 발언을 한 것과 관련, \" 새누리당 대표가 장외투쟁을 막는 법안을 마련해야 한다고 참으로 믿기 어려운 발언을 했다\" 면서 이같이 비판한 뒤 \" 새누리당의 ' 광장 공포증' 이 재발했다\" 고 지적했다. ";
		//String testSentence = "한편/NNG 전병/NNG+허/XSV+ㄴ/ETD 원내/NNG 대표/NNG+는/JX 전날/NNG 새누리당/NNP 황우/NNG 여/NNG 대표/NNG+가/JKS 민주당/NNG+의/JKG 장외/NNG+투쟁/NNG+에/JKM 대하/VV+어/ECS 입법/NNG+으로/JKM 이르/VV+ㄹ/ETD 방지/NNG+하/XSV+어야/ECD 하/VV+ㄴ다는/ETD 취지/NNG+의/JKG 발언/NNG+을/JKO 하/VV+ㄴ/ETD 것/NNB+과/JKM 관련/NNG+,/SP \"/SS 새누리당/NNP 대표/NNG+가/JKS 장외/NNG+투쟁/NNG+을/JKO 막/VV+는/ETD 법안/NNG+을/JKO 마련/NNG+하/XSV+어야/ECD 하/VV+ㄴ다고/ECE 참으로/MAG 믿/VV+기/ETN 어렵/VA+ㄴ/ETD 발언/NNG+을/JKO 하/VV+었/EPT+다/ECS+\"/SS 면/NNG+서/JKM 이같이/MAG 비판/NNG+하/XSV+ㄴ/ETD 뒤/NNG \"/SS 새누리당/NNP+의/JKG '/SS 광장/NNG 공포증/NNG+'/SS 이/NNG 재발/NNG+하/XSV+었/EPT+다/ECS+\"/SS 고/NNG 지적/NNG+하/XSV+었/EPT+다/EFN+./SF";
		//String testExp = "개성공단 남북공동위원회, 2일 첫회의 개최 [머니투데이 서동욱기자]개성공단 정상화 합의에 따른 '개성공단 남북공동위원회' 첫 회의가 2일 개성공단 종합지원센터에서 열린다. 남과 북 각각 위원장 1명, 위원 5명으로 구성된 공동위에서는 재가동 시점 등 공단 정상화를 위한 논의가 진행될 예정이다. 앞서 남북은 공동위원회를 분기 1회 개최하는 것을 원칙으로 하되 필요한 경우 쌍방이 합의, 수시로 열수 있도록 했다. 양측은 또 공동위원회 산하에 △출입·체류 분과위원회 △투자보호 및 관리운영 분과위원회 △통행·통신·통관 분과위원회 △국제경쟁력 분과위원회를 두며, 필요한 경우 쌍방이 합의해 분과위원회를 추가로 구성 및 운영할 수 있도록 했다. 각 분과위원회는 위원장 1명과 3~4명의 인원으로 구성된다. 분과위원장의 급은 과장급 이상으로 하며, 쌍방이 합의하는데 따라 인원수를 조정할 수 있다. ";
		//String testSentence = "개성/ncn+공단/ncn 남북/ncn+공동위원회/ncn+,/sp 2/nnc+일/nbu 첫/nno+회/nbu+의/jcm 개최/ncpa [/sl+머니/ncn+투데이/ncn 서동욱기자]개성공단/ncn 정상화/nq 합의/ncpa+에/jca 따르/pvg+ㄴ/etm '/sr+개성/ncn+공단/ncn 남북/ncn+공동위원회/ncn+'/sr 첫/nno 회의/ncn+가/jcs 2/nnc+일/nbu 개성/ncn+공단/ncn 종합/ncpa+지원/ncpa+센터/ncn+에서/jca 열/pvg+리/ep+ㄴ다/ef+./sf 남/ncn+과/jct 북/mag 각각/mag 위원장/f 1/nnc+명/nbu+,/sp 위원/f 5/nnc+명/nbu+으로/jca 구성/ncn+되/xsvn+ㄴ/etm 공동위/ncn+에서/jca+는/jxc 재가/ncpa+동/ncn 시점/ncn 등/nbu 공단/ncn 정상화/nq+를/jco 위하/pvg+ㄴ/etm 논의/ncn+가/jcs 진행/ncpa+되/xsvn+ㄹ/etm 예정/ncn+이/jp+다/ef+./sf 앞서/mag 남북/ncn+은/jxc 공동위원회/ncn+를/jco 분기/ncn 1/nnc+회/nbu 개최/ncpa+하/xsva+는/etm 것/nbn+을/jco 원칙/ncn+으로/jca 하/pvg+되/ecs 필요/ncps+하/xsms+ㄴ/etm 경우/ncn 쌍방/ncn+이/jcc 합/ncn+의/jcm+,/sp 수시/ncn+로/jca 열/pvg+ㄹ/etm+수/nbn 있/px+도록/ecs 하/pvg+었/ep+다/ef+./sf 양측/ncn+은/jxc 또/maj 공동위원회/ncn 산하/ncn+에/jca △출입·체류/ncn 분과위원회/ncn △투자보호/ncn 및/maj 관리운영/ncpa 분과위원회/ncn △통행·통신·통관/ncn 분과위원회/ncn △국제경쟁력/ncn 분과위원회/ncn+를/jco 두/pvg+며/ecc+,/sp 필요/ncps+하/xsms+ㄴ/etm 경우/ncn 쌍방/ncn+이/jcc 합의/ncpa+하/xsva+어/ecx 분과위원회/ncn+를/jco 추가/ncpa+로/jca 구성/ncn 및/maj 운영/ncpa+하/xsva+ㄹ/etm 수/nbn 있/px+도록/ecs 하/pvg+었/ep+다/ef+./sf 각/mma 분과위원회/ncn+는/jxc 위원장/f 1/nnc+명/nbu+과/jcj 3/nnc+~/sd+4/nnc+명/nbu+의/jcm 인원/ncn+으로/jca 구성/ncn+되/xsvn+ㄴ다/ef+./sf 분과위원/ncn+장/ncn+의/jcm 급/nbu+은/jxc 과장/ncpa+급/ncn 이상/ncn+으로/jca 하/pvg+며/ecc+,/sp 쌍방/ncn+이/jcc 합의/ncpa+하/xsva+ㄴ데/ecs 따르/pvg+아/ecx 인원수/ncn+를/jco 조정/ncpa+하/xsva+ㄹ/etm 수/nbn 있/paa+다/ef+./sf";
		//String testExp = "그러나 새누리당, 민주당, 청와대, 오은석과 박근혜 그리고 새누리당 간사인 권성동 의원은 연합뉴스와의 통화에서 \"고도의 도덕성을 요구받는 자리인 검찰총장에 대한 도덕성 논란이 계속되는 상황에서 법무부의 진상조사는 타당한 것으로 보이는 만큼, 정치공방 대상이 아니다\"며 \"앞서 민주당은 정작 결산심사를 위한 상임위 소집 요구에는 불응했다\"고 일축했다 .";
		//String testSentence="그러나/maj 새누리당/unk+,/sp 민주당/unk+,/sp 청와대/unk+,/sp 오은석과/unk 박근혜/unk 그리고/maj 새누리당/unk 간사인/unk 권성동/unk 의원은/unk 연합/ncpa+뉴스/ncn+와의/jcm 통화/ncn+에서/jca \"/sl+고도/ncn+의/jcm 도덕성/ncn+을/jco 요구/ncpa+받/xsva+는/etm 자리인/unk 검찰총장/ncr+에/jca 대하/pvg+ㄴ/etm 도덕성/ncn 논란이/unk 계속/ncpa+되/xsvn+는/etm 상황/ncn+에서/jca 법무부/ncn+의/jcm 진상조사/ncn+는/jxc 타당한/unk 것/nbn+으로/jca 보이/pvg+는/etm 만큼/nbn+,/sp 정치/ncpa+공방/ncn 대상이/unk 아니다\"며/unk \"/sl+앞서/mag 민주당은/unk 정작/mag 결산/ncpa+심사/ncn+를/jco 위하/pvg+ㄴ/etm 상임위/ncn 소집/ncpa 요구/ncpa+에/jca+는/jxc 불응했다\"고/unk 일축했다/unk ./sf";
		//String testExp = "공화당 대선 후보였던 존 매케인 상원의원도 버락 오바마 대통령의 \' 제한적 조치 \' 가 \" 겉치레만 중시한 공습 \" 이라며 시리아 정권의 공군기지 파괴와 반군 무장 지원 등 수위 높은 개입을 주문한 바 있다. ";
		//String testSentence ="공화당/f 대선/ncn 후보/ncr+이/jp+었/ep+던/etm 졸/pvg+ㄴ/etm 매케인/nq 상원의원/ncn+도/jxc 버락/ncn 오바마/nq 대통령의/ncn '/sr 제하/pvg+ㄴ/etm+적/nbn 조/nnc+치/nbu '/sr 가/jcs \"/sl 겉치레/ncpa+만/jxc 중시/ncn+한/ncn 공습/ncpa \"/sl 이/npd+이/jp+라며/ecs 시리아/ncn 정권/ncn+의/jcm 공군기지/ncn 파괴/ncn+와/jcj 반군/ncn 무장/ncn 지원/nq 등/nbu 수위/ncr 높/paa+은/etm 개입/ncpa+을/jco 주문/ncn+한/ncn 바/nbn 있/paa+다/ef+./sf ";
		String testExp="리 전 외교부장은 지난 12일 중국 남부 광둥(廣東)성 선전(深천 &lt; 土+川 &gt; )시 칭화대학교 연구원 강연에서 \" 요즘 중국이 굴기하고 있다고도 하고, 어떤 사람은 중국이 미국에 이어 세계 제2의 대국으로 성장했다고도 하지만 이는 바보만이 믿는 이야기 \" 라면서 이같이 말했다고 대만 중국시보가 14일 전했다. ";
		String testSentence ="리/nbu 전/nbn 외교부장/ncn+은/jxc 지나/pvg+ㄴ/etm 12/nnc+일/nbu 중국/ncn 남부/ncn 광둥(廣東)성/ncn 선/ncn+전/xsnx+(/sl+深/ncn+천/ncn &lt;/ncn 土+川/ncn &gt;/ncn )/sr+시/nbu 칭화대학교/ncn 연구원/f 강연/ncpa+에서/jca \"/sl 요즘/mag 중국/ncn+이/jcc 굴/pvg+기/etn+하고/jct 있/px+다/ef+고/jcr+도/jxc 하/pvg+고/ecc+,/sp 어떤/mmd 사람/ncn+은/jxc 중국/ncn+이/jcc 미국/ncn+에/jca 이/npd+이/jp+어/ecs 세계/ncn 제2/ncn+의/jcm 대국/ncn+으로/jca 성장/ncpa+하/xsva+었/ep+다/ef+고/jcr+도/jxc 하지만/maj 이/npd+는/jxc 바보/ncn+만/ncn+이/jcc 믿/pvg+는/etm 이야기/ncpa \"/sl 라/ncn+이/jp+면서/ecc 이/npd+같이/jca 말/ncpa+하/xsva+었/ep+다/ef+고/jcr 대/pvg+어/ecx+말/px+ㄴ/etm 중국시보/ncn+가/jcs 14/nnc+일/nbu 전하/pvg+었/ep+다/ef+./sf ";
	//	String testExp="국방부-MS社, 분쟁해결 업무유공자 표창 수여 ";
	//	String testSentence="국방부/f+-/sd+MS社/f+/ncn+,/sp 분쟁해결/ncpa 업무/ncn+유공자/ncn 표창/ncpa 수여/ncpa ";
				
		
		SentenceParser SP = new SentenceParser();
		SentenceStructure result = SP.SentenceSubstitution(testExp, testSentence);
		ArrayList<Word> resultWord = result.getSentenceStructure();
		String resultIndex="";
		String resultExp = "";
		String resultStringChar = "";
		String resultStringTag ="";
		
		for(int i=0; i < resultWord.size();i++){
			ArrayList<Morpheme> resultMorpheme =  resultWord.get(i).getWord();
			resultExp += resultWord.get(i).getExp()+":::::"+resultWord.get(i).getExpIdx()+"\t";
			
			for(int x=0; x < resultMorpheme.size(); x++){
				resultIndex += i+"-"+x+"\t";
				resultStringChar += resultMorpheme.get(x).getCharacter()+"\t";
				resultStringTag += resultMorpheme.get(x).getTag()+"\t";
				resultExp += "\t";
			}
			//resultExp += "|\t";
			resultIndex += "|\t";
			resultStringChar += "|\t";
			resultStringTag +="|\t";
		}
		System.out.println("문장 자료구조");
		System.out.println(resultExp);
		System.out.println(resultIndex);
		System.out.println(resultStringChar);
		System.out.println(resultStringTag);
		
	}


	
}
