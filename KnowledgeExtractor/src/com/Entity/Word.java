package com.Entity;

import java.util.ArrayList;

public class Word {
	private ArrayList<Morpheme> word;
	private String exp;
	private int expIdx;
	
	public Word() {
		super();
		this.word = new ArrayList<Morpheme>();
	}
	
	public Word(ArrayList<Morpheme> wordData) {
		super();
		this.word = wordData;
	}
	
	public Word(ArrayList<Morpheme> wordData, String expData) {
		super();
		this.word = wordData;
		this.exp = expData;
	}

	
	public String getExp() {
		return exp;
	}

	public void setExp(String exp) {
		this.exp = exp;
	}

	public ArrayList<Morpheme> getWord() {
		return word;
	}

	public void setWord(ArrayList<Morpheme> wordData) {
		this.word = wordData;
	}
	
	public void setWord(Morpheme morpheme) {
		this.word.add(morpheme);
	}

	public int getExpIdx() {
		return expIdx;
	}

	public void setExpIdx(int expIdx) {
		this.expIdx = expIdx;
	}
	
	
	
}
