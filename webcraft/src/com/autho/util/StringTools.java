package com.autho.util;

public class StringTools {

	public static String firstUpper(String str){
		if(str!=null && !str.equals("")){
			String firstAl = str.substring(0,1);
			String lastAl = str.substring(1);
			return firstAl.toUpperCase()+lastAl;
		}else{
			return null;
		}
	}
	
}
