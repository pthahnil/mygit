package com.autho.main.employee.service;

import java.util.List;

import com.autho.main.employee.po.Employee;
import com.autho.vo.Page;

public interface EmployeeService {
	public int add(Employee employee,int did);
	public void delete(int id);
	public void update(Employee employee);
	public Employee findById(int id);
	public List<Employee> findByOrgz(int did);
	public List<Employee> getAll();
	public int getCount();
	public List<Employee> getByPage(Page p);
	public Employee getById(int id);
}
