package com.yuale01.mis.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.io.IOUtils;

import com.yuale01.mis.po.BasicInfo;

public class ChildrenManagement extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1551802310797999409L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			String json = "{'aa': '1'}";
			JSONObject jsonObject = JSONObject.fromObject( json );  
			BasicInfo bean = (BasicInfo) JSONObject.toBean( jsonObject, BasicInfo.class );
			String path = request.getPathInfo();
			if (path.endsWith("list"))
			{
				response.setCharacterEncoding("utf-8");
				PrintWriter out = response.getWriter();
				out.print("{'aa': '1'}");
				out.flush();
				out.close();
			}
			System.out.println(path);
		}
		catch (Exception e)
		{
			
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
	{
		try 
		{
			String requestStr = IOUtils.toString(request.getInputStream(), "UTF-8");
			System.out.println(requestStr);
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
