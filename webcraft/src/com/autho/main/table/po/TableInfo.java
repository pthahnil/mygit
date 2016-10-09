package com.autho.main.table.po;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 用于做数据库字典，估计还可以做配置字段
 * 
 * @author xr
 * 
 */
@Entity
@Table(name = "t_tableinfo")
public class TableInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String tableName;// 表名
	private String descpt;// 表的描述
	
	@OneToMany(mappedBy = "table")
	private Set<XrField> fields;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getDescpt() {
		return descpt;
	}

	public void setDescpt(String descpt) {
		this.descpt = descpt;
	}

	public Set<XrField> getFields() {
		return fields;
	}

	public void setFields(Set<XrField> fields) {
		this.fields = fields;
	}

}
