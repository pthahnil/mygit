package com.autho.main.employee.action;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.autho.main.department.po.Department;
import com.autho.main.department.service.DepartmentService;
import com.autho.main.employee.po.Employee;
import com.autho.main.employee.service.EmployeeService;
import com.autho.util.JsonUtils;
import com.autho.util.ResponseUtils;
import com.autho.vo.Page;
import com.opensymphony.xwork2.ActionSupport;

@Controller("employeeAction")
@Scope("prototype")
public class EmployeeAction extends ActionSupport {

	@Resource
	private EmployeeService service;
	@Resource
	private DepartmentService dservice;

	private Employee employee;
	private int oid;
	private List<Employee> employees;
	private int rid;
	
	private int did;//部门id
	private int id;
	private String ename;
	private int age;
	private String gender;
	private String phone;
	private String duties;

	private int page;
	private int rows;

	// ---------------------------------------------------------------------------
	/**
	 * 添加
	 * 
	 * @return
	 */
	public String add() throws Exception {
		JSONObject jo = new JSONObject();
		if(id>0){
			employee = service.getById(id);
			String oname = employee.getEname();
			int oage = employee.getAge();
			String ogender = employee.getGender();
			String ophone = employee.getPhone();
			String oduties = employee.getDuties();
			int odid = employee.getDept().getId();
			employee.setEname(ename);
			employee.setAge(age);
			employee.setGender(gender);
			employee.setPhone(phone);
			employee.setDuties(duties);
			Department d = dservice.getById(did);
			employee.setDept(d);
			service.update(employee);
			if(oname.equals(ename)&&ogender.equals(gender)&&oage==age&&ophone.equals(phone)&&oduties.equals(duties)&&odid==did){
				jo.put("success", true);
				jo.put("msg", "修改失败！");
			}else{
				jo.put("success", true);
			}
		}else{
			employee = new Employee(ename, age, gender, phone, duties);
			int i = service.add(employee, did);
			if(i>0){
				jo.put("success", true);
			}else{
				jo.put("success", true);
				jo.put("msg", "添加失败！");
			}
		}
		
		HttpServletResponse reponse = ServletActionContext.getResponse();
		ResponseUtils.write(reponse, jo);
		return null;
	}

	/**
	 * 查找所有
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getAll() throws Exception {
		Page p = new Page(page, rows);
		employees = service.getByPage(p);

		int count = service.getCount();
		JSONObject jo = new JSONObject();
		JSONArray ja = JsonUtils.emp2Jsonarray(employees);
		jo.put("rows", ja);
		jo.put("total", count);

		HttpServletResponse reponse = ServletActionContext.getResponse();
		ResponseUtils.write(reponse, jo);
		return null;
	}
	
	public String delete() throws Exception{
		service.delete(id);
		Employee e = service.getById(id);
		JSONObject jo = new JSONObject();
		if(e!=null){
			jo.put("msg", "删除失败！");
			jo.put("success", true);
		}else{
			jo.put("success", true);
		}
		
		HttpServletResponse reponse = ServletActionContext.getResponse();
		ResponseUtils.write(reponse, jo);
		return null;
	}
	
	public String export() throws IOException{
		
		List<Employee> employees = service.getAll();
		
		String file = "excelTemplate/employees.xls";
		String filepath = ServletActionContext.getServletContext().getRealPath("/")+file;
		InputStream is = new BufferedInputStream(new FileInputStream(filepath));
		
		OutputStream os = new FileOutputStream(ServletActionContext.getServletContext().getRealPath("/")+
				"target/object_collection_output.xls");
		
		Context context = new Context();
        context.putVar("employees", employees);
        JxlsHelper.getInstance().processTemplate(is, os, context);
		
		return null;
	}
	
	public String getTemplate() throws IOException{
		String file = "excelTemplate/employees.xls";
		
		String filepath = ServletActionContext.getServletContext().getRealPath("/")+file;
		InputStream is = new BufferedInputStream(new FileInputStream(filepath));
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.reset();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + "humanInfo.xls\"");
		OutputStream out = response.getOutputStream();
		
		int readlen;
		while ((readlen = is.read()) != -1) {
			out.write(readlen);
        }
		out.flush();
		
		if(is!=null){
			is.close();
		}
		if(out!=null){
			out.close();
		}
		
		return null;
	}
	// ---------------------------------------------------------------------------
	public EmployeeService getService() {
		return service;
	}

	public void setService(EmployeeService service) {
		this.service = service;
	}

	public DepartmentService getDservice() {
		return dservice;
	}

	public void setDservice(DepartmentService dservice) {
		this.dservice = dservice;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public int getOid() {
		return oid;
	}

	public void setOid(int oid) {
		this.oid = oid;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	public int getDid() {
		return did;
	}

	public void setDid(int did) {
		this.did = did;
	}

	public int getRid() {
		return rid;
	}

	public void setRid(int rid) {
		this.rid = rid;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDuties() {
		return duties;
	}

	public void setDuties(String duties) {
		this.duties = duties;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

}
