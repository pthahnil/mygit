package com.autho.main.module.po;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.autho.main.access.po.Access;

@Entity
@Table(name = "t_module")
public class Module {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String mname;
	private String url;
	private String route;
	private int orderNum;

	private int leaf;

	@OneToMany(mappedBy = "module")
	private Set<Access> aclist;

	@ManyToOne
	private Module parent;

	@OneToMany(mappedBy = "parent")
	private Set<Module> modules;

	public Module() {

	}

	public Module(String mname, String url, String route, int orderNum) {
		super();
		this.mname = mname;
		this.url = url;
		this.route = route;
		this.orderNum = orderNum;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Set<Access> getAclist() {
		return aclist;
	}

	public void setAclist(Set<Access> aclist) {
		this.aclist = aclist;
	}

	public String getMname() {
		return mname;
	}

	public void setMname(String mname) {
		this.mname = mname;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public Module getParent() {
		return parent;
	}

	public void setParent(Module parent) {
		this.parent = parent;
	}

	public Set<Module> getModules() {
		return modules;
	}

	public void setModules(Set<Module> modules) {
		this.modules = modules;
	}

	public int getLeaf() {
		return leaf;
	}

	public void setLeaf(int leaf) {
		this.leaf = leaf;
	}

}
