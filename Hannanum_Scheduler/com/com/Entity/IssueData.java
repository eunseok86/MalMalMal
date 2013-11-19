package com.Entity;

public class IssueData {
	private String issueID;
	private String issueSentence;
	
	
	public IssueData() {
		super();
	}

	public IssueData(String issueID, String issueSentence) {
		super();
		this.issueID = issueID;
		this.issueSentence = issueSentence;
	}

	public String getIssueID() {
		return issueID;
	}

	public void setIssueID(String issueID) {
		this.issueID = issueID;
	}

	public String getIssueSentence() {
		return issueSentence;
	}

	public void setIssueSentence(String issueSentence) {
		this.issueSentence = issueSentence;
	}
	
}
