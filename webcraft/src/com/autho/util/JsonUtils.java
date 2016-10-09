package com.autho.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.autho.main.employee.po.Employee;

public class JsonUtils {

	public static JSONArray toJsonArray(List list){
		Method method = null;
		String fieldName = null;
		JSONArray array=new JSONArray();
		
		if(list!=null & list.size()>0){
			for (Object object : list) {
				Class clazz = object.getClass();// 获取集合中的对象类型
				Field[] fds = clazz.getDeclaredFields();// 获取他的字段数组
				
				JSONObject jo = new JSONObject();
				for (Field field : fds) {
					fieldName = field.getName();
					try {
						method = clazz.getMethod("get" + StringTools.firstUpper (fieldName), null);
						Object value = method.invoke(object, null);
						jo.put(fieldName, value);
						
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					} catch (SecurityException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}
				array.add(jo);
			}
		}
		
		return array;
	}
	
	/**
	 * 人员，为了显示部门
	 * @param list
	 * @return
	 */
	public static JSONArray emp2Jsonarray(List<Employee> list){
		JSONArray array = new JSONArray();
		if(list.size()>0){
			for (Employee e : list) {
				JSONObject jo = new JSONObject();
				jo.put("id", e.getId());
				jo.put("ename", e.getEname());
				jo.put("age", e.getAge());
				jo.put("gender", e.getGender());
				jo.put("phone", e.getPhone());
				jo.put("duties", e.getDuties());
				jo.put("did", e.getDept().getId());
				jo.put("dname", e.getDept().getDname());
				array.add(jo);
			}
		}
		
		return array;
	}
	
}
