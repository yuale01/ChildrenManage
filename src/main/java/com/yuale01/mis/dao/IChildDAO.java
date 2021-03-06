package com.yuale01.mis.dao;

import java.util.List;
import java.util.Locale;

import com.yuale01.mis.exception.CommonException;
import com.yuale01.mis.po.Child;

public interface IChildDAO {

    public List<Child> getChildren() throws CommonException;

    public Child getChild(Long id) throws CommonException;

    public Child updateChild(Child child, Long id) throws CommonException;

    public void deleteChildren(Long[] ids) throws CommonException;

    public void deleteChild(Long id) throws CommonException;

    public Child createChild(Child child) throws CommonException;

    //public void exportChildren(String locale) throws CommonException;

}
