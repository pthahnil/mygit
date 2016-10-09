package com.web.project;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.web.base.ActionResult;
import com.web.base.BaseAction;
import com.web.jdbc.DataRow;
import com.web.util.RequestHelper;
import com.web.util.StringHelper;

/**
 * 针对项目的servlet
 * @author lxr
 * @date 2016-2-4 上午11:00:21	
 *
 */
public class ProjectBaseAction extends BaseAction
{
	
	private static Logger logger = Logger.getLogger(ProjectBaseAction.class);
	
	public void before(String function) { }
	
	
	/**
	 * 实现Action中的execute方法
	 * 
	 * @param request
	 *            http请求
	 * @param response
	 *            http应带
	 * @return
	 * @throws Exception
	 */
	public ActionResult execute(HttpServletRequest request, HttpServletResponse response) throws Exception{
		return super.execute(request, response);
	}
	
	/**
	 * 向页面写入返回消息
	 * @param message
	 */
	protected void writeMessageToPage(String message){
		PrintWriter writer = null;
		try{
			writer = getResponse().getWriter();
			writer.print(message);
			writer.flush();
		}catch (IOException e){
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		finally{
			if (writer != null){
				writer.close();
				writer = null;
			}
		}
	}
	
	/**
	 * 向页面写入json内容
	 * @param obj
	 */
	protected void writeJsonDataToPage(Object obj){
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("data", obj);
		PrintWriter writer = null;
		try{
			writer = getResponse().getWriter();
			writer.print(jsonObj.toString());
			writer.flush();
			writer.close();
		}catch (IOException e){
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		finally{
			if (writer != null){
				writer.close();
				writer = null;
			}
		}
	}
	/**
	 * 获取当前用户消息
	 * @return
	 */
	public DataRow ftUserInfo(){
		DataRow userInfo = new DataRow();
		userInfo.set("username",getSession().getAttribute("username"));
		userInfo.set("passwd",getSession().getAttribute("passwd"));
		return userInfo;
	}
	
	/**
	 * 过滤参数
	 */
	public static String encodeURL(String source){
		if (source == null){
			return null;
		}
		 String html = new String(source);
	     html = StringHelper.replace(html, "<", "&lt;");
	     html = StringHelper.replace(html, ">", "&gt;");
	     html = StringHelper.replace(html, "\"", "&quot;");
	     html = StringHelper.replace(html, " ", "&nbsp;");
	     html = StringHelper.replace(html, "\'", "&acute;");
	     html = StringHelper.replace(html, "\\", "&#092;");
	     html = StringHelper.replace(html, "&", "&amp;");
	     html = StringHelper.replace(html, "\r", "");
	     html = StringHelper.replace(html, "\n", "");
	     html = StringHelper.replace(html, "(", "&#40;");
	     html = StringHelper.replace(html, ")", "&#41;");
	     html = StringHelper.replace(html, "[", "&#91;");
	     html = StringHelper.replace(html, "]", "&#93;");
	     html = StringHelper.replace(html, ";", "&#59;");
	     html = StringHelper.replace(html, "/", "&#47;");
	     
		return html;
	}

}
