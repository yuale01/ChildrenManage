package com.yuale01.mis.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.io.IOUtils;

import com.yuale01.mis.controller.DAOFactory;
import com.yuale01.mis.dao.IChildDAO;
import com.yuale01.mis.exception.CommonException;
import com.yuale01.mis.exception.ErrorCode;
import com.yuale01.mis.exception.ErrorMessage;
import com.yuale01.mis.po.Child;

public class ChildrenManagement extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1551802310797999409L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	{
		response.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		try
		{
			IChildDAO childDAO = DAOFactory.getChildDAO();
			String pathInfo = request.getPathInfo();
			if (pathInfo.endsWith("list"))
			{
				List<Child> children = childDAO.getChildren();
				JSONArray jsonChildren = JSONArray.fromObject(children);
				
				out.print(jsonChildren);
				out.flush();
				out.close();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			ErrorMessage em = new ErrorMessage();
			if (e instanceof CommonException)
			{
				em.setErrorCode(((CommonException) e).getErrorCode());
				em.setMessage(((CommonException) e).getLocalizedMessage());
				em.setStatusCode(((CommonException) e).getStatusCode());
				response.setStatus(((CommonException) e).getStatusCode());
			}
			else
			{
				em.setErrorCode(ErrorCode.unknow_internal_error);
				em.setMessage(e.getMessage());
				em.setStatusCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
				response.setStatus(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
			}
			JSONObject errorMessage = JSONObject.fromObject(em);
			out.print(errorMessage);
			out.flush();
			out.close();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
	{
		response.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		try 
		{
			String requestStr = IOUtils.toString(request.getInputStream(), "UTF-8");
			System.out.println(requestStr);
			String pathInfo = request.getPathInfo();
			IChildDAO childDAO = DAOFactory.getChildDAO();
			if (pathInfo.endsWith("create"))
			{
				JSONObject jsonChild = JSONObject.fromObject(requestStr);
				Child child = (Child) JSONObject.toBean(jsonChild, Child.class);
				
				Child result = childDAO.createChild(child);
				out.print(JSONObject.fromObject(result));
				out.flush();
				out.close();
			}
			else if (pathInfo.endsWith("delete"))
			{
				JSONArray jsonArray = JSONArray.fromObject(requestStr);
				Long[] ids = new Long[jsonArray.size()];
				for (int i=0; i<jsonArray.size(); i++)
				{
					ids[i] = jsonArray.getLong(i);
				}
				childDAO.deleteChildren(ids);
				out.print(JSONObject.fromObject("{\"success\":true}"));
				out.flush();
				out.close();
			}
			else if (pathInfo.endsWith("update"))
			{
				JSONObject jsonChild = JSONObject.fromObject(requestStr);
				Child child = (Child) JSONObject.toBean(jsonChild, Child.class);
				childDAO.updateChild(child);
				out.print(JSONObject.fromObject("{\"success\":true}"));
				out.flush();
				out.close();
			}
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			ErrorMessage em = new ErrorMessage();
			if (e instanceof CommonException)
			{
				em.setErrorCode(((CommonException) e).getErrorCode());
				em.setMessage(((CommonException) e).getLocalizedMessage());
				em.setStatusCode(((CommonException) e).getStatusCode());
				response.setStatus(((CommonException) e).getStatusCode());
			}
			else
			{
				em.setErrorCode(ErrorCode.unknow_internal_error);
				em.setMessage(e.getMessage());
				em.setStatusCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
				response.setStatus(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
			}
			JSONObject errorMessage = JSONObject.fromObject(em);
			out.print(errorMessage);
			out.flush();
			out.close();
		}
	}
}
