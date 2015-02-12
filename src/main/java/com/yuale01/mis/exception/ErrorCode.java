package com.yuale01.mis.exception;

public class ErrorCode {
    public static final int unknow_internal_error         = 1000;

    public static final int error_children_delete         = 2001;
    public static final int error_children_get            = 2002;
    public static final int error_children_delete_idsnull = 2003;

    public static final int error_child_create            = 2010;
    public static final int error_child_create_badinput   = 2011;
    public static final int error_child_update            = 2012;
    public static final int error_child_get               = 2013;
    public static final int error_child_update_badinput   = 2014;
    public static final int error_child_common_invalidid  = 2015;
    public static final int error_child_update_conflict   = 2016;
    public static final int error_child_delete            = 2017;
    public static final int error_child_delete_idnull     = 2018;

}
