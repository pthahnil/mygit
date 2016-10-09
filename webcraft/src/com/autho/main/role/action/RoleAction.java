package com.autho.main.role.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.autho.main.role.po.Role;
import com.autho.main.role.service.RoleService;
import com.autho.util.JsonUtils;
import com.autho.util.ResponseUtils;
import com.autho.vo.Page;
import com.opensymphony.xwork2.ActionSupport;

@Controller("roleAction")
@Scope("prototype")
public class RoleAction extends ActionSupport {

	private static final long serialVersionUID = -8051022112333149570L;
	@Resource
	private RoleService service;
	private Role role;
	private List<Role> roles;

	private int id;
	private String rname;

	private int page;
	private int rows;
//-----------------------------------------------------------------------------------------
	/**
	 * 获取所有
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getAll() throws Exception {
		Page p = new Page(page,rows);
		
		roles = service.getByPage(p);
		int count = service.getCount();
		
		JSONObject jo = new JSONObject();
		JSONArray ja = JsonUtils.toJsonArray(roles);
		jo.put("rows", ja);
		jo.put("total", count);

		HttpServletResponse reponse = ServletActionContext.getResponse();
		ResponseUtils.write(reponse, jo);
		return null;
	}
	public String getAll2() throws Exception {
//		Page p = new Page(page,rows);
		
		roles = service.getAll();
		int count = service.getCount();
		
		JSONObject jo = new JSONObject();
//		JSONArray ja = JsonUtils.toJsonArray(roles);
		JSONArray ja = new JSONArray();
		for (Role r : roles) {
			JSONObject j = new JSONObject();
			j.put("role", r);
			ja.add(j);
		}
		jo.put("rows", ja);
		jo.put("total", count);
		
		HttpServletResponse reponse = ServletActionContext.getResponse();
		ResponseUtils.write(reponse, jo);
		return null;
	}

	/**
	 * 添加
	 * 
	 * @return
	 * @throws Exception 
	 */
	public String add() throws Exception {
		
		JSONObject jo = new JSONObject();
		if(id>0){
			role = service.getById(id);
			String or = role.getRname();
			role.setRname(rname);
			service.update(role);
			if(or.equals(role.getRname())){				jo.put("success", true);
				jo.put("msg", "修改失败！");
			}else{
				jo.put("success", true);
			}
			
		}else{
			role = new Role(rname);
			int i = service.add(role);
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
	 * 根据ID获取权限
	 * 
	 * @return
	 */
	public String getById() {
		role = service.getById(id);
		return NONE;
	}
	
	/**
	 * 删除角色
	 * @return
	 * @throws Exception
	 */
	public String delete() throws Exception{
		service.delete(id);
		role = service.getById(id);
		JSONObject jo = new JSONObject();
		if(role!=null){
			jo.put("msg", "删除失败！");
		}else{
			jo.put("success", true);
		}
		
		HttpServletResponse reponse = ServletActionContext.getResponse();
		ResponseUtils.write(reponse, jo);
		return null;
	}
//-----------------------------------------------------------------------------------------
	public RoleService getService() {
		return service;
	}

	public void setService(RoleService service) {
		this.service = service;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getRname() {
		return rname;
	}

	public void setRname(String rname) {
		this.rname = rname;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

}
