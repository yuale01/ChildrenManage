package com.yuale01.mis.po;

public class Child 
{
	private BasicInfo basicInfo;
	private ContactInfo contactInfo;
	private BodyInfo bodyInfo;
	private long id;
	
	public BasicInfo getBasicInfo() {
		return basicInfo;
	}
	public void setBasicInfo(BasicInfo basicInfo) {
		this.basicInfo = basicInfo;
	}
	public BodyInfo getBodyInfo() {
		return bodyInfo;
	}
	public void setBodyInfo(BodyInfo bodyInfo) {
		this.bodyInfo = bodyInfo;
	}
	public ContactInfo getContactInfo() {
		return contactInfo;
	}
	public void setContactInfo(ContactInfo contactInfo) {
		this.contactInfo = contactInfo;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	

}
