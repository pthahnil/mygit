package com.web.base;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

import com.web.util.ReflectHelper;
import com.web.util.StringHelper;

/**
 * 
 * @param 
 * @author lxr
 * @date 2016-2-4 上午8:46:31	
 *
 */
public class FastServlet extends HttpServlet {
	
	private static Logger logger = Logger.getLogger(FastServlet.class);
    private ServletConfig servletConfig = null;
    private static final List ignore = Arrays.asList(new String[]{"class", "writer", "postBack", "request", "response", "session", "application"}); //skip getClass,...
    
    //初始化
    public void init(ServletConfig servletConfig){
    	
        this.servletConfig = servletConfig;
    }
    
   /**
    * 主方法
    */
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
    	String path = "";

        String uri = request.getRequestURI();
        String ctxPath = request.getContextPath();
        
        if(StringHelper.isNotEmpty(ctxPath) && uri.startsWith(ctxPath)){
        	uri = uri.substring(ctxPath.length());
        }
        
        if (uri.startsWith("/servlet/")){//以/servlet/则截掉前面/servlet/，生成action
            int pos = uri.indexOf("/", 1);
            path = uri.substring(pos + 1);
        } else{
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        if (!StringHelper.isEmpty(path)){
            if (path.indexOf(".") > 0) {//路径存在.则错误
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            //根据路径生成相应的Action对象
            String className = StringHelper.replace(path, "/", ".");
            Action action = (Action) ReflectHelper.objectForName(className);


            if (action != null && action instanceof Action){
                invokeAction(request, response, action);
            }else{
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }
    
    /**
     * 处理Action
     * @param request
     * @param response
     * @param action
     */
    private void invokeAction(HttpServletRequest request, HttpServletResponse response, Action action){
        try
        {
            ActionResult actionResult = ((Action) action).execute(request, response);
            disposeActionResult(action, actionResult, request, response);
        }
        catch (InvocationTargetException ex)
        {
            logger.error("", ex.getCause());
            showErrorMsg(response);
        }
        catch (Exception ex)
        {
            logger.error("", ex);
            showErrorMsg(response);
        }
    }
    
    /**
     * 处理action结果
     * @param action
     * @param actionResult
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void disposeActionResult(Action action, ActionResult actionResult, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        if (actionResult != null)
        {
            if (actionResult.isDispatch())
            {
                setAttributeValues(action, request);
                request.setAttribute("REQUEST_FROM_INSIDE", new Boolean(true));
                dispatchPage(request, response, actionResult);
            }
            else
            {
                response.sendRedirect(request.getContextPath() + actionResult.getPage());
            }
        }
    }
    
    /**
     * 显示错误信息
     * @param response
     */
    private void showErrorMsg(HttpServletResponse response){
        try{
            response.reset();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }catch (Exception ex){
        }
    }
    
    /**
     * 获取action参数，好存到request中
     * @param action
     * @param request
     */
    private void setAttributeValues(Action action, HttpServletRequest request){
        Map props = extractGetterPropertyValues(action);
        
        for (Object key : props.keySet()) {
        	request.setAttribute((String) key, props.get(key));
		}
    }
    
    private void dispatchPage(HttpServletRequest request, HttpServletResponse response, ActionResult actionResult) throws ServletException, IOException{
        ServletContext context = servletConfig.getServletContext();
        String page = actionResult.getPage();
        RequestDispatcher dispatcher = context.getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }
    
    /**
     *  获得某对象中所有可以GET的属性值
     * @param bean
     * @return
     */
    private Map extractGetterPropertyValues(Object bean){
        PropertyDescriptor[] descr = PropertyUtils.getPropertyDescriptors(bean);
        Map props = new HashMap();
        for (int i = 0; i < descr.length; i++){
            PropertyDescriptor d = descr[i];
            if (d.getReadMethod() == null){
                continue;
            }
            if (ignore.contains(d.getName())){
                continue;
            }

            try{
                props.put(d.getName(), PropertyUtils.getProperty(bean, d.getName()));
            }catch (Exception e){
            }
        }
        return props;
    }
}
