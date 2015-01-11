package com.yuale01.mis.dao;

import java.util.List;

import com.yuale01.mis.po.Child;

public interface IChildDAO {
	
	public List<Child> getChildren();
	
	public Child getChild();
	
	public void updateChild(Child child);
	
	public void deleteChild(long id);
	
	public void createChild(Child child);

}
