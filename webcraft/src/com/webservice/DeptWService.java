package com.webservice;

import java.util.List;

import javax.jws.WebService;

import com.autho.main.employee.po.Employee;

@WebService(targetNamespace = "com.webservice.DeptWService", name = "DeptWService")
public interface DeptWService {

	public String payRead(String name);
	
	public List<Employee> getEmpByDept(int did);
	
}
