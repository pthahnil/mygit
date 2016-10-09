package com.autho.main.department.service;

import java.util.List;

import com.autho.exception.DepartmentDelException;
import com.autho.main.department.po.Department;
import com.autho.main.employee.po.Employee;
import com.autho.vo.Page;

public interface DepartmentService {
	
	public int add(Department dept,int pid);
	public void update(Department dept);
	public void delete(int id) throws DepartmentDelException;
	public Department getById(int id);
	public List<Department> getChildren(int pid);
	public int getCount();
	public List<Department> getByPage(Page p);
	public List<Department> getAll();
	public List<Employee> getEmps(int did);
	public List<Employee> getEmps2(int did);
	
}
