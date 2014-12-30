package com.yuale01.mis.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.yuale01.mis.manage.ConnectionPoolManager;
import com.yuale01.mis.po.Child;

public class ChildDAO 
{
	public List<Child> getChildren() 
	{
		try 
		{
			ComboPooledDataSource ds = ConnectionPoolManager.getDataSource();
			Connection connection = ds.getConnection();
		} catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Child getChild()
	{
		return null;
	}
	
	public void updateChild(Child child)
	{
		
	}
	
	public void deleteChild(long id)
	{
		
	}
	
	public void createChild(Child child)
	{
		
	}

}
