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
		//String testPattern = "�湮|S+��/JKS|O+��/JKO|�湮/NNG+��/XSV";
		String testPattern =  "�߾�|S+��/JX|O+\"/SS_��/VCP+��/ECE_����/NNG";
		//String testPattern =  "�߾�|S+��/JX|O+\"/SS_��/JC";
		//String testPattern =  "�߾�|S+��/JX|O+\"/SS+��/JC";
		PatternParser PP = new PatternParser();
		PatternStructure result =  PP.PatternSubstitution(testPattern);
		
		//���� ���
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
		
		System.out.println("����(pattern)-"+result.getCategory()+"   ����Ž��:"+result.isReverseSerch());
		System.out.println(resultIdx);
		System.out.println(resultStringChar);
		System.out.println(resultStringTag);
		
		///////////////////////////////
			
		//assertEquals("�湮", result.getCategory());
		assertEquals("S", result.getPatternStructure().get(0).getType());
		assertEquals("O", result.getPatternStructure().get(1).getType());
	}
	
	@Test
	public void testSentenceParser(){
		//String testSentence = "�輺��/NNG �ǿ�/NNG+��/JKO ���/XR+��/XSA+��/ETD ��/MDT ����/NNG ��/NNG �Ҽ�/NNG ��ȸ�ǿ�/NNG+��/XSN+��/JKO �����/NNG+�ǿ�/NNG+��/XSN+��/JX �̳�/NNG ����/NNG 10/NR+��/NNM 30/NR+��/NNM ��û/NNG 1/NR ��/NNG �κ�/NNG+����/JKM ����/NNG+ȸ��/NNG+��/JKO ��/VV+��/ECE \"/SS ��/NNG ����/NNG+��/JKS ���ڱ�/MAG ���/NNG ����/NNG ����/NNG+��/JKO ��/VV+���ٰ�/ECE ��/MDT �ڸ�/NNG+��/JKO ����/VV+��/EPT+��/ECS+\"/SS ��/NNG ��/NNG+�Ҹ�/NNG+��/JKO ����/VV+��/EPT+��/EFN+./SF";
		//String testExp = "�ڿ��� �ǿ��� \"ssss\" �鼭 \"sssssss\" �� ���ߴ�.";
		//String testSentence = "�ڿ���/NNG �ǿ�/NNG+��/JX \"/SS+ssss/OL+\"/SS ��/NNG+��/JKM \"/SS+sssssss/OL+\"/SS ��/NNG ����/VV+��/EPT+��/EFN+./SF";
		//String testExp = "���ִ� �ڿ��� �ǿ��� \" �ڱ� �� ���ΰ� �뼱 ���� ��ǿ� �־� �̷��� ��ó�� �ϰ� �ִ�\" �鼭 \" �ڱ� �� ������� �Ϸ�� ���� �ڽ��� ������ ������ ������ ������ ���� �ŷ��� �� �ִ� ���α׷��� �����ƾ� �Ѵ�\" �� ���ߴ�.";
		//String testSentence = "���ִ�/NNG �ڿ���/NNP �ǿ�/NNG+��/JX \"/SS �ڱ�/NNG ��/UN ����/NNG+��/JKS �뼱/NNG ����/NNG ���/NNG+��/JKM ��/VV+��/ECD �̷�/NNG+��/XSV+��/ETD ��ó/NNG+��/JKO ��/VV+��/ECE ��/VXV+��/ECS+\"/SS ��/NNG+��/JKM \"/SS �ڱ�/NNG ��/UN �����/NNG+��/JX �Ϸ�/NNG+��/JX ����/MAG �ڽ�/NNG+��/JKG ����/NNG+��/JKO ����/VV+��/ECE ������/NNG ����/NNG+��/JKM ����/VV+��/ECS �ŷ�/NNG+��/XSV+��/ETD ��/NNB ��/VV+��/ETD ���α׷�/NNG+��/JKO ����/VV+�ƾ�/ECD ��/VV+����/ECS+\"/SS ��/NNG ����/VV+��/EPT+��/EFN+./SF";
		//String testExp = "���� ������ ���� ��ǥ�� ���� �������� Ȳ�� �� ��ǥ�� ���ִ��� ������￡ ���� �Թ����� �̸� �����ؾ� �Ѵٴ� ������ �߾��� �� �Ͱ� ����, \" �������� ��ǥ�� ��������� ���� ������ �����ؾ� �Ѵٰ� ������ �ϱ� ����� �߾��� �ߴ�\" �鼭 �̰��� ������ �� \" ���������� ' ���� ������' �� ����ߴ�\" �� �����ߴ�. ";
		//String testSentence = "����/NNG ����/NNG+��/XSV+��/ETD ����/NNG ��ǥ/NNG+��/JX ����/NNG ��������/NNP Ȳ��/NNG ��/NNG ��ǥ/NNG+��/JKS ���ִ�/NNG+��/JKG ���/NNG+����/NNG+��/JKM ����/VV+��/ECS �Թ�/NNG+����/JKM �̸�/VV+��/ETD ����/NNG+��/XSV+���/ECD ��/VV+���ٴ�/ETD ����/NNG+��/JKG �߾�/NNG+��/JKO ��/VV+��/ETD ��/NNB+��/JKM ����/NNG+,/SP \"/SS ��������/NNP ��ǥ/NNG+��/JKS ���/NNG+����/NNG+��/JKO ��/VV+��/ETD ����/NNG+��/JKO ����/NNG+��/XSV+���/ECD ��/VV+���ٰ�/ECE ������/MAG ��/VV+��/ETN ���/VA+��/ETD �߾�/NNG+��/JKO ��/VV+��/EPT+��/ECS+\"/SS ��/NNG+��/JKM �̰���/MAG ����/NNG+��/XSV+��/ETD ��/NNG \"/SS ��������/NNP+��/JKG '/SS ����/NNG ������/NNG+'/SS ��/NNG ���/NNG+��/XSV+��/EPT+��/ECS+\"/SS ��/NNG ����/NNG+��/XSV+��/EPT+��/EFN+./SF";
		//String testExp = "�������� ���ϰ�������ȸ, 2�� ùȸ�� ���� [�Ӵ������� ���������]�������� ����ȭ ���ǿ� ���� '�������� ���ϰ�������ȸ' ù ȸ�ǰ� 2�� �������� �����������Ϳ��� ������. ���� �� ���� ������ 1��, ���� 5������ ������ ������������ �簡�� ���� �� ���� ����ȭ�� ���� ���ǰ� ����� �����̴�. �ռ� ������ ��������ȸ�� �б� 1ȸ �����ϴ� ���� ��Ģ���� �ϵ� �ʿ��� ��� �ֹ��� ����, ���÷� ���� �ֵ��� �ߴ�. ������ �� ��������ȸ ���Ͽ� �����ԡ�ü�� �а�����ȸ �����ں�ȣ �� ����� �а�����ȸ �����ࡤ��š���� �а�����ȸ �ⱹ������� �а�����ȸ�� �θ�, �ʿ��� ��� �ֹ��� ������ �а�����ȸ�� �߰��� ���� �� ��� �� �ֵ��� �ߴ�. �� �а�����ȸ�� ������ 1��� 3~4���� �ο����� �����ȴ�. �а��������� ���� ����� �̻����� �ϸ�, �ֹ��� �����ϴµ� ���� �ο����� ������ �� �ִ�. ";
		//String testSentence = "����/ncn+����/ncn ����/ncn+��������ȸ/ncn+,/sp 2/nnc+��/nbu ù/nno+ȸ/nbu+��/jcm ����/ncpa [/sl+�Ӵ�/ncn+������/ncn ���������]��������/ncn ����ȭ/nq ����/ncpa+��/jca ����/pvg+��/etm '/sr+����/ncn+����/ncn ����/ncn+��������ȸ/ncn+'/sr ù/nno ȸ��/ncn+��/jcs 2/nnc+��/nbu ����/ncn+����/ncn ����/ncpa+����/ncpa+����/ncn+����/jca ��/pvg+��/ep+����/ef+./sf ��/ncn+��/jct ��/mag ����/mag ������/f 1/nnc+��/nbu+,/sp ����/f 5/nnc+��/nbu+����/jca ����/ncn+��/xsvn+��/etm ������/ncn+����/jca+��/jxc �簡/ncpa+��/ncn ����/ncn ��/nbu ����/ncn ����ȭ/nq+��/jco ����/pvg+��/etm ����/ncn+��/jcs ����/ncpa+��/xsvn+��/etm ����/ncn+��/jp+��/ef+./sf �ռ�/mag ����/ncn+��/jxc ��������ȸ/ncn+��/jco �б�/ncn 1/nnc+ȸ/nbu ����/ncpa+��/xsva+��/etm ��/nbn+��/jco ��Ģ/ncn+����/jca ��/pvg+��/ecs �ʿ�/ncps+��/xsms+��/etm ���/ncn �ֹ�/ncn+��/jcc ��/ncn+��/jcm+,/sp ����/ncn+��/jca ��/pvg+��/etm+��/nbn ��/px+����/ecs ��/pvg+��/ep+��/ef+./sf ����/ncn+��/jxc ��/maj ��������ȸ/ncn ����/ncn+��/jca �����ԡ�ü��/ncn �а�����ȸ/ncn �����ں�ȣ/ncn ��/maj �����/ncpa �а�����ȸ/ncn �����ࡤ��š����/ncn �а�����ȸ/ncn �ⱹ�������/ncn �а�����ȸ/ncn+��/jco ��/pvg+��/ecc+,/sp �ʿ�/ncps+��/xsms+��/etm ���/ncn �ֹ�/ncn+��/jcc ����/ncpa+��/xsva+��/ecx �а�����ȸ/ncn+��/jco �߰�/ncpa+��/jca ����/ncn ��/maj �/ncpa+��/xsva+��/etm ��/nbn ��/px+����/ecs ��/pvg+��/ep+��/ef+./sf ��/mma �а�����ȸ/ncn+��/jxc ������/f 1/nnc+��/nbu+��/jcj 3/nnc+~/sd+4/nnc+��/nbu+��/jcm �ο�/ncn+����/jca ����/ncn+��/xsvn+����/ef+./sf �а�����/ncn+��/ncn+��/jcm ��/nbu+��/jxc ����/ncpa+��/ncn �̻�/ncn+����/jca ��/pvg+��/ecc+,/sp �ֹ�/ncn+��/jcc ����/ncpa+��/xsva+����/ecs ����/pvg+��/ecx �ο���/ncn+��/jco ����/ncpa+��/xsva+��/etm ��/nbn ��/paa+��/ef+./sf";
		//String testExp = "�׷��� ��������, ���ִ�, û�ʹ�, �������� �ڱ��� �׸��� �������� ������ �Ǽ��� �ǿ��� ���մ������� ��ȭ���� \"���� �������� �䱸�޴� �ڸ��� �������忡 ���� ������ ����� ��ӵǴ� ��Ȳ���� �������� ��������� Ÿ���� ������ ���̴� ��ŭ, ��ġ���� ����� �ƴϴ�\"�� \"�ռ� ���ִ��� ���� ���ɻ縦 ���� ������ ���� �䱸���� �����ߴ�\"�� �����ߴ� .";
		//String testSentence="�׷���/maj ��������/unk+,/sp ���ִ�/unk+,/sp û�ʹ�/unk+,/sp ��������/unk �ڱ���/unk �׸���/maj ��������/unk ������/unk �Ǽ���/unk �ǿ���/unk ����/ncpa+����/ncn+����/jcm ��ȭ/ncn+����/jca \"/sl+��/ncn+��/jcm ������/ncn+��/jco �䱸/ncpa+��/xsva+��/etm �ڸ���/unk ��������/ncr+��/jca ����/pvg+��/etm ������/ncn �����/unk ���/ncpa+��/xsvn+��/etm ��Ȳ/ncn+����/jca ������/ncn+��/jcm ��������/ncn+��/jxc Ÿ����/unk ��/nbn+����/jca ����/pvg+��/etm ��ŭ/nbn+,/sp ��ġ/ncpa+����/ncn �����/unk �ƴϴ�\"��/unk \"/sl+�ռ�/mag ���ִ���/unk ����/mag ���/ncpa+�ɻ�/ncn+��/jco ����/pvg+��/etm ������/ncn ����/ncpa �䱸/ncpa+��/jca+��/jxc �����ߴ�\"��/unk �����ߴ�/unk ./sf";
		//String testExp = "��ȭ�� �뼱 �ĺ����� �� ������ ����ǿ��� ���� ���ٸ� ������� \' ������ ��ġ \' �� \" ��ġ���� �߽��� ���� \" �̶�� �ø��� ������ �������� �ı��� �ݱ� ���� ���� �� ���� ���� ������ �ֹ��� �� �ִ�. ";
		//String testSentence ="��ȭ��/f �뼱/ncn �ĺ�/ncr+��/jp+��/ep+��/etm ��/pvg+��/etm ������/nq ����ǿ�/ncn+��/jxc ����/ncn ���ٸ�/nq �������/ncn '/sr ����/pvg+��/etm+��/nbn ��/nnc+ġ/nbu '/sr ��/jcs \"/sl ��ġ��/ncpa+��/jxc �߽�/ncn+��/ncn ����/ncpa \"/sl ��/npd+��/jp+���/ecs �ø���/ncn ����/ncn+��/jcm ��������/ncn �ı�/ncn+��/jcj �ݱ�/ncn ����/ncn ����/nq ��/nbu ����/ncr ��/paa+��/etm ����/ncpa+��/jco �ֹ�/ncn+��/ncn ��/nbn ��/paa+��/ef+./sf ";
		String testExp="�� �� �ܱ������� ���� 12�� �߱� ���� ����(����)�� ����(�õ &lt; ��+�� &gt; )�� Īȭ���б� ������ �������� \" ���� �߱��� �����ϰ� �ִٰ� �ϰ�, � ����� �߱��� �̱��� �̾� ���� ��2�� �뱹���� �����ߴٰ� ������ �̴� �ٺ����� �ϴ� �̾߱� \" ��鼭 �̰��� ���ߴٰ� �븸 �߱��ú��� 14�� ���ߴ�. ";
		String testSentence ="��/nbu ��/nbn �ܱ�����/ncn+��/jxc ����/pvg+��/etm 12/nnc+��/nbu �߱�/ncn ����/ncn ����(����)��/ncn ��/ncn+��/xsnx+(/sl+�/ncn+õ/ncn &lt;/ncn ��+��/ncn &gt;/ncn )/sr+��/nbu Īȭ���б�/ncn ������/f ����/ncpa+����/jca \"/sl ����/mag �߱�/ncn+��/jcc ��/pvg+��/etn+�ϰ�/jct ��/px+��/ef+��/jcr+��/jxc ��/pvg+��/ecc+,/sp �/mmd ���/ncn+��/jxc �߱�/ncn+��/jcc �̱�/ncn+��/jca ��/npd+��/jp+��/ecs ����/ncn ��2/ncn+��/jcm �뱹/ncn+����/jca ����/ncpa+��/xsva+��/ep+��/ef+��/jcr+��/jxc ������/maj ��/npd+��/jxc �ٺ�/ncn+��/ncn+��/jcc ��/pvg+��/etm �̾߱�/ncpa \"/sl ��/ncn+��/jp+�鼭/ecc ��/npd+����/jca ��/ncpa+��/xsva+��/ep+��/ef+��/jcr ��/pvg+��/ecx+��/px+��/etm �߱��ú�/ncn+��/jcs 14/nnc+��/nbu ����/pvg+��/ep+��/ef+./sf ";
	//	String testExp="�����-MS��, �����ذ� ���������� ǥâ ���� ";
	//	String testSentence="�����/f+-/sd+MS��/f+/ncn+,/sp �����ذ�/ncpa ����/ncn+������/ncn ǥâ/ncpa ����/ncpa ";
				
		
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
		System.out.println("���� �ڷᱸ��");
		System.out.println(resultExp);
		System.out.println(resultIndex);
		System.out.println(resultStringChar);
		System.out.println(resultStringTag);
		
	}


	
}
