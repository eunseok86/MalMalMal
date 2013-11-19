package com.Entity;

import java.util.ArrayList;

public class SentenceStructure {
	private ArrayList<Word> sentenceStructure;
	private String knowledgeS_job="";
	private String knowledgeS_name="";
	private String knowledgeS_organization="";
	private String knowledgeS_unknown="";
	
	public SentenceStructure() {
		super();
		this.sentenceStructure = new ArrayList<Word>();
	}
	
	public SentenceStructure(ArrayList<Word> sentenceStructure) {
		super();
		this.sentenceStructure = sentenceStructure;
	}

	public ArrayList<Word> getSentenceStructure() {
		return sentenceStructure;
	}

	public void setSentenceStructure(ArrayList<Word> sentenceStructure) {
		this.sentenceStructure = sentenceStructure;
	}
	
	public void setSentenceStructure(Word sentence) {
		this.sentenceStructure.add(sentence);
	}

	public String getKnowledgeS_job() {
		return knowledgeS_job;
	}

	public void setKnowledgeS_job(String knowledgeS_job) {
		this.knowledgeS_job = knowledgeS_job;
	}

	public String getKnowledgeS_name() {
		return knowledgeS_name;
	}

	public void setKnowledgeS_name(String knowledgeS_name) {
		this.knowledgeS_name = knowledgeS_name;
	}

	public String getKnowledgeS_organization() {
		return knowledgeS_organization;
	}

	public void setKnowledgeS_organization(String knowledgeS_organization) {
		this.knowledgeS_organization = knowledgeS_organization;
	}

	public String getKnowledgeS_unknown() {
		return knowledgeS_unknown;
	}

	public void setKnowledgeS_unknown(String knowledgeS_unknown) {
		this.knowledgeS_unknown += knowledgeS_unknown+",";
	}
	
}
