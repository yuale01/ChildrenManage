package com.yuale01.mis.manage;

import java.beans.PropertyVetoException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ConnectionPoolManager 
{
	private static ComboPooledDataSource cpds = null;
	
	private ConnectionPoolManager()
	{
		
	}
	
	public synchronized static ComboPooledDataSource getDataSource()
	{
		if (cpds == null)
		{
			cpds = new ComboPooledDataSource();
			try {
				cpds.setDriverClass("com.mysql.jdbc.Driver");
				cpds.setJdbcUrl("jdbc:mysql://localhost:3306/Children?&useUnicode=true&characterEncoding=utf8");
				cpds.setUser("root");
				cpds.setPassword("root");
				
				cpds.setMaxStatements( 180 );
				
				cpds.setMinPoolSize(5);                                     
				cpds.setAcquireIncrement(5);
				cpds.setMaxPoolSize(20);
				
			} catch (PropertyVetoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return cpds;
				
	}
	
	public synchronized  static void closeDataSource()
	{
		if (cpds != null)
			cpds.close();
	}

}
