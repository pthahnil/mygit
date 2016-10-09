package com.autho.main.access.service;

import com.autho.main.access.po.Access;

public interface AccessService {
	public void add(int rid,int mid,int opcode);
	public void del(int rid,int mid,int opcode);
	public void update(Access access);
	public Access getById(int id);
}
