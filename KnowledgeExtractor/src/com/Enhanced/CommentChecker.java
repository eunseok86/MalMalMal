package com.Enhanced;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommentChecker {
	private int total = 0;
	private ArrayList<String> comment = new ArrayList<String>();;
	
	public CommentChecker() {
		super();
	}
	
	public CommentChecker(String sentence) {
		super();
		Pattern p = Pattern.compile("\".*?\""); 
		Matcher m = p.matcher(sentence);
		while(m.find()){
			this.total++;
			this.comment.add(m.group());
		}
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
	
	public String getComment(int idx){
		return this.comment.get(idx-1);
	}
	
}
