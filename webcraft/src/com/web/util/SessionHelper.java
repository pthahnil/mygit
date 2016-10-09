
package com.web.util;

import java.util.Enumeration;
import javax.servlet.http.HttpSession;

public final class SessionHelper {

	public SessionHelper() {}

	public static void setString(String name, String value, HttpSession session) {
		if (!StringHelper.isEmpty(name)) {
			session.setAttribute(name, value);
		}
	}

	public static void setInt(String name, int value, HttpSession session) {
		if (!StringHelper.isEmpty(name)) {
			session.setAttribute(name, new Integer(value));
		}
	}

	public static void setObject(String name, Object value, HttpSession session) {
		if (!StringHelper.isEmpty(name)) {
			session.setAttribute(name, value);
		}
	}

	public static void setBoolean(String name, boolean value,
			HttpSession session) {
		if (!StringHelper.isEmpty(name)) {
			session.setAttribute(name, value);
		}
	}

	public static String getString(String name, HttpSession session) {
		Object value = session.getAttribute(name);
		return value ==null?"":value.toString();
	}

	public static int getInt(String name, HttpSession session) {
		Object value = session.getAttribute(name);
		return value ==null?0:((Integer) value).intValue();
	}

	public static Object getObject(String name, HttpSession session) {
		return session.getAttribute(name);
	}

	public static boolean getBoolean(String name, HttpSession session) {
		Object value = session.getAttribute(name);
		return value ==null?false:((Boolean) value).booleanValue();
	}

	public static void removeAllAttribute(HttpSession session) {
		String name;
		for (Enumeration enumeration = session.getAttributeNames(); enumeration
				.hasMoreElements(); session.removeAttribute(name)){
			name = (String) enumeration.nextElement();
		}

	}
}