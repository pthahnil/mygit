package com.autho.main.department.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.autho.main.basedao.impl.BaseDaoImpl;
import com.autho.main.department.dao.DepartmentDao;
import com.autho.main.department.po.Department;

@Component("departmentDao")
public class DepartmentDaoImpl extends BaseDaoImpl<Department> implements DepartmentDao {

	public List<Department> getChildren(int pid) {
		if(pid<=0){
//			return (List<Department>) getHibernateTemplate().find("from Department d where d.parent is null");
			return (List<Department>) getHibernateTemplate().find("from Department d");
		}else{
			return (List<Department>) getHibernateTemplate().find("from Department d where d.parent.id=?",new Object[]{pid});
		}
	}
	
	
	
}
