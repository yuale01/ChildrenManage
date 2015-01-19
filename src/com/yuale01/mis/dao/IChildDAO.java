package com.yuale01.mis.dao;

import java.util.List;

import com.yuale01.mis.po.Child;

public interface IChildDAO {
	
	public List<Child> getChildren();
	
	public Child getChild(Long id);
	
	public void updateChild(Child child);
	
	public void deleteChildren(Long[] ids);
	
	public void createChild(Child child);

}
