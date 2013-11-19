package com.Entity;

public class ResultKnowledge {
	private String n_idx;
	private String p_idx;
	private String lsp_idx;
	private String s;
	private String p;
	private String o;
	private String o2;
	private String overFlag;
	private String name;
	private String job;
	private String organization;
	private String unknown;
	private String k_idx;
	
	public ResultKnowledge() {
		super();
	}

	public ResultKnowledge(String n_idx, String p_idx, String lsp_idx, String s, String p, String o, String overflag) {
		super();
		this.n_idx = n_idx;
		this.p_idx = p_idx;
		this.lsp_idx = lsp_idx;
		this.s = s;
		this.p = p;
		this.o = o;
		this.overFlag = overflag;
	}


	public String getN_idx() {
		return n_idx;
	}


	public void setN_idx(String n_idx) {
		this.n_idx = n_idx;
	}


	public String getP_idx() {
		return p_idx;
	}


	public void setP_idx(String p_idx) {
		this.p_idx = p_idx;
	}


	public String getLsp_idx() {
		return lsp_idx;
	}


	public void setLsp_idx(String lsp_idx) {
		this.lsp_idx = lsp_idx;
	}


	public String getS() {
		return s;
	}


	public void setS(String s) {
		this.s = s;
	}


	public String getP() {
		return p;
	}


	public void setP(String p) {
		this.p = p;
	}


	public String getO() {
		return o;
	}


	public void setO(String o) {
		this.o = o;
	}


	public String getOverFlag() {
		return overFlag;
	}


	public void setOverFlag(String overFlag) {
		this.overFlag = overFlag;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getUnknown() {
		return unknown;
	}

	public void setUnknown(String unknown) {
		this.unknown = unknown;
	}

	public String getK_idx() {
		return k_idx;
	}

	public void setK_idx(String k_idx) {
		this.k_idx = k_idx;
	}

	public String getO2() {
		return o2;
	}

	public void setO2(String o2) {
		this.o2 = o2;
	}
	
}
