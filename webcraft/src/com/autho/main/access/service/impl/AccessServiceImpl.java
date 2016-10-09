package com.autho.main.access.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.autho.main.access.dao.AccessDao;
import com.autho.main.access.po.Access;
import com.autho.main.access.service.AccessService;
import com.autho.main.module.dao.ModuleDao;
import com.autho.main.module.po.Module;
import com.autho.main.role.dao.RoleDao;
import com.autho.main.role.po.Role;

@Service("accessService")
public class AccessServiceImpl implements AccessService {
	
	@Resource
	private AccessDao dao;
	@Resource
	private RoleDao rdao;
	@Resource
	private ModuleDao mdao;
	
	public void add(int rid,int mid,int opcode) {
		System.out.println("acl------------------------------------");
		Access access = dao.getByRidMid(rid, mid);
		if(access==null){
			access = new Access();
			Role role = rdao.getById(rid);
			Module module = mdao.getById(mid);
			
			access.setRole(role);
			access.setModule(module);
			access.setOpcode(opcode);
			
			dao.save(access);
		}else{
			access.setOpcode(opcode|access.getOpcode());
		}
	}

	public Access getById(int id) {
		return dao.getById(id);
	}

	public void update(Access access) {
		dao.update(access);
	}

	public RoleDao getRdao() {
		return rdao;
	}

	public void setRdao(RoleDao rdao) {
		this.rdao = rdao;
	}

	public ModuleDao getMdao() {
		return mdao;
	}

	public void setMdao(ModuleDao mdao) {
		this.mdao = mdao;
	}

	public void del(int rid, int mid, int opcode) {
		Access access = dao.getByRidMid(rid, mid);
		int newopcode = access.getOpcode()^opcode;
		if(newopcode>0){
			access.setOpcode(newopcode);
		}else{
			dao.delete(access);
		}
		
	}
	
}
