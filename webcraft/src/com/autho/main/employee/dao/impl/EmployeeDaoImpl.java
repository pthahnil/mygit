package com.autho.main.employee.dao.impl;

import org.springframework.stereotype.Component;

import com.autho.main.basedao.impl.BaseDaoImpl;
import com.autho.main.employee.dao.EmployeeDao;
import com.autho.main.employee.po.Employee;

@Component("employeeDao")
public class EmployeeDaoImpl extends BaseDaoImpl<Employee> implements EmployeeDao {


}
