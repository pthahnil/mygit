package com.autho.main.department.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;

import com.autho.exception.DepartmentDelException;
import com.autho.main.department.dao.DepartmentDao;
import com.autho.main.department.po.Department;
import com.autho.main.department.service.DepartmentService;
import com.autho.main.employee.dao.EmployeeDao;
import com.autho.main.employee.po.Employee;
import com.autho.vo.Page;

@Service("departmentService")
public class DepartmentServiceImpl implements DepartmentService {
	
	@Resource
	private DepartmentDao dao;
	
	@Resource
	private EmployeeDao edao;
	
	public int add(Department dept, int pid) {
		
		Department parent = dao.getById(pid);
		
		dept.setParent(parent);
		dept.setLeaf(1);
		
		int i = dao.save(dept);
		
		if(parent!=null){
			dept.setRoute(""+parent.getRoute()+"|"+dept.getId());
			parent.setLeaf(0);
			dao.update(parent);
		}else{
			dept.setRoute(""+dept.getId());
		}
		return i;
	}

	public void update(Department dept) {
		
	}

	public void delete(int id) throws DepartmentDelException {
		
		Department dept = dao.getById(id);
		
		if(dept.getChildren()==null || dept.getChildren().size()==0){
			dao.delete(dept);
		}else{
			throw new DepartmentDelException("该机构下有子机构，不能删除！");
		}
	}

	public Department getById(int id) {
		return dao.getById(id);
	}

	public List<Department> getChildren(int pid) {
		return dao.getChildren(pid);
	}

	@Override
	public int getCount() {
		return dao.loadAll()==null?0:dao.loadAll().size();
	}
	
	public List<Department> getByPage(Page p) {
		Session session = dao.getSession();
		Query q = session.createQuery("from Department");
		q.setFirstResult(p.getStartrow());
		q.setMaxResults(p.getRows());
		return q.list();
	}

	@Override
	public List<Department> getAll() {
		String hql = "from Department t order by t.id";
		Session session = dao.getSession();
		Query q = session.createQuery(hql);
		return q.list();
	}

	@Override
	public List<Employee> getEmps(int did) {
		Department dept = dao.getById(did);
		List<Employee> all = new ArrayList<>();
		if(dept!=null){
			all = getFromRoot(dept);
		}
		return all;
	}
	
	//网络接口不能再导航对象中递归，只能这样
	@Override
	public List<Employee> getEmps2(int did) {
		List<Employee> emps = new ArrayList<>();
		
		String sql = "select * from t_employee t where t.dept_id="+did;
		Session session = edao.getSession();
		Query query= session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		for (int i = 0;i<query.list().size();i++) {
			Map<String,Object> map = (Map) query.list().get(i);
			Employee emp = new Employee();
			emp.setId((int)map.get("id"));
			emp.setAge((int)map.get("age"));
			emp.setEname((String)map.get("ename"));
			emp.setGender((String)map.get("gender"));
			emp.setDuties((String)map.get("duties"));
			emps.add(emp);
		}
		return emps;
	}
	
	public List<Employee> getFromRoot(Department dept){
		List<Employee> emps = new ArrayList<>();
		if(dept.getEmployees().size()>0){
			List<Employee> es = dept.getEmployees();
			for (Employee e : es) {
				emps.add(e);
			}
		}
		if(dept.getChildren().size()>0){
			for (Department d : dept.getChildren()) {
				List<Employee> e = getFromRoot(d);
				emps.addAll(e);
			}
		}
		return emps;
	}
}
