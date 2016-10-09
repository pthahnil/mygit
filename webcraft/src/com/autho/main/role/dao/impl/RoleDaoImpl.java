package com.autho.main.role.dao.impl;

import org.springframework.stereotype.Component;

import com.autho.main.basedao.impl.BaseDaoImpl;
import com.autho.main.role.dao.RoleDao;
import com.autho.main.role.po.Role;

@Component("roleDao")
public class RoleDaoImpl extends BaseDaoImpl<Role> implements RoleDao {


}
