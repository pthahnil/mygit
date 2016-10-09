package com.autho.main.table.dao.impl;

import org.springframework.stereotype.Component;

import com.autho.main.basedao.impl.BaseDaoImpl;
import com.autho.main.table.dao.TableInfoDao;
import com.autho.main.table.po.TableInfo;

@Component("tableInfoDao")
public class TableInfoDaoImpl extends BaseDaoImpl<TableInfo> implements TableInfoDao {
	
}
