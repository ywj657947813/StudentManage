package com.JavaWeb.model;

/**
 * �û�ʵ��
 * @author Administrator
 *
 */
public class AdminUser {

	private int id; // ���
	private String adminName; // �û���
	private String password; // ����
	
	
	public AdminUser() {
		super();
		// TODO Auto-generated constructor stub
	}


	public AdminUser(String adminName, String password) {
		super();
		this.adminName = adminName;
		this.password = password;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getAdminName() {
		return adminName;
	}


	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
}
