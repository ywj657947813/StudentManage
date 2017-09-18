package com.JavaWeb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.JavaWeb.model.Grade;
import com.JavaWeb.model.PageBean;
import com.JavaWeb.util.StringUtil;

public class GradeDao {

	public ResultSet gradeList(Connection con,PageBean pageBean,Grade grade)throws Exception{
		StringBuffer sb=new StringBuffer("select * from t_grade g,t_spec s,t_depa d where g.specId=s.spId and g.depaId=d.deId");
		if(grade!=null && StringUtil.isNotEmpty(grade.getGradeName())){
			sb.append(" and gradeName like '%"+grade.getGradeName()+"%'");
		}
		if(grade!=null && StringUtil.isNotEmpty(grade.getDepaId())){
			sb.append(" and g.depaId like '%"+grade.getDepaId()+"%'");
		}
		if(grade!=null && StringUtil.isNotEmpty(grade.getSpecId())){
			sb.append(" and specId like '%"+grade.getSpecId()+"%'");
		}
		if(pageBean!=null){
			sb.append(" limit "+pageBean.getStart()+","+pageBean.getRows());
		}
		PreparedStatement pstmt=con.prepareStatement(sb.toString());
		return pstmt.executeQuery();
	}
	
	public int gradeCount(Connection con,Grade grade)throws Exception{
		StringBuffer sb=new StringBuffer("select count(*) as total from t_grade");
		if(StringUtil.isNotEmpty(grade.getGradeName())){
			sb.append(" and gradeName like '%"+grade.getGradeName()+"%'");
		}
		PreparedStatement pstmt=con.prepareStatement(sb.toString().replaceFirst("and", "where"));
		ResultSet rs=pstmt.executeQuery();
		if(rs.next()){
			return rs.getInt("total");//获取查询到的班级总记录数
		}else{
			return 0;
		}
	}
	
	/**
	 * delete from tableName where field in (1,3,5)
	 * @param con
	 * @param delIds
	 * @return
	 * @throws Exception
	 */
	public int gradeDelete(Connection con,String delIds)throws Exception{
		String sql="delete from t_grade where grId in("+delIds+")";
		PreparedStatement pstmt=con.prepareStatement(sql);
		return pstmt.executeUpdate();
	}
	
	public int gradeAdd(Connection con,Grade grade)throws Exception{
		String sql="insert into t_grade values(null,?,?,?,?)";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, grade.getGrId());
		pstmt.setString(2, grade.getGradeName());
		pstmt.setString(3, grade.getSpecId());
		pstmt.setString(4, grade.getDepaId());
		return pstmt.executeUpdate();
	}
	
	public int gradeModify(Connection con,Grade grade)throws Exception{
		String sql="update t_grade set grId=?,gradeName=?,specId=?,depaId=? where gId=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, grade.getGrId());
		pstmt.setString(2, grade.getGradeName());
		pstmt.setString(3, grade.getSpecId());
		pstmt.setString(4, grade.getDepaId());
		pstmt.setString(5, grade.getgId());
		return pstmt.executeUpdate();
	}
	/**
	 * 判断系别下是否有班级
	 * 
	 */
	public boolean getGradeByspecId(Connection con,String specId)throws Exception{
		String sql="select * from t_grade where specId=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, specId);
		ResultSet rs=pstmt.executeQuery();
		if(rs.next()){
			return true;
		}else{
			return false;
		}
	}
	
	
}
