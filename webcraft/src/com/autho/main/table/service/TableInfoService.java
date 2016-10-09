package com.autho.main.table.service;

import java.util.List;

import com.autho.main.table.po.TableInfo;
import com.autho.main.user.po.User;

public interface TableInfoService {
	public void add(TableInfo tableInfo, int eid);
	public List<TableInfo> getAll();
	public void delete(int id);
	public void update(TableInfo tableInfo);
	public User getById(int id);
	public int getCount();
}	
