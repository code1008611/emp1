package com.util;

import com.alibaba.fastjson.serializer.PropertyFilter;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class AJAxUtil {
	public static void printString(HttpServletResponse response,String s){
		response.setCharacterEncoding("utf-8");
		try {
			PrintWriter out=response.getWriter();
			out.print(s);
			out.flush();
			out.close();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	public static PropertyFilter filterProperts(final String...propNames){
		PropertyFilter propertyFilter=new PropertyFilter() {

			@Override
			public boolean apply(Object arg0, String propertyName, Object arg2) {
				for (String pname : propNames) {
					if(propertyName.equals(pname)){
						return false;
					}
				}
				return true;
			}
		};

		return propertyFilter;
	}
}
