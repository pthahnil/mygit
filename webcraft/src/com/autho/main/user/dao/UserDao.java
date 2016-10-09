package com.autho.main.user.dao;

import com.autho.main.basedao.BaseDao;
import com.autho.main.user.po.User;

public interface UserDao extends BaseDao<User> {

	public User login(String username, String passwd);

}
