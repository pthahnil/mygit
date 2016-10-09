package com.autho.util;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

public class ResponseUtils {
	
	public static void write(HttpServletResponse response, Object o)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(o.toString());
		out.flush();
		out.close();
	}
	
	public static void export(HttpServletResponse response, Object o)
			throws Exception {
		// 导出excel时文件名带一个小尾巴
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		// 设置一下响应的文件格式和文件名
		response.reset();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + format.format(new Date()) + ".xls\"");
		PrintWriter out = response.getWriter();
		out.print(o.toString());
		out.flush();
		out.close();
	}
	
}
