package com.web.util;

import org.apache.log4j.Logger;

public class ReflectHelper {

	public ReflectHelper() { }

	public static Class classForName(String name)throws ClassNotFoundException{
		Class cla = null;
		
		cla = Thread.currentThread().getContextClassLoader().loadClass(name);
		if(cla==null){
			cla = Class.forName(name);
		}
        return cla;
    }

	public static Object objectForName(String name){
        Object obj = null;
        try{
        	obj = Class.forName(name).newInstance();
        }catch(Exception e){
        	e.printStackTrace();
        	logger.error("", e);
        }
        return obj;
    }

	private static Logger logger;

	static {
		logger = Logger.getLogger(com.web.util.ReflectHelper.class);
	}
}
