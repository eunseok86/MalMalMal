package com.Entity;

public class NewsSentence {
	private String newsId;
	private String sentenceId;
	private String sentence;
	
	public NewsSentence() {
		super();
	}
	
	
	public NewsSentence(String newsId, String sentenceId, String sentence) {
		super();
		this.newsId = newsId;
		this.sentenceId = sentenceId;
		this.sentence = sentence;
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
	public String getSentence() {
		return sentence;
	}
	public void setSentence(String sentence) {
		this.sentence = sentence;
	}
	
	
}
