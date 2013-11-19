package com.Sequence;


public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		knowledgeExtSeq keE = new knowledgeExtSeq();
		for(int i=11;i>=9;i--){
			System.out.println(i);
			
			//db»£√‚
			keE.callDB(Integer.toString(i));
		//keE.callDB(Integer.toString(11));
			//
			keE.callCandidatesExtractingSeq(true);
			System.out.println(i+"≥°");
	}
		
		
	}
	
	

}
