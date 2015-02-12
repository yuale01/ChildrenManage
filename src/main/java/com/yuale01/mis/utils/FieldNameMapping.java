package com.yuale01.mis.utils;

public class FieldNameMapping {
	public String dbField;
	public int cellIndex;
	public String cellI18NKey;
	
	FieldNameMapping (String dbField, int cellIndex, String cellI18NKey) {
		this.dbField = dbField;
		this.cellIndex = cellIndex;
		this.cellI18NKey = cellI18NKey;
	}

}
