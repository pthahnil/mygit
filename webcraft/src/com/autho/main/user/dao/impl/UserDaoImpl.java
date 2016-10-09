package com.autho.main.user.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.autho.main.basedao.impl.BaseDaoImpl;
import com.autho.main.user.dao.UserDao;
import com.autho.main.user.po.User;

@Component("userDao")
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {

	public User login(String username, String passwd) {
		
		String hql = "from User u where u.username=? and u.passwd=?";
		
		List<User> users = find(hql, new Object[]{username,passwd});
		if(users==null || users.size()==0){
			return null;
		}else{
			return users.get(0);
		}
	}
}
