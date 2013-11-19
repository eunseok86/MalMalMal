package com.Entity;

import java.util.ArrayList;

public class Knowledge {
	private ArrayList<SentenceStructure> S = new ArrayList<SentenceStructure>();
	private ArrayList<SentenceStructure> O = new ArrayList<SentenceStructure>();
	private String MopData;
	private String ExpData;
	private String CandidateString;
	private boolean dieFlag = false;

	
	public Knowledge() {
		super();
	}
	public Knowledge(SentenceStructure s, SentenceStructure o) {
		super();
		S.add(s);
		O.add(o);
	}
	public ArrayList<SentenceStructure> getS() {
		return S;
	}
	public void setS(SentenceStructure s) {
		S.add(s);
	}
	public ArrayList<SentenceStructure> getO() {
		return O;
	}
	public void setO(SentenceStructure o) {
		O.add(o);
	}
	public String getMopData() {
		return MopData;
	}
	public void setMopData(String mopData) {
		MopData = mopData;
	}
	public String getExpData() {
		return ExpData;
	}
	public void setExpData(String expData) {
		ExpData = expData;
	}
	public String getCandidateString() {
		return CandidateString;
	}
	public void setCandidateString(String candidateString) {
		CandidateString = candidateString;
	}
	public boolean isDieFlag() {
		return dieFlag;
	}
	public void setDieFlag(boolean dieFlag) {
		this.dieFlag = dieFlag;
	}
	
	
	
}
