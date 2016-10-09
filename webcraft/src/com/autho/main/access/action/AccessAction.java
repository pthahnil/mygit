package com.autho.main.access.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.autho.main.access.service.AccessService;
import com.opensymphony.xwork2.ActionSupport;

@Controller("accessAction")
@Scope("prototype")
public class AccessAction extends ActionSupport {

	@Resource
	public AccessService service;
	private int rid;
	private int mid;
	private int opcode;
	
	private String add(){
		service.add(rid, mid, opcode);
		return "";
	}
	
	private String del(){
		service.del(rid, mid, opcode);
		return "";
	}
	
	public int getRid() {
		return rid;
	}

	public void setRid(int rid) {
		this.rid = rid;
	}

	public int getMid() {
		return mid;
	}

	public void setMid(int mid) {
		this.mid = mid;
	}

	public int getOpcode() {
		return opcode;
	}

	public void setOpcode(int opcode) {
		this.opcode = opcode;
	}

}
