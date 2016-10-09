package com.autho.main.module.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import com.autho.main.module.dao.ModuleDao;
import com.autho.main.module.po.Module;
import com.autho.main.module.service.ModuleService;
import com.autho.main.role.po.Role;
import com.autho.vo.Page;

@Service("moduleService")
public class ModuleServiceImpl implements ModuleService {
	
	@Resource
	private ModuleDao dao;
	
	public int add(Module module) {
		
		module.setLeaf(1);
		int i = dao.save(module);
		
		Module parent = module.getParent();
		if(parent!=null){
			parent.setLeaf(0);
			dao.update(parent);
		}
		return i;
	}
	
	public void delete(int id) {
		Module module = dao.getById(id);
		dao.delete(module);
	}
	
	public Module getById(int id) {
		return dao.getById(id);
	}
	
	public void update(Module module) {
		dao.update(module);
	}
	
	public List<Module> getAll() {
		return dao.loadAll();
	}

	@Override
	public int getCount() {
		return dao.loadAll()==null?0:dao.loadAll().size();
	}
	public List<Module> getByPage(Page p) {
		Session session = dao.getSession();
		Query q = session.createQuery("from Module");
		q.setFirstResult(p.getStartrow());
		q.setMaxResults(p.getRows());
		return q.list();
	}
}
