package com.autho.main.module.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.autho.main.module.po.Module;
import com.autho.main.module.service.ModuleService;
import com.autho.util.JsonUtils;
import com.autho.util.ResponseUtils;
import com.autho.vo.Page;
import com.opensymphony.xwork2.ActionSupport;

@Controller("moduleAction")
@Scope("prototype")
public class ModuleAction extends ActionSupport {

	private static final long serialVersionUID = 3912143767209403749L;
	@Resource
	private ModuleService service;
	private Module module;
	private List<Module> modulelist;

	private int id;
	private String mname;
	private String url;
	private String route;
	private int orderNum;

	private int page;
	private int rows;

	/**
	 * 
	 * @return
	 * @throws Exception
	 */

	public String add() throws Exception {
		
		JSONObject jo = new JSONObject();
		if(id>0){
			module = service.getById(id);
			String omname = module.getMname();
			String ourl = module.getUrl();
			String oroute = module.getRoute() ;
			int oorderNum = module.getOrderNum();
			if(omname.equals(mname) && ourl.equals(url) && oroute.equals(route) && oorderNum == orderNum){
				jo.put("success", true);
				jo.put("msg", "修改失败！");
			}else{
				module.setMname(mname);
				module.setUrl(url);
				module.setRoute(route);
				module.setOrderNum(orderNum);
				service.update(module);
				jo.put("success", true);
			}
			
		}else{
			module = new Module(mname, url, route, orderNum);
			int i = service.add(module);
			
			if (i > 0) {
				jo.put("success", true);
			} else {
				jo.put("success", true);
				jo.put("msg", "添加失败！");
			}
		}

		HttpServletResponse reponse = ServletActionContext.getResponse();
		ResponseUtils.write(reponse, jo);
		return null;
	}

	public String getAll() throws Exception {
		Page p = new Page(page, rows);
		modulelist = service.getByPage(p);

		int count = service.getCount();
		JSONObject jo = new JSONObject();
		JSONArray ja = JsonUtils.toJsonArray(modulelist);
		jo.put("rows", ja);
		jo.put("total", count);

		HttpServletResponse reponse = ServletActionContext.getResponse();
		ResponseUtils.write(reponse, jo);
		return null;
	}

	public ModuleService getService() {
		return service;
	}

	public void setService(ModuleService service) {
		this.service = service;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public List<Module> getModulelist() {
		return modulelist;
	}

	public void setModulelist(List<Module> modulelist) {
		this.modulelist = modulelist;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

}
