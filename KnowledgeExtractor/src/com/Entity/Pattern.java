package com.Entity;

import java.util.ArrayList;



public class Pattern {
	private ArrayList<Morpheme> pattern;
	private int scope;
	private String type = "P";
	
	public Pattern() {
		super();
		this.pattern = new ArrayList<Morpheme>();
	}	
	
	public Pattern(ArrayList<Morpheme> pattern, int scope, String type) {
		super();
		this.pattern = pattern;
		this.scope = scope;
		this.type = type;
	}

	public ArrayList<Morpheme> getPattern() {
		return pattern;
	}
	
	public void setPattern(Morpheme data) {
		this.pattern.add(data);
	}

	public void setPattern(ArrayList<Morpheme> pattern) {
		this.pattern = pattern;
	}

	public int getScope() {
		return scope;
	}

	public void setScope(int scope) {
		this.scope = scope;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
	
}
