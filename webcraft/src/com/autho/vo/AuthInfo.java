package com.autho.vo;

public class AuthInfo {
	
	private String url;
	private int opCode;
	private int priority;

	public AuthInfo() {	}
	
	public AuthInfo(String url, int opCode, int priority) {
		super();
		this.url = url;
		this.opCode = opCode;
		this.priority = priority;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getOpCode() {
		return opCode;
	}

	public void setOpCode(int opCode) {
		this.opCode = opCode;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

}
