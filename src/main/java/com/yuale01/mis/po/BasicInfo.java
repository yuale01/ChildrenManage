package com.yuale01.mis.po;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BasicInfo 
{
	private Long id;
	private String name;
	private String grade;
	private String className;
	private Integer gender;
	private String nation;
	private String birthday;
	private String idCardNo;
	private Integer huKou;
	private String huKouAddr;
	private Boolean migration;
	private Boolean onlyChild;
	private Boolean minLiving;
	private Boolean imburse;
	private Boolean orphan;
	private Boolean pathography;
	private String specialPerformance;
	private String otherAnnouncement;
	private Long timeStamp;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getIdCardNo() {
		return idCardNo;
	}
	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}
	public Integer getHuKou() {
		return huKou;
	}
	public void setHuKou(Integer huKou) {
		this.huKou = huKou;
	}
	public String getHuKouAddr() {
		return huKouAddr;
	}
	public Boolean getMigration() {
		return migration;
	}
	public void setMigration(Boolean migration) {
		this.migration = migration;
	}
	public Boolean getOnlyChild() {
		return onlyChild;
	}
	public void setOnlyChild(Boolean onlyChild) {
		this.onlyChild = onlyChild;
	}
	public Boolean getMinLiving() {
		return minLiving;
	}
	public void setMinLiving(Boolean minLiving) {
		this.minLiving = minLiving;
	}
	public Boolean getImburse() {
		return imburse;
	}
	public void setImburse(Boolean imburse) {
		this.imburse = imburse;
	}
	public Boolean getOrphan() {
		return orphan;
	}
	public void setOrphan(Boolean orphan) {
		this.orphan = orphan;
	}
	public Boolean getPathography() {
		return pathography;
	}
	public void setHuKouAddr(String huKouAddr) {
		this.huKouAddr = huKouAddr;
	}
	public void setPathography(Boolean pathography) {
		this.pathography = pathography;
	}
	public String getSpecialPerformance() {
		return specialPerformance;
	}
	public void setSpecialPerformance(String specialPerformance) {
		this.specialPerformance = specialPerformance;
	}
	public String getOtherAnnouncement() {
		return otherAnnouncement;
	}
	public void setOtherAnnouncement(String otherAnnouncement) {
		this.otherAnnouncement = otherAnnouncement;
	}
	public Long getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Long timeStamp) {
		this.timeStamp = timeStamp;
	}
	
}
