package com.JavaWeb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.JavaWeb.model.Student;
import com.JavaWeb.model.StuUser;

/**
 * �û�Dao��
 * @author Administrator
 *
 */
public class StuUserDao {

	/**
	 * ��¼��֤
	 * @param con
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public StuUser login(Connection con,StuUser user) throws Exception{
		StuUser resultUser=null;
		String sql="select * from stu_user where userName=? and password=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, user.getUserName());
		pstmt.setString(2, user.getPassword());
		ResultSet rs=pstmt.executeQuery();
		if(rs.next()){
			resultUser=new StuUser();
			resultUser.setUserName(rs.getString("userName"));
			resultUser.setPassword(rs.getString("password"));
		}
		return resultUser;
	}
	/**
	 * ����ѧ��id��student�����ѧ�Ŷ�Ӧ������
	 */
	public Student find (Connection con,String id) throws SQLException{
		
		Student resultStudent=null;
		//String stuName = null;
		String sql = "select * from t_student s,t_grade g,t_spec sp,t_depa d where s.gradeid=g.grId and" +
				     " s.specid=sp.spId and s.depaid=d.deId and stuNo=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, id);
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()){
			resultStudent=new Student();
			resultStudent.setStuNo(rs.getString("stuNo"));
			resultStudent.setStuName(rs.getString("stuName"));
			resultStudent.setBirthday(rs.getDate("birthday"));
			resultStudent.setEmail(rs.getString("email"));
			resultStudent.setSex(rs.getString("sex"));
			resultStudent.setInTime(rs.getString("inTime"));
			resultStudent.setStuDesc(rs.getString("stuDesc"));
			resultStudent.setGradeName(rs.getString("gradeName"));
			resultStudent.setDepaName(rs.getString("depaName"));
			resultStudent.setSpecName(rs.getString("specName"));
		//  stuName = rs.getString(1);//ֻ��ѯstuName������ֻ��һ��Ԫ��the first column is 1, the second is 2, 	
		//	stuName = rs.getString(2);
		}
		return resultStudent;
	}
	/**
	 *   ����Ա��ѯ 
	 */
	public Student findAdmin(Connection con,String id)throws Exception{
		Student resultStudent=null;
		//String stuName = null;
		String sql = "select * from t_student where stuNo=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, id);
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()){
			resultStudent=new Student();
			resultStudent.setStuName(rs.getString("stuName"));
		}
		return resultStudent;
	}
	public int pwModify(Connection con,String id,String pw)throws Exception{
		String sql="update stu_user set password=? where userName=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, pw);
		pstmt.setString(2, id);
		return pstmt.executeUpdate();
	}
	
}
