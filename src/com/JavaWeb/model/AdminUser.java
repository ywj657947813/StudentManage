package com.JavaWeb.model;

/**
 * 用户实体
 * @author Administrator
 *
 */
public class AdminUser {

	private int id; // 编号
	private String adminName; // 用户名
	private String password; // 密码
	
	
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
