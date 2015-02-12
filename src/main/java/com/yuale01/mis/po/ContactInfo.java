package com.yuale01.mis.po;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ContactInfo {
    private Long   id;
    private String motherName;
    private String motherCompany;
    private String motherContact;
    private String motherIdCard;
    private String fatherName;
    private String fatherCompany;
    private String fatherContact;
    private String fatherIdCard;
    private String livingAddr;
    private String otherContact;
    private Long   timeStamp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getMotherCompany() {
        return motherCompany;
    }

    public void setMotherCompany(String motherCompany) {
        this.motherCompany = motherCompany;
    }

    public String getMotherContact() {
        return motherContact;
    }

    public void setMotherContact(String motherContact) {
        this.motherContact = motherContact;
    }

    public String getMotherIdCard() {
        return motherIdCard;
    }

    public void setMotherIdCard(String motherIdCard) {
        this.motherIdCard = motherIdCard;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getFatherCompany() {
        return fatherCompany;
    }

    public void setFatherCompany(String fatherCompany) {
        this.fatherCompany = fatherCompany;
    }

    public String getFatherContact() {
        return fatherContact;
    }

    public void setFatherContact(String fatherContact) {
        this.fatherContact = fatherContact;
    }

    public String getFatherIdCard() {
        return fatherIdCard;
    }

    public void setFatherIdCard(String fatherIdCard) {
        this.fatherIdCard = fatherIdCard;
    }

    public String getLivingAddr() {
        return livingAddr;
    }

    public void setLivingAddr(String livingAddr) {
        this.livingAddr = livingAddr;
    }

    public String getOtherContact() {
        return otherContact;
    }

    public void setOtherContact(String otherContact) {
        this.otherContact = otherContact;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

}
