package com.autho.main.role.service;

import java.util.List;

import com.autho.main.role.po.Role;
import com.autho.vo.Page;

public interface RoleService {
	public int add(Role role);
	public void delete(int id);
	public void update(Role role);
	public Role getById(int id);
	public List<Role> getAll();
	public int getCount();
	public List<Role> getByPage(Page p);
}
