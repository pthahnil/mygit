package com.web.base;

/**
 * 
 * @author lxr
 * @date 2016-2-4 上午9:35:54	
 *
 */
public class ActionResult {
	private boolean dispatch = true;
	private String page = "";

	/**
	 * 
	 * @param page 跳转页面
	 * @param dispatch 是否是容器内分派(true:forward方式 false:redirect方式）
	 */
	public ActionResult(String page, boolean dispatch) {
		this.page = page;
		this.dispatch = dispatch;
	}

	/**
	 * 
	 * @param page
	 */
	public ActionResult(String page) {
		this.page = page;
		this.dispatch = true;
	}

	/**
	 * 获取跳转路径
	 * @return
	 */
	public String getPage() {
		return page;
	}

	/**
	 * 跳转方式
	 * @return
	 */
	public boolean isDispatch() {
		return dispatch;
	}
}