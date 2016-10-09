package com.autho.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.autho.main.department.po.Department;

public class ListUtils {
	/**
	 * 获取根元素
	 * @param depts
	 * @return
	 */
	public static Department getRoot(List<Department> depts){
		if(depts.size()==1){
			return depts.get(0);
		}else{
			for (Department d : depts) {
				if(d.getParent()==null){
					return d;
				}
			}
		}
		return null;
	}
	
	public static JSONObject geneTree(Department root){
		JSONObject jo = new JSONObject();
		jo.put("id", root.getId());
		jo.put("text", root.getDname());
		jo.put("dname", root.getDname());
		jo.put("descpt", root.getDescpt());
		List<Department> children = root.getChildren();
		if(children.size()>0){
			JSONArray ja = new JSONArray();
			for (Department d : children) {
				JSONObject joo = geneTree(d);
				ja.add(joo);
			}
			jo.put("children", ja);
		}
		
		return jo;
	}
	public static void main(String[] args) {
		Department d1 = new Department();
		d1.setId(1);
		d1.setDname("aa");
		Department d2 = new Department();
		d2.setId(2);
		d2.setDname("bb");
		
		d1.getChildren().add(d2);
		
		List<Department> dps = new ArrayList<>();
		dps.add(d1);
		dps.add(d2);
		
		Department root = getRoot(dps);
		System.out.println(root.getDname());
	}
}
