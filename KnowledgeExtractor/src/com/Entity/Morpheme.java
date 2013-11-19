package com.Entity;

public class Morpheme {
	private String character;
	private String tag;
	
	public Morpheme() {
		super();
	}
	
	public Morpheme( String charData, String Mtag) {
		super();
		character = charData;
		tag = Mtag;
	}
	


	public String getCharacter() {
		return character;
	}
	public void setCharacter(String charData) {
		character = charData;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String Mtag) {
		tag = Mtag;
	}
}
