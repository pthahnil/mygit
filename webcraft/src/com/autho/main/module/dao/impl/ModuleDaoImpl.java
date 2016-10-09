package com.autho.main.module.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.autho.main.basedao.impl.BaseDaoImpl;
import com.autho.main.module.dao.ModuleDao;
import com.autho.main.module.po.Module;

@Component("moduleDao")
public class ModuleDaoImpl extends BaseDaoImpl<Module> implements ModuleDao {
	
	
}
