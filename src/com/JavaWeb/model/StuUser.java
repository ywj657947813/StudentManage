package com.JavaWeb.model;

/**
 * �û�ʵ��
 * @author Administrator
 *
 */
public class StuUser {

	private int id; // ���
	private String userName; // �û���
	private String password; // ����

	
	
	public StuUser() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public StuUser(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
