
package com.web.jdbc;

import java.util.HashMap;

public class DataRow extends HashMap {

	public DataRow(){ }

	public void set(String name, String value) {
		if (name == null || name.equals("")){
			return;
		}
		put(name, value);
	}

	public void set(String name, int value) {
		put(name, new Integer(value));
	}

	public void set(String name, boolean value) {
		put(name, new Boolean(value));
	}

	public void set(String name, long value) {
		put(name, new Long(value));
	}

	public void set(String name, float value) {
		put(name, new Float(value));
	}

	public void set(String name, double value) {
		put(name, new Double(value));
	}

	public void set(String name, Object value) {
		put(name, value);
	}

	public String getString(String name) {
		if (name == null || name.equals("")) {
			return "";
		} else {
			Object obj = get(name);
			return obj != null ? obj.toString() : "";
		}
	}

	public int getInt(String name) {
		if (name == null || name.equals("")){
			return 0;
		}
		int value = 0;
		if (!containsKey(name)){
			return 0;
		}
		Object obj = get(name);
		if (obj == null){
			return 0;
		}
		if (!(obj instanceof Integer)) {
			try {
				value = Integer.parseInt(obj.toString());
			} catch (Exception ex) {
				value = 0;
			}
		} else {
			value = ((Integer) obj).intValue();
			obj = null;
		}
		return value;
	}

	public long getLong(String name) {
		if (name == null || name.equals("")){
			return 0L;
		}
		long value = 0L;
		if (!containsKey(name)){
			return 0L;
		}
		Object obj = get(name);
		if (obj == null){
			return 0L;
		}
		if (!(obj instanceof Long)) {
			try {
				value = Long.parseLong(obj.toString());
			} catch (Exception ex) {
				value = 0L;
			}
		} else {
			value = ((Long) obj).longValue();
			obj = null;
		}
		return value;
	}

	public float getFloat(String name) {
		if (name == null || name.equals("")){
			return 0.0F;
		}
		float value = 0.0F;
		if (!containsKey(name)){
			return 0.0F;
		}
		Object obj = get(name);
		if (obj == null){
			return 0.0F;
		}
		if (!(obj instanceof Float)) {
			try {
				value = Float.parseFloat(obj.toString());
			} catch (Exception ex) {
				value = 0.0F;
			}
		} else {
			value = ((Float) obj).floatValue();
			obj = null;
		}
		return value;
	}

	public double getDouble(String name) {
		double bk = 0.0d;
		if(name!=null && !name.equals("")){
			if(containsKey(name)){
				Object o = get(name);
				if(o!=null){
					if(o instanceof Double){
						bk = ((Double)o).doubleValue();
					}else{
						bk = Double.valueOf(o.toString()).doubleValue();
					}
				}
			}
		}
		
		return bk;
	}

	public boolean getBoolean(String name) {
		boolean bk = false;
		if(name!=null && !name.equals("")){
			if(containsKey(name)){
				Object o = get(name);
				if(o!=null){
					if(o instanceof Boolean){
						bk = ((Boolean)o).booleanValue();
					}else{
						bk = Boolean.valueOf(o.toString()).booleanValue();
					}
				}
			}
		}
		
		return bk;
	}

	public Object getObject(String name) {
		if (name == null || name.equals("")){
			return null;
		}
		if (!containsKey(name)){
			return null;
		}else{
			return get(name);
		}
	}
}