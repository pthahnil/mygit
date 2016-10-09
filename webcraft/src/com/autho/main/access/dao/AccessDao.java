package com.autho.main.access.dao;

import com.autho.main.access.po.Access;
import com.autho.main.basedao.BaseDao;

public interface AccessDao extends BaseDao<Access> {

	public Access getByRidMid(int rid, int mid);

}
