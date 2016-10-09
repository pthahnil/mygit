package com.autho.main.user.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.autho.main.role.po.Role;
import com.autho.main.user.po.User;
import com.autho.main.user.service.UserService;
import com.autho.util.JsonUtils;
import com.autho.util.ResponseUtils;
import com.autho.vo.Page;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@Controller("userAction")
@Scope("prototype")
public class UserAction extends ActionSupport {

	@Resource
	private UserService service;
	private User user;

	private int eid;
	private int uid;
	private int rid;

	private String username;
	private String passwd;

	private List<User> users = new ArrayList<>();
	
	private int page;
	private int rows;
	/**
	 * 添加账号
	 * 
	 * @return
	 */
	public String add() {
		service.add(user, eid);
		return "success";
	}

	public String getAll() throws Exception {
		Page p = new Page(page,rows);
		users = service.getByPage(p);
		
		int count = service.getCount();
		JSONObject jo = new JSONObject();
		JSONArray ja = JsonUtils.toJsonArray(users);
		jo.put("rows", ja);
		jo.put("total", count);

		HttpServletResponse reponse = ServletActionContext.getResponse();
		ResponseUtils.write(reponse, jo);
		return "all";
	}

	/**
	 * 添加赋予角色
	 * 
	 * @return
	 */
	public String addRole() {
		service.addRole(uid, rid);
		return "success";
	}

	/**
	 * 登录
	 * 
	 * @return
	 * @throws Exception
	 */
	public String login() throws Exception {
		user = service.login(username, passwd);
		if (user == null) {
			return "login";
		} else {
			ActionContext.getContext().getSession().put("user", user);
			List<Role> roles = user.getRoles();
			if (roles != null || roles.size() > 0) {
				Map<String, Integer> auth = service.getAuth(roles);
				ActionContext.getContext().getSession().put("auth", auth);
				ActionContext.getContext().getSession().put("roles", roles);
			}
		}
		return "index";
	}

	public UserService getService() {
		return service;
	}

	public void setService(UserService service) {
		this.service = service;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getEid() {
		return eid;
	}

	public void setEid(int eid) {
		this.eid = eid;
	}

	public int getUid() {
		return uid;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public void setUid(int uid) {
		this.uid = uid;
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

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

}
