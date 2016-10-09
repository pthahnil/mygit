package com.web.base;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.log4j.Logger;

import com.web.util.ConvertHelper;
import com.web.util.RequestHelper;
import com.web.util.StringHelper;

public class BaseAction implements Action {
	
	 private static Logger logger = Logger.getLogger(BaseAction.class);


	    private ArrayList actionErrors = new ArrayList();

	    private ArrayList actionMessages = new ArrayList();


	    private HttpServletRequest request = null;

	    private HttpServletResponse response = null;
	
	
	public ActionResult execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		this.request = request;
        this.response = response;
		return execute();
	}
	
	private ActionResult execute() throws Exception{
        try{
            String function = RequestHelper.getString(request, "function");
            String method = (function.length() == 0) ? "default" : function;
            method = "do" + method.substring(0, 1).toUpperCase() + method.substring(1);

            long startTime = System.currentTimeMillis();
            if (logger.isInfoEnabled())
            {
                logger.info("开始执行 [" + getClass().getName() + "." + method + "]");
            }

            //在进入相应的function对应的方法前，先调用before，在调用相应的方法后，再调用after
            before(function);
            ActionResult result = (ActionResult) MethodUtils.invokeMethod(this, method, null);
            after(function);
            this.setAttribute("module", this.getStrParameter("module"));
            if (logger.isInfoEnabled())
            {
                logger.info("执行完成 [" + getClass().getName() + "." + method + "] times=" + String.valueOf(System.currentTimeMillis() - startTime) + " millisecond");
            }
            return result;
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }
	
	public void before(String function){    }
	
	public void after(String function){    }
	 
	public ActionResult doDefault() throws Exception{
       return null;
    }
	 
	public HttpServletResponse getResponse(){
        return response;
    }
	 
	public PrintWriter getWriter(){
        try{
            return response.getWriter();
        }catch (IOException ex){
            ex.printStackTrace();
        }
        return null;
    }
	
	public HttpServletRequest getRequest(){
        return request;
    }
	
	public HttpSession getSession(){
        return getRequest().getSession();
    }
	
	public ServletContext getApplication(){
        return getSession().getServletContext();
    }
	
	public void addActionError(String error){
        actionErrors.add(error);
    }
	
	public List getActionErrors(){
        return actionErrors;
    }
	
	public boolean hasActionErrors(){
        return actionErrors.size() > 0;
    }
	
	public void addActionMessages(String message){
        actionMessages.add(message);
    }
	
	public List getActionMessages(){
        return actionMessages;
    }
	
	public boolean hasActionMessages(){
        return actionMessages.size() > 0;
    }
	
	public void clearErrorsAndMessages(){
        actionMessages.clear();
        actionErrors.clear();
    }
	
	public boolean isPostBack(){
        String method = getRequest().getMethod();
        boolean b = false;
        if ("POST".equalsIgnoreCase(method)){
           b = true;
        }
        return b;
    }
	
	/**
     * 辅佐方法，显示请求参数.
     */
    protected final void dumpRequest(){
        Enumeration e = getRequest().getParameterNames();
        while (e.hasMoreElements()){
            String key = (String) e.nextElement();
            String[] paramValues = getRequest().getParameterValues(key);
            for (int i = 0; i < paramValues.length; i++){
                logger.debug(" " + key + " : " + paramValues[i]);
            }
        }
    }
    
    /**
     * 返回字串Parameter,若不存在，则返回空字串
     *
     * @param fieldName 表单字段名称
     * @return 字符串，如果为NULL返回空字符
     */
    public String getStrParameter(String fieldName){
        String value = RequestHelper.getString(request, fieldName);
        return value;
    }
    
    /**
     * 返回字串Parameter,若不存在，则返回缺省值
     *
     * @param fieldName    表单字段名称
     * @param defaultValue 缺省值
     * @return
     */
    public String getStrParameter(String fieldName, String defaultValue){
        String value = RequestHelper.getString(request, fieldName, defaultValue);
        return value;
    }
    
    /**
     * 返回字串数组Parameter，若不存在，则返回空字符串数组
     *
     * @param fieldName 字段名称
     * @return 字符串数组
     */
    public String[] getStrArrayParameter(String fieldName){
        String[] valueArray = request.getParameterValues(fieldName);
        if (valueArray != null && valueArray.length > 0){
            for (int i = 0; i < valueArray.length; i++){
                valueArray[i] = StringHelper.encodeHtml(valueArray[i]);
            }
            return valueArray;
        }else{
            return new String[0];
        }
    }
    
    /**
     * 返回整数，若不存在或转换失败，则返回0
     *
     * @param name
     * @return
     */
    public int getIntParameter(String name){
        return ConvertHelper.strToInt(getStrParameter(name));
    }
    
    /**
     * 返回整数，若不存在或转换失败，则返回缺省值
     *
     * @param name
     * @param defaultValue
     * @return
     */
    public int getIntParameter(String name, int defaultValue){
        String value = getStrParameter(name);
        if (value != null && value.length() > 0){
            value = StringHelper.encodeHtml(request.getParameter(name));
        }
        if (StringHelper.isEmpty(value)){
            return defaultValue;
        }else{
            return ConvertHelper.strToInt(value);
        }
    }
    
    /**
     * 返回整数数组，若不存在，则返回长度为0的整型数组
     *
     * @param name
     * @return
     */
    public int[] getIntArrayParameter(String name){
        String[] valueArray = getStrArrayParameter(name);
        int[] result = new int[valueArray.length];
        for (int i = 0; i < valueArray.length; i++){
            result[i] = ConvertHelper.strToInt(valueArray[i]);
        }
        return result;
    }
    
    /**
     * 返回长整数，若不存在或转换失败，则返回0
     *
     * @param name
     * @return
     */
    public long getLongParameter(String name){
        return ConvertHelper.strToLong(getStrParameter(name));
    }
    
    /**
     * 返回长整数，若不存在或转换失败，则返回0
     *
     * @param name
     * @return
     */
    public long getLongParameter(String name, long defaultValue){
        String value = getStrParameter(name);
        if (value != null && value.length() > 0){
            value = StringHelper.encodeHtml(request.getParameter(name));
        }
        if (StringHelper.isEmpty(value)){
            return defaultValue;
        }else{
            return ConvertHelper.strToLong(value);
        }
    }
    
    /**
     * 返回长整数数组，若不存在，则返回长度为0的长整型数组
     *
     * @param name
     * @return
     */
    public long[] getLongArrayParameter(String name){
        String[] valueArray = getStrArrayParameter(name);
        long[] result = new long[valueArray.length];
        for (int i = 0; i < valueArray.length; i++){
            result[i] = ConvertHelper.strToLong(valueArray[i]);
        }
        return result;
    }
    
    /**
     * 从HttpServletRequest中提取属性值
     *
     * @param attributeName 属性名称
     * @return
     * @deprecated
     */
    public String getStrAttribute(String attributeName){
        String value = (String) request.getAttribute(attributeName);
        return value == null ? "" : value;
    }


    /**
     * 从HttpServletRequest中提取属性值
     *
     * @param attributeName 属性名称
     * @param defaultValue  缺省值
     * @return
     */
    public String getStrAttribute(String attributeName, String defaultValue){
        String value = (String) request.getAttribute(attributeName);
        return value == null ? defaultValue : value;
    }
    
    /**
     * 从HttpServletRequest中提取属性值
     *
     * @param attributeName 属性名称
     * @return
     */
    public int getIntAttribute(String attributeName){
        String value = getStrAttribute(attributeName);
        if (StringHelper.isEmpty(value)){
            return 0;
        }
        try{
            return new Integer(value).intValue();
        }catch (Exception ex){
            return 0;
        }
    }


    /**
     * 从HttpServletRequest中提取属性值
     *
     * @param attributeName 属性名称
     * @param defaultValue  缺省值
     * @return
     */
    public int getIntAttribute(String attributeName, int defaultValue){
        int value = getIntAttribute(attributeName);
        if (value == 0){
            value = defaultValue;
        }
        return value;
    }


    /**
     * 向HttpServletRequest中设置属性值
     *
     * @param attributeName 属性名称
     * @param value         属性值
     */
    public void setIntAttribute(String attributeName, int value){
        request.setAttribute(attributeName, new Integer(value));
    }


    /**
     * 从HttpServletRequest中提取属性值
     *
     * @param attributeName
     * @return
     */
    public Object getAttribute(String attributeName){
        return getRequest().getAttribute(attributeName);
    }


    /**
     * 向HttpServletRequest中设置对象
     *
     * @param attributeName
     * @param object
     */
    public void setAttribute(String attributeName, Object object){
        getRequest().setAttribute(attributeName, object);
    }


    /**
     * 描述:设置操作返回消息
     *
     * @param resultMap 返回操作结果
     * @param showInfo  当ret_code=0,前端显示信息
     */
    public void setActionMsg(Map resultMap, String showInfo){
        int ret_code = Integer.parseInt(resultMap.get("flag").toString());
        if (ret_code != 0){
            this.addActionError(resultMap.get("message").toString());
        }else{
            this.addActionError(showInfo);
        }
    }


    /**
     * 描述:设置操作返回消息
     *
     * @param resultMap 返回操作结果
     * @param showInfo  当ret_code=0表示交易成功，当ret_code!=0表示交易失败
     */
    public int getActionMsgs(Map resultMap, String showInfo){
        int ret_code = Integer.parseInt(resultMap.get("flag").toString());
        if (ret_code != 0)
        {
            this.addActionError(resultMap.get("message").toString());
            return ret_code;
        }else{
            this.addActionError(showInfo);
            return 0;
        }
    }


    /**
     * 获得IP地址
     *
     * @return
     */
    public String getIpAddr(){
        HttpServletRequest request = getRequest();
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
