package com.webservice;

import java.util.List;

import javax.annotation.Resource;
import javax.jws.WebService;

import com.autho.main.department.service.DepartmentService;
import com.autho.main.employee.po.Employee;

@WebService(targetNamespace = "com.webservice.DeptWService", name = "DeptWService")
public class DeptWServiceImpl implements DeptWService{
	
	@Resource
	private DepartmentService service;
	
	@Override
	public String payRead(String name) {
		return "test:"+name;
	}

	@Override
	public List<Employee> getEmpByDept(int did) {
		return service.getEmps2(did);
	}

}
