package com.web.util;

public class ConvertHelper {

	public ConvertHelper() { }

	public static int strToInt(String str){
		int val = 0;
		if(str!=null && !str.equals("")){
			try{
				val = Integer.parseInt(str);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
        return val;
    }

	public static long strToLong(String str){
		long val = 0l;
        if(str!=null && !str.equals("")){
        	try{
        		val = Long.parseLong(str);
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        }
        return val;
    }

	public static float strToFloat(String str){
		float val = 0f;
        if(str!=null && !str.equals("")){
        	try{
        		val = Float.parseFloat(str);
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        }
        return val;
    }

	public static double strToDouble(String str){
		double val = 0.0;
        if(str!=null && !str.equals("")){
        	try{
        		val = Double.parseDouble(str);
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        }
        return val;
    }

	public static int[] strArrayToIntArray(String strArray[]) {
		int intArray[] = new int[strArray.length];
		for (int i = 0; i < strArray.length; i++){
			intArray[i] = strToInt(strArray[i]);
		}
		return intArray;
	}
}