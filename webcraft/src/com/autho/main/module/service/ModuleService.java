package com.autho.main.module.service;

import java.util.List;

import com.autho.main.module.po.Module;
import com.autho.vo.Page;

public interface ModuleService {
	public int add(Module module);
	public void delete(int id);
	public void update(Module module);
	public Module getById(int id);
	public List<Module> getAll();
	public int getCount();
	public List<Module> getByPage(Page p);
}
