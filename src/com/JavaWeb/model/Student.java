package com.JavaWeb.model;

import java.util.Date;

public class Student {
	
	private int stuId;
	private String stuNo;
	private String stuName;
	private String sex;
	private String email;
	private Date birthday;
	private String inTime;
	private String gradeName;
	private String gradeId;
	private String depaId;
	private String specId;
	private String stuDesc;
	
	private String depaName;
	private String specName;
	
	public String getDepaName() {
		return depaName;
	}


	public void setDepaName(String depaName) {
		this.depaName = depaName;
	}


	public String getSpecName() {
		return specName;
	}


	public void setSpecName(String specName) {
		this.specName = specName;
	}


	public String getStuDesc() {
		return stuDesc;
	}


	public void setStuDesc(String stuDesc) {
		this.stuDesc = stuDesc;
	}


	public String getInTime() {
		return inTime;
	}
	
	
	public void setInTime(String inTime) {
		this.inTime = inTime;
	}

	public String getDepaId() {
		return depaId;
	}


	public void setDepaId(String depaId) {
		this.depaId = depaId;
	}


	public String getSpecId() {
		return specId;
	}


	public void setSpecId(String specId) {
		this.specId = specId;
	}


	public Student() {
		super();
	}
	
	public Student(String stuNo, String stuName,String sex,String email, Date birthday,
			String stuDesc) {
		super();
		this.stuNo = stuNo;
		this.stuName = stuName;
		this.sex = sex;
		this.email = email;
		this.birthday = birthday;
		this.stuDesc = stuDesc;
	}
	public Student(String stuNo, String stuName, String sex, Date birthday,
			String gradeId, String email, String inTime,String depaId,String specId,String stuDesc) {
		super();
		this.stuNo = stuNo;
		this.stuName = stuName;
		this.sex = sex;
		this.birthday = birthday;
		this.gradeId = gradeId;
		this.email = email;
		this.inTime = inTime;
		this.depaId = depaId;
		this.specId = specId;
		this.stuDesc= stuDesc;
	}


	public int getStuId() {
		return stuId;
	}
	public void setStuId(int stuId) {
		this.stuId = stuId;
	}
	public String getStuNo() {
		return stuNo;
	}
	public void setStuNo(String stuNo) {
		this.stuNo = stuNo;
	}
	public String getStuName() {
		return stuName;
	}
	public void setStuName(String stuName) {
		this.stuName = stuName;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getGradeId() {
		return gradeId;
	}
	public void setGradeId(String gradeId) {
		this.gradeId = gradeId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getGradeName() {
		return gradeName;
	}
	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}


	@Override
	public String toString() {
		return "Student [stuId=" + stuId + ", stuNo=" + stuNo + ", stuName="
				+ stuName + ", sex=" + sex + ", email=" + email + ", birthday="
				+ birthday + ", inTime=" + inTime + ", gradeName=" + gradeName
				+ ", gradeId=" + gradeId + ", depaId=" + depaId + ", specId="
				+ specId + ", stuDesc=" + stuDesc + ", depaName=" + depaName
				+ ", specName=" + specName + "]";
	}
	
	
	
	

}
