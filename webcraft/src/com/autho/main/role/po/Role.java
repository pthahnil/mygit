package com.autho.main.role.po;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.autho.main.access.po.Access;
import com.autho.main.user.po.User;

@Entity
@Table(name = "t_role")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String rname;

	@OneToMany(mappedBy = "role")
	private Set<Access> aclist;

	@ManyToMany(mappedBy = "roles")
	private Set<User> users;

	public int getId() {
		return id;
	}

	public Role() {
		// TODO Auto-generated constructor stub
	}

	public Role(String rname) {
		super();
		this.rname = rname;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRname() {
		return rname;
	}

	public void setRname(String rname) {
		this.rname = rname;
	}

	public Set<Access> getAclist() {
		return aclist;
	}

	public void setAclist(Set<Access> aclist) {
		this.aclist = aclist;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

}
