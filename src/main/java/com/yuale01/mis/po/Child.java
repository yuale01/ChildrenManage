package com.yuale01.mis.po;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Child {
    private BasicInfo   basicInfo;
    private ContactInfo contactInfo;
    private BodyInfo    bodyInfo;
    private Long        id;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
