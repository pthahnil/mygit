package com.autho.interceptor;

import java.util.List;
import java.util.Map;

import com.autho.main.role.po.Role;
import com.autho.main.user.po.User;
import com.autho.util.Constant;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class AccessInterceptor extends AbstractInterceptor {

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		
		ActionContext context = invocation.getInvocationContext();
		User user = (User) context.getSession().get("user");
		
		if(user==null && !invocation.getProxy().getMethod().equals("login")){
			return "login";
		}else{
			Map<String , Integer> auth = (Map<String, Integer>) context.getSession().get("auth");
			System.out.println("请求的Action名字是："+context.getName());
			System.out.println("请求的Action名字是："+invocation.getProxy().getMethod());
			
			Integer opcd = auth.get(context.getName());
			if(opcd==null){
				return "no_auth";
			}else{
				
				List<Role> roles = (List<Role>) context.getSession().get("roles");
				boolean go = false;
				for (Role role : roles) {
					if(role.getId()==1 || role.getRname().contains("管理员")){
						go = true;
					}
				}
				
				if(go){//管理员 直接通过
					return invocation.invoke();
				}else{
					
					String method = invocation.getProxy().getMethod();
					if(method.contains("add")){//增
						if((opcd & Constant.cre)==0){
							return "no_auth";
						}else{
							return invocation.invoke();
						}
					}else if(method.contains("del")){//删
						if((opcd & Constant.del)==0){
							return "no_auth";
						}else{
							return invocation.invoke();
						}
					}else if(method.contains("update")){//改
						if((opcd & Constant.upd)==0){
							return "no_auth";
						}else{
							return invocation.invoke();
						}
					}else if(method.contains("find")){//查
						if((opcd & Constant.rea)==0){
							return "no_auth";
						}else{
							return invocation.invoke();
						}
					}else{//其他通过
						return invocation.invoke();
					}
				}
			}
		}
	}
}
