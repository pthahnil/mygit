package com.autho.main.table.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.autho.main.table.po.TableInfo;
import com.autho.main.table.service.TableInfoService;
import com.autho.util.JsonUtils;
import com.autho.util.ResponseUtils;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
@Controller("tableAction")
@Scope("prototype")
public class TableInfoAction extends ActionSupport {

	@Resource
	TableInfoService service;
	TableInfo info;
	List<TableInfo> infos = new ArrayList<TableInfo>();

// --------------主方法区-------------------
	public String add() {

		return "success";
	}

	public String getAll() throws Exception {
		infos = service.getAll();
		
		int count = service.getCount();
		JSONObject jo = new JSONObject();
		JSONArray ja = JsonUtils.toJsonArray(infos);
		jo.put("rows", ja);
		jo.put("total", count);
		
		HttpServletResponse reponse = ServletActionContext.getResponse();
		ResponseUtils.write(reponse, jo);
		return null;
	}

// --------------主方法区-------------------

	public TableInfo getInfo() {
		return info;
	}

	public TableInfoService getService() {
		return service;
	}

	public void setService(TableInfoService service) {
		this.service = service;
	}

	public List<TableInfo> getInfos() {
		return infos;
	}

	public void setInfos(List<TableInfo> infos) {
		this.infos = infos;
	}

	public void setInfo(TableInfo info) {
		this.info = info;
	}

}
