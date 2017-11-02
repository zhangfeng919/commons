package com.zhangf.base.data;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class DbServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		PrintWriter pw = resp.getWriter();
		
		Map<String, String> data = new HashMap<String, String>();
		data.put("aa", "11");
		data.put("ab", "22");
		
		Gson gson = new Gson();
		
		
		
		try {
			pw.write(gson.toJson(data));
		} catch (Exception e) {
			if(pw != null)
			pw.close();
		}
		
		super.doPost(req, resp);
	}
	
	
	

}
