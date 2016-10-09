package com.autho.main.role.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import com.autho.main.role.dao.RoleDao;
import com.autho.main.role.po.Role;
import com.autho.main.role.service.RoleService;
import com.autho.vo.Page;

@Service("roleService")
public class RoleServiceImpl implements RoleService {
	
	@Resource
	private RoleDao dao;
	
	public int add(Role role) {
		return dao.save(role);
	}

	public void delete(int id) {
		Role role = dao.getById(id);
		dao.delete(role);
	}

	public Role getById(int id) {
		return dao.getById(id);
	}

	public void update(Role role) {
		dao.update(role);
	}

	public List<Role> getAll() {
		return dao.loadAll();
	}

	public int getCount() {
		return dao.loadAll().size();
	}

	public List<Role> getByPage(Page p) {
		Session session = dao.getSession();
		Query q = session.createQuery("from Role");
		q.setFirstResult(p.getStartrow());
		q.setMaxResults(p.getRows());
		return q.list();
	}

}
