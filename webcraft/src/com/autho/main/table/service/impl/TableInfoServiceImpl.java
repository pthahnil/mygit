package com.autho.main.table.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.autho.main.table.dao.TableInfoDao;
import com.autho.main.table.po.TableInfo;
import com.autho.main.table.service.TableInfoService;
import com.autho.main.user.po.User;

@Service("tableInfoService")
public class TableInfoServiceImpl implements TableInfoService {
	
	@Resource
	private TableInfoDao dao;
	
	public void add(TableInfo tableInfo, int eid) {
		
//		String hql = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'autho'";
//		
//		List<TableInfo> infos = dao.getAll();//录入表名
//		List<String> tableNames = dao.find(hql);//所有表名
//		
//		if(tableNames != null && tableNames.size()>0){
//			for (String name : tableNames) {
//				if(infos!=null && infos.size()>0){
//					for (TableInfo info : infos) {
//						if(name.equals(info.getTableName()) || !name.startsWith("t")){
//							tableNames.remove(name);
//						}
//					}
//				}
//			}
//		}
		
	}

	public void delete(int id) {

	}

	public void update(TableInfo tableInfo) {

	}

	public User getById(int id) {
		return null;
	}

	public List<TableInfo> getAll() {
		return dao.loadAll();
	}

	@Override
	public int getCount() {
		return dao.loadAll()==null?0:dao.loadAll().size();
	}

}
