package com.yuale01.mis.controller;

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
				
				cpds.setMinPoolSize(3);                                     
				cpds.setAcquireIncrement(3);
				cpds.setMaxPoolSize(15);
				
				cpds.setIdleConnectionTestPeriod(14400);
				cpds.setPreferredTestQuery("SELECT 1");
				
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
