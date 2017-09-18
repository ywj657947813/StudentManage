package com.JavaWeb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.JavaWeb.model.Admin;
import com.JavaWeb.model.AdminUser;

/**
 * 用户Dao类
 * @author Administrator
 *
 */
public class AdminUserDao {

	/**
	 * 登录验证
	 * @param con
	 * @param adminUser
	 * @return
	 * @throws Exception
	 */
	public AdminUser login(Connection con,AdminUser adminUser) throws Exception{
		AdminUser resultUser=null;
		String sql="select * from admin_user where adminName=? and password=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, adminUser.getAdminName());
		pstmt.setString(2, adminUser.getPassword());
		ResultSet rs=pstmt.executeQuery();
		if(rs.next()){
			resultUser=new AdminUser();
			resultUser.setAdminName(rs.getString("adminName"));
			resultUser.setPassword(rs.getString("password"));
		}
		return resultUser;
	}
	/**
	 *   管理员信息查询
	 */
	public Admin find(Connection con,String id)throws Exception{
		Admin resultAdmin=null;
		//String stuName = null;
		String sql = "select * from t_admin where adminId=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, id);
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()){
			resultAdmin=new Admin();
			resultAdmin.setAdminId(rs.getString("adminId"));
			resultAdmin.setAdminName(rs.getString("adminName"));
		}
		return resultAdmin;
	}
	
	
}
