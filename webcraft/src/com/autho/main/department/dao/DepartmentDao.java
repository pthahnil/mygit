package com.autho.main.department.dao;

import java.util.List;

import com.autho.main.basedao.BaseDao;
import com.autho.main.department.po.Department;

public interface DepartmentDao extends BaseDao<Department> {
	
	public List<Department> getChildren(int pid);
	
}
