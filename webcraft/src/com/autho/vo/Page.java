package com.autho.vo;

public class Page {

	private int page;
	private int rows;
	private int startrow;

	public Page() {
		
	}

	public Page(int page, int rows) {
		super();
		this.page = page;
		this.rows = rows;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getStartrow() {
		return (page-1)*rows;
	}

}
