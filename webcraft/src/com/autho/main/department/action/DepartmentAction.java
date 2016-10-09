package com.autho.main.department.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.autho.exception.DepartmentDelException;
import com.autho.main.department.po.Department;
import com.autho.main.department.service.DepartmentService;
import com.autho.main.employee.po.Employee;
import com.autho.util.JsonUtils;
import com.autho.util.ListUtils;
import com.autho.util.ResponseUtils;
import com.autho.vo.Page;
import com.opensymphony.xwork2.ActionSupport;

@Controller("deptAction")
@Scope("prototype")
public class DepartmentAction extends ActionSupport {

	@Resource
	private DepartmentService service;
	private Department dept;
	private int pid;
	private int id;
	private int did;
	
	private String dname;
	private String descpt;
	
	List<Department> deptlist;

	private int page;
	private int rows;
	//--------------------------------------------------------
	/**
	 * 添加
	 * 
	 * @return
	 * @throws Exception 
	 */
	public String add() throws Exception {
		JSONObject jo = new JSONObject();
		if(id>0){
			dept = service.getById(id);
			String odname = dept.getDname();
			String odescpt = dept.getDescpt();
			
			dept.setDname(dname);
			dept.setDescpt(descpt);
			service.update(dept);
			if(odname.equals(dname)&&odescpt.equals(descpt)){
				jo.put("success", true);
				jo.put("msg", "修改失败！");
			}else{
				jo.put("success", true);
			}
		}else{
			dept = new Department(dname, descpt);
			int i = service.add(dept, pid);
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
	};

	/**
	 * 子机构
	 * 
	 * @return
	 */
	public String getList() {
		deptlist = service.getChildren(pid);
		return "all";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception 
	 * @throws OrganisationDelException
	 */
	public String delete() throws Exception {
		service.delete(id);
		Department d = service.getById(id);
		
		JSONObject jo = new JSONObject();
		if(d!=null){
			jo.put("msg", "删除失败！");
			jo.put("success", true);
		}else{
			jo.put("success", true);
		}
		
		HttpServletResponse reponse = ServletActionContext.getResponse();
		ResponseUtils.write(reponse, jo);
		return null;
	}

	/**
	 * 获取所有子机构
	 * 
	 * @return
	 * @throws Exception 
	 */
	public String getAll() throws Exception {
		deptlist = service.getAll();
		
		Department root = ListUtils.getRoot(deptlist);
		JSONObject jo = new JSONObject();
		if(root!=null){
			jo = ListUtils.geneTree(root);
		}
		
		HttpServletResponse reponse = ServletActionContext.getResponse();
		ResponseUtils.write(reponse, "["+jo.toString()+"]");
		
		return null;
	}
	
	public String getCombo() throws Exception{
		deptlist = service.getAll();
		JSONArray ja = new JSONArray();
		if(deptlist.size()>0){
			JSONObject jo = new JSONObject();
			for (Department d : deptlist) {
				jo.put("did", d.getId());
				jo.put("dname", d.getDname());
				ja.add(jo);
			}
		}
		
		HttpServletResponse reponse = ServletActionContext.getResponse();
		ResponseUtils.write(reponse, ja);
		return null;
	}
	
	public String getEmpByDept() throws Exception{
		List<Employee> emps = service.getEmps(did);
		
		JSONObject jo = new JSONObject();
		JSONArray ja = JsonUtils.emp2Jsonarray(emps);
		jo.put("rows", ja);
		jo.put("total", 100);

		HttpServletResponse reponse = ServletActionContext.getResponse();
		ResponseUtils.write(reponse, jo);
		
		return null;
	}
	//--------------------------------------------------------
	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public int getDid() {
		return did;
	}

	public void setDid(int did) {
		this.did = did;
	}

	public String getDname() {
		return dname;
	}

	public void setDname(String dname) {
		this.dname = dname;
	}

	public String getDescpt() {
		return descpt;
	}

	public void setDescpt(String descpt) {
		this.descpt = descpt;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public DepartmentService getService() {
		return service;
	}

	public void setService(DepartmentService service) {
		this.service = service;
	}

	public Department getDept() {
		return dept;
	}

	public void setDept(Department dept) {
		this.dept = dept;
	}

	public List<Department> getDeptlist() {
		return deptlist;
	}

	public void setDeptlist(List<Department> deptlist) {
		this.deptlist = deptlist;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

}
