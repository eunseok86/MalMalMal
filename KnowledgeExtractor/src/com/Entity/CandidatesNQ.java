package com.Entity;

public class CandidatesNQ {
	private String name = "";
	private String job = "";
	private String organization = "";
	private int expIdx;
	
	public CandidatesNQ() {
		super();
	}
	
	public CandidatesNQ(String name, int expIdx) {
		super();
		this.name = name;
		this.expIdx = expIdx;
	}
	
	public CandidatesNQ(String name, String job, int expIdx) {
		super();
		this.name = name;
		this.job = job;
		this.expIdx = expIdx;
	}
	
	
	
	public CandidatesNQ(String name, String job, String organization, int expIdx) {
		super();
		this.name = name;
		this.job = job;
		this.organization = organization;
		this.expIdx = expIdx;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getExpIdx() {
		return expIdx;
	}

	public void setExpIdx(int expIdx) {
		this.expIdx = expIdx;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}
	
}
