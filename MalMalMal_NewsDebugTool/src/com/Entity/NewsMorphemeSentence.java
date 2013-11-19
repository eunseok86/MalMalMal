package com.Entity;

public class NewsMorphemeSentence {
	private String exp;
	private String morpheme;
	private String newsId;
	private String sentenceId;
	
	public NewsMorphemeSentence() {
		super();
	}
	public NewsMorphemeSentence(String exp, String morpheme) {
		super();
		this.exp = exp;
		this.morpheme = morpheme;
	}
	public NewsMorphemeSentence(String exp, String morpheme, String newsId,
			String sentenceId) {
		super();
		this.exp = exp;
		this.morpheme = morpheme;
		this.newsId = newsId;
		this.sentenceId = sentenceId;
	}

	public String getNewsId() {
		return newsId;
	}

	public void setNewsId(String newsId) {
		this.newsId = newsId;
	}

	public String getSentenceId() {
		return sentenceId;
	}

	public void setSentenceId(String sentenceId) {
		this.sentenceId = sentenceId;
	}

	public String getExp() {
		return exp;
	}
	public void setExp(String exp) {
		this.exp = exp;
	}
	public String getMorpheme() {
		return morpheme;
	}
	public void setMorpheme(String morpheme) {
		this.morpheme = morpheme;
	}
	
	
}
