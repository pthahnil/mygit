package com.autho.main.employee.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import com.autho.main.department.dao.DepartmentDao;
import com.autho.main.department.po.Department;
import com.autho.main.employee.dao.EmployeeDao;
import com.autho.main.employee.po.Employee;
import com.autho.main.employee.service.EmployeeService;
import com.autho.main.role.dao.RoleDao;
import com.autho.vo.Page;

@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService {

	@Resource
	private EmployeeDao dao;
	@Resource
	private DepartmentDao DDao;
	@Resource
	private RoleDao rdao;
	
	public int add(Employee employee, int oid) {
		Department dept = DDao.getById(oid);
		if (dept != null) {
			employee.setDept(dept);
		}
		return dao.save(employee);
	}

	public void delete(int id) {
		Employee employee = dao.getById(id);
		if (employee != null) {
			dao.delete(employee);
		}
	}

	public Employee findById(int id) {
		return dao.getById(id);
	}

	public List<Employee> findByOrgz(int oid) {
		return (List<Employee>) dao.getById(oid);
	}

	public void update(Employee employee) {
		dao.update(employee);
	}

	public EmployeeDao getDao() {
		return dao;
	}

	public void setDao(EmployeeDao dao) {
		this.dao = dao;
	}

	public DepartmentDao getDDao() {
		return DDao;
	}

	public void setDDao(DepartmentDao dDao) {
		DDao = dDao;
	}

	public RoleDao getRdao() {
		return rdao;
	}

	public void setRdao(RoleDao rdao) {
		this.rdao = rdao;
	}

	public List<Employee> getAll() {
		return dao.loadAll();
	}

	@Override
	public int getCount() {
		return dao.loadAll()==null?0:dao.loadAll().size();
	}
	
	public List<Employee> getByPage(Page p) {
		Session session = dao.getSession();
		Query q = session.createQuery("from Employee");
		q.setFirstResult(p.getStartrow());
		q.setMaxResults(p.getRows());
		return q.list();
	}

	@Override
	public Employee getById(int id) {
		return dao.getById(id);
	}
}
