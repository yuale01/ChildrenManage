package com.yuale01.mis.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.io.IOUtils;

import com.yuale01.mis.dao.IChildDAO;
import com.yuale01.mis.po.BasicInfo;
import com.yuale01.mis.po.BodyInfo;
import com.yuale01.mis.po.Child;
import com.yuale01.mis.po.ContactInfo;
import com.yuale01.mis.utils.DAOFactory;

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
			String pathInfo = request.getPathInfo();
			if (pathInfo.endsWith("list"))
			{
				response.setCharacterEncoding("utf-8");
				PrintWriter out = response.getWriter();
				out.print("{'aa': '1'}");
				out.flush();
				out.close();
			}
			if (pathInfo.endsWith("create"))
			{
				Child child = new Child();
				BasicInfo basicInfo = new BasicInfo();
				basicInfo.setName("test");
				basicInfo.setBirthday("1987-08-22");
				basicInfo.setGender(0);
				basicInfo.setClassName("class1");
				basicInfo.setGrade("grade1");
				basicInfo.setIdCardNo("130303198708222110");
				basicInfo.setMigaration(true);
				basicInfo.setSpecialPerformance("this is special");
				
				ContactInfo contactInfo = new ContactInfo();
				contactInfo.setMotherName("wang");
				contactInfo.setMotherCompany("comp");
				contactInfo.setMotherIdCard("130303169870888552");
				contactInfo.setMotherContact("afdsfdf");
				contactInfo.setFatherName("fang");
				contactInfo.setFatherCompany("comp");
				contactInfo.setFatherIdCard("130303169870888552");
				contactInfo.setFatherContact("afdsfdf");
				
				BodyInfo bodyInfo = new BodyInfo();
				bodyInfo.setDoffOn(0);
				bodyInfo.setEating(1);
				bodyInfo.setSleeping(2);
				bodyInfo.setSleepingInfo("sleeping info");
				
				child.setBasicInfo(basicInfo);
				child.setContactInfo(contactInfo);
				child.setBodyInfo(bodyInfo);
				
				IChildDAO childDAO = DAOFactory.getChildDAO();
				childDAO.createChild(child);
				
				
			}
			System.out.println(pathInfo);
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
			String pathInfo = request.getPathInfo();
			if (pathInfo.endsWith("create"))
			{
				Child child = new Child();
				BasicInfo basicInfo = new BasicInfo();
				basicInfo.setName("test");
				basicInfo.setBirthday("1987-08-22");
				basicInfo.setGender(0);
				basicInfo.setClassName("class1");
				basicInfo.setGrade("grade1");
				basicInfo.setIdCardNo("130303198708222110");
				basicInfo.setMigaration(true);
				basicInfo.setSpecialPerformance("this is special");
				
				ContactInfo contactInfo = new ContactInfo();
				contactInfo.setMotherName("wang");
				contactInfo.setMotherCompany("comp");
				contactInfo.setMotherIdCard("130303169870888552");
				contactInfo.setMotherContact("afdsfdf");
				contactInfo.setFatherName("fang");
				contactInfo.setFatherCompany("comp");
				contactInfo.setFatherIdCard("130303169870888552");
				contactInfo.setFatherContact("afdsfdf");
				
				BodyInfo bodyInfo = new BodyInfo();
				bodyInfo.setDoffOn(0);
				bodyInfo.setEating(1);
				bodyInfo.setSleeping(2);
				bodyInfo.setSleepingInfo("sleeping info");
				
				child.setBasicInfo(basicInfo);
				child.setContactInfo(contactInfo);
				child.setBodyInfo(bodyInfo);
				
				IChildDAO childDAO = DAOFactory.getChildDAO();
				childDAO.createChild(child);
				
				
			}
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
