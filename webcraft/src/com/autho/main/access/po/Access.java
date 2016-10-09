package com.autho.main.access.po;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.autho.main.module.po.Module;
import com.autho.main.role.po.Role;
import com.autho.main.user.po.User;

@Entity
@Table(name="t_access")
public class Access {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private int opcode;

	@ManyToOne
	private Role role;

	@ManyToOne
	private Module module;

	@ManyToOne
	private User user;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOpcode() {
		return opcode;
	}

	public void setOpcode(int opcode) {
		this.opcode = opcode;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
