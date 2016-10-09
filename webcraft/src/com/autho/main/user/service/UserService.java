package com.autho.main.user.service;

import java.util.List;
import java.util.Map;

import com.autho.main.role.po.Role;
import com.autho.main.user.po.User;
import com.autho.vo.Page;

public interface UserService {
	public void add(User user, int eid);
	public void delete(int id);
	public void update(User user);
	public User getById(int id);
	public void addRole(int uid, int rid);
	public User login(String username, String passwd);
	public Map<String,Integer> getAuth(List<Role> roles);
	public int getCount();
	public List<User> getAll();
	public List<User> getByPage(Page p);
}
