
package com.web.util;

import java.io.IOException;
import java.util.Enumeration;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class RequestHelper {

	public RequestHelper() { }

	public static String getString(HttpServletRequest request, String fieldName) {
		String value = request.getParameter(fieldName);
		
		if (value != null && value.length() > 0){
			value = StringHelper.getFilterStr(value);
		}
		
		return value != null ? value : "";
	}

	public static String getString(HttpServletRequest request,
			String fieldName, String defaultValue) {
		String value = request.getParameter(fieldName);
		
		if (value != null && value.length() > 0){
			value = StringHelper.getFilterStr(value);
		}
		
		return value != null ? value : defaultValue;
	}

	public static int getInt(HttpServletRequest request, String fieldName){
        String value;
        int intvalue = 0;
        value = getString(request, fieldName);
        if(StringHelper.isEmpty(value)){
        	return 0;
        }
        try{
        	intvalue = Integer.parseInt(value);
        }catch(Exception ex){
        	ex.printStackTrace();
        }
        return intvalue;
    }

	public static int getInt(HttpServletRequest request, String fieldName,
			int defaultValue) {
		int value = getInt(request, fieldName);
		if (value == 0){
			value = defaultValue;
		}
		return value;
	}

	public static long getLong(HttpServletRequest request, String fieldName){
		
        String value  = getString(request, fieldName);
        Long longvalue = 0L;
        if(!StringHelper.isEmpty(value)){
        	longvalue = Long.parseLong(value);
        }
        return longvalue;
    }

	public static long getLong(HttpServletRequest request, String fieldName,
			long defaultValue) {
		long value = getLong(request, fieldName);
		if (value == 0L){
			value = defaultValue;
		}
		return value;
	}

	public static String getStrAttribute(HttpServletRequest request,
			String attributeName) {
		String value = (String) request.getAttribute(attributeName);
		return value != null ? value : "";
	}

	public static String getStrAttribute(HttpServletRequest request,
			String attributeName, String defaultValue) {
		String value = (String) request.getAttribute(attributeName);
		return value != null ? value : defaultValue;
	}

	public static int getIntAttribute(HttpServletRequest request, String attributeName){
        String value = getStrAttribute(request, attributeName);
        int ingvalue = 0;
        
        if(!StringHelper.isEmpty(value)){
        	ingvalue = Integer.parseInt(value);
        }
        return ingvalue;
    }

	public static int getIntAttribute(HttpServletRequest request,
			String attributeName, int defaultValue) {
		int value = getIntAttribute(request, attributeName);
		if (value == 0){
			value = defaultValue;
		}
		return value;
	}

	public static void setIntAttribute(HttpServletRequest request,
			String attributeName, int value) {
		request.setAttribute(attributeName, new Integer(value));
	}

	public static void setStrAttribute(HttpServletRequest request,
			String attributeName, String value) {
		request.setAttribute(attributeName, value);
	}

	public static String[] getStringArray(HttpServletRequest request,
			String fieldName) {
		String valueArray[] = request.getParameterValues(fieldName);
		if (valueArray != null && valueArray.length > 0) {
			for (int i = 0; i < valueArray.length; i++) {
				String value = valueArray[i];
				if (value != null && value.length() > 0){
					valueArray[i] = StringHelper.getFilterStr(value);
				}
			}

		}
		return valueArray;
	}

	public static int[] getIntArray(HttpServletRequest request, String fieldName) {
		String array[] = getStringArray(request, fieldName);
		int value[] = new int[0];
		if (array == null || array.length == 0){
			value = null;
		}else{
			value = new int[array.length];
			for (int i = 0; i < array.length; i++){
				value[i] = Integer.parseInt(array[i]);
			}
		}
		return value;
	}

	public static void dispatchRequest(HttpServletRequest request,
			HttpServletResponse response, String url) throws IOException,
			ServletException {
		RequestDispatcher rd = request.getRequestDispatcher(url);
		rd.forward(request, response);
		rd = null;
	}

	public static boolean isPostBack(HttpServletRequest request) {
		String method = request.getMethod();
		return "POST".equalsIgnoreCase(method);
	}

	public static void removeAllAttribute(HttpServletRequest request) {
		String name;
		for (Enumeration enumeration = request.getAttributeNames(); enumeration.hasMoreElements(); request.removeAttribute(name)){
			name = (String) enumeration.nextElement();
		}

	}

	public static Object getAttribute(HttpServletRequest request,
			String attributeName) {
		Object value = request.getAttribute(attributeName);
		return value != null ? value : null;
	}
}