package com.test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import ognl.ParseException;

import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.autho.main.employee.po.Employee;


public class Test {

	public static void main(String[] args) throws IOException, ParseException {
		
		
		
//		List<Employee> employees = generateSampleEmployeeData();
//	        try(InputStream is = Test.class.getResourceAsStream("employees.xls")) {
//	            try (OutputStream os = new FileOutputStream("D:\\myworkspace\\webcraft\\WebRoot\\target\\employees.xls")) {
//	                Context context = new Context();
//	                context.putVar("employees", employees);
//	                JxlsHelper.getInstance().processTemplateAtCell(is, os, context, "Result!A1");
//	            }
//	        }
	}
    private static List<Employee> generateSampleEmployeeData() throws ParseException {
        List<Employee> employees = new ArrayList<Employee>();
        employees.add( new Employee("Elsa",22,"a","zz","kk") );
        employees.add( new Employee("Elen",24,"a","zz","kk") );
        employees.add( new Employee("Elono",44,"a","zz","kk") );
        employees.add( new Employee("Emma",67,"a","zz","kk") );
        employees.add( new Employee("Eng",25,"a","zz","kk") );
        return employees;
    }
}
