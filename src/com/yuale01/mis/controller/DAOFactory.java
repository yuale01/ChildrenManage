package com.yuale01.mis.controller;

import com.yuale01.mis.dao.ChildDAO;
import com.yuale01.mis.dao.IChildDAO;

public class DAOFactory {
	
	public static IChildDAO getChildDAO()
	{
		return new ChildDAO();
	}

}
