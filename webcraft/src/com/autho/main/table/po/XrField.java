package com.autho.main.table.po;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 表的字段
 * 
 * @author xr
 * 
 */
@Entity
@Table(name = "t_fields")
public class XrField {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String fieldName;// 字段名
	private String fieldHeader;// 页面显示名，如表头显示
	private String fieldDescpt;// 字段描述

	private String isVisible;// 是否可见

	@ManyToOne
	private TableInfo table;

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldDescpt() {
		return fieldDescpt;
	}

	public void setFieldDescpt(String fieldDescpt) {
		this.fieldDescpt = fieldDescpt;
	}

	public String getIsVisible() {
		return isVisible;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFieldHeader() {
		return fieldHeader;
	}

	public void setFieldHeader(String fieldHeader) {
		this.fieldHeader = fieldHeader;
	}

	public void setIsVisible(String isVisible) {
		this.isVisible = isVisible;
	}

	public TableInfo getTable() {
		return table;
	}

	public void setTable(TableInfo table) {
		this.table = table;
	}

}
