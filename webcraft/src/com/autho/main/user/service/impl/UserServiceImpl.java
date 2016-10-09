package com.autho.main.user.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import com.autho.main.access.po.Access;
import com.autho.main.employee.dao.EmployeeDao;
import com.autho.main.employee.po.Employee;
import com.autho.main.role.dao.RoleDao;
import com.autho.main.role.po.Role;
import com.autho.main.user.dao.UserDao;
import com.autho.main.user.po.User;
import com.autho.main.user.service.UserService;
import com.autho.vo.AuthInfo;
import com.autho.vo.Page;

@Service("userService")
public class UserServiceImpl implements UserService {
	
	@Resource
	private UserDao dao;
	@Resource
	private EmployeeDao edao;
	@Resource
	private RoleDao rdao;
	
	public void add(User user,int eid) {
		Employee emp = edao.getById(eid);
		user.setEmployee(emp);
		dao.save(user);
	}

	public void delete(int id) {
		User user = dao.getById(id);
		dao.delete(user);
	}

	public User getById(int id) {
		return dao.getById(id);
	}

	public void update(User user) {
		dao.update(user);
	}

	public UserDao getDao() {
		return dao;
	}

	public void setDao(UserDao dao) {
		this.dao = dao;
	}

	public EmployeeDao getEdao() {
		return edao;
	}

	public void setEdao(EmployeeDao edao) {
		this.edao = edao;
	}

	public void addRole(int uid, int rid) {
		Role role = rdao.getById(rid);
		User user = dao.getById(uid);
		if(role!=null){
			user.getRoles().add(role);
		}
	}

	public User login(String username, String passwd) {
		return dao.login(username,passwd);
	}

	public Map<String, Integer> getAuth(List<Role> roles) {

		Set<AuthInfo> ause = new HashSet<AuthInfo>();
		Map<String, Integer> aump = new HashMap<String, Integer>();
		
		for (Role role : roles) {
			
			Set<Access> set = role.getAclist();
			
			for (Access acl : set) {
				
				AuthInfo aut = new AuthInfo(acl.getModule().getUrl(),acl.getOpcode(),0);
				boolean b = false;
				
				for (Access acl1 : set) {
					AuthInfo temp = new AuthInfo(acl1.getModule().getUrl(),acl1.getOpcode(),0);
					if(aut.getUrl().equals(temp.getUrl())){
						b = true;
						if(temp.getPriority()>aut.getPriority()){
							ause.add(temp);
						}
					}
				}
				if(!b){
					ause.add(aut);
				}
			}
			
			for (AuthInfo au : ause) {
				aump.put(au.getUrl(), au.getOpCode());
			}
			
		}
		
		return aump;
	}

	@Override
	public int getCount() {
		return dao.loadAll()==null?0:dao.loadAll().size();
	}

	@Override
	public List<User> getAll() {
		return dao.loadAll();
	}
	
	public List<User> getByPage(Page p) {
		Session session = dao.getSession();
		Query q = session.createQuery("from User");
		q.setFirstResult(p.getStartrow());
		q.setMaxResults(p.getRows());
		return q.list();
	}
}
