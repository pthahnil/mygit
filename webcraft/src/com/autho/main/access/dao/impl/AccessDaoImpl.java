package com.autho.main.access.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.autho.main.access.dao.AccessDao;
import com.autho.main.access.po.Access;
import com.autho.main.basedao.impl.BaseDaoImpl;

@Component("accessDao")
public class AccessDaoImpl extends BaseDaoImpl<Access> implements AccessDao {

	public Access getByRidMid(int rid, int mid) {
		
		String hql = "from Access a where a.role.id=? and a.module.id=?";
		List<Access> list = find(hql, new Object[]{rid,mid});

		if(list==null || list.size()==0){
			return null;
		}else{
			return list.get(0);
		}
	}

}
