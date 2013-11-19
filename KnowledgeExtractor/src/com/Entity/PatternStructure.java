package com.Entity;

import java.util.ArrayList;

public class PatternStructure {
	private ArrayList<Pattern> patternStructure;
	private String category;
	private boolean reverseSerch = true;
	public int Pstatus=0;
	public int Mstatus=0;
	
	public PatternStructure() {
		super();
		this.patternStructure = new ArrayList<Pattern>();
	}
	
	public PatternStructure(ArrayList<Pattern> patternStructure,
			String category, boolean reverseSerch) {
		super();
		this.patternStructure = patternStructure;
		this.category = category;
		this.reverseSerch = reverseSerch;
	}


	public void setPatternStructure(Pattern pattern) {
		this.patternStructure.add(pattern);
	}

	public ArrayList<Pattern> getPatternStructure() {
		return patternStructure;
	}

	public void setPatternStructure(ArrayList<Pattern> patternStructure) {
		this.patternStructure = patternStructure;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public boolean isReverseSerch() {
		return reverseSerch;
	}

	public void setReverseSerch(boolean reverseSerch) {
		this.reverseSerch = reverseSerch;
	}
	
	
}
