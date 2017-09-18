package com.JavaWeb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.JavaWeb.model.Depa;
import com.JavaWeb.model.Grade;
import com.JavaWeb.model.PageBean;
import com.JavaWeb.model.Spec;
import com.JavaWeb.model.Student;
import com.JavaWeb.util.DateUtil;
import com.JavaWeb.util.StringUtil;

public class StudentDao {
    //Connection con,PageBean pageBean,Student student,String bbirthday,String ebirthday
	public ResultSet studentList(Connection con,PageBean pageBean,Student student)throws Exception{
		StringBuffer sb=new StringBuffer("select * from t_student s,t_grade g,t_depa d,t_spec sp where s.gradeId=g.grId and s.depaId=d.deId and s.specId=sp.spId");
		if(StringUtil.isNotEmpty(student.getStuNo())){
			sb.append(" and s.stuNo like '%"+student.getStuNo()+"%'");
		}
		if(StringUtil.isNotEmpty(student.getStuName())){
			sb.append(" and s.stuName like '%"+student.getStuName()+"%'");
		}
		if(StringUtil.isNotEmpty(student.getSex())){
			sb.append(" and s.sex ='"+student.getSex()+"'");
		}
		if(StringUtil.isNotEmpty(student.getGradeId())){
			sb.append(" and s.gradeId ='"+student.getGradeId()+"'");
		}
		if(StringUtil.isNotEmpty(student.getSpecId())){
			sb.append(" and s.specId ='"+student.getSpecId()+"'");
		}
		if(StringUtil.isNotEmpty(student.getDepaId())){
			sb.append(" and s.depaId ='"+student.getDepaId()+"'");
		}
		/*if(StringUtil.isNotEmpty(bbirthday)){
			sb.append(" and TO_DAYS(s.birthday)>=TO_DAYS('"+bbirthday+"')");
		}
		if(StringUtil.isNotEmpty(ebirthday)){
			sb.append(" and TO_DAYS(s.birthday)<=TO_DAYS('"+ebirthday+"')");
		}*/
		if(pageBean!=null){
			sb.append(" limit "+pageBean.getStart()+","+pageBean.getRows());
		}
		PreparedStatement pstmt=con.prepareStatement(sb.toString());
		return pstmt.executeQuery();
	}
	//Connection con,Student student,String bbirthday,String ebirthday
	public int studentCount(Connection con,Student student)throws Exception{
		StringBuffer sb=new StringBuffer("select count(*) as total from t_student s,t_grade g,t_depa d,t_spec sp where s.gradeId=g.grId and s.depaId=d.deId and s.specId=sp.spId");
		if(StringUtil.isNotEmpty(student.getStuNo())){
			sb.append(" and s.stuNo like '%"+student.getStuNo()+"%'");
		}
		if(StringUtil.isNotEmpty(student.getStuName())){
			sb.append(" and s.stuName like '%"+student.getStuName()+"%'");
		}
		if(StringUtil.isNotEmpty(student.getSex())){
			sb.append(" and s.sex ='"+student.getSex()+"'");
		}
		if(StringUtil.isNotEmpty(student.getDepaId())){
			sb.append(" and s.depaId ='"+student.getDepaId()+"'");
		}
		if(StringUtil.isNotEmpty(student.getGradeId())){
			sb.append(" and s.specId ='"+student.getSpecId()+"'");
		}
		if(StringUtil.isNotEmpty(student.getGradeId())){
			sb.append(" and s.gradeId ='"+student.getGradeId()+"'");
		}
		/*if(StringUtil.isNotEmpty(bbirthday)){
			sb.append(" and TO_DAYS(s.birthday)>=TO_DAYS('"+bbirthday+"')");
		}
		if(StringUtil.isNotEmpty(ebirthday)){
			sb.append(" and TO_DAYS(s.birthday)<=TO_DAYS('"+ebirthday+"')");
		}*/
		PreparedStatement pstmt=con.prepareStatement(sb.toString());
		ResultSet rs=pstmt.executeQuery();
		if(rs.next()){
			return rs.getInt("total");
		}else{
			return 0;
		}
	}
	
	public int studentDelete(Connection con,String delIds)throws Exception{
		String sql="delete from t_student where stuId in("+delIds+")";
		PreparedStatement pstmt=con.prepareStatement(sql);
		return pstmt.executeUpdate();
	}
	
	public int studentAdd(Connection con,Student student)throws Exception{
		String sql="insert into t_student values(null,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, student.getStuNo());
		pstmt.setString(2, student.getStuName());
		pstmt.setString(3, student.getSex());
		pstmt.setString(4, DateUtil.formatDate(student.getBirthday(), "yyyy-MM-dd"));
		pstmt.setString(5, student.getGradeId());
		pstmt.setString(6, student.getEmail());
		pstmt.setString(7, student.getInTime());
		pstmt.setString(8, student.getDepaId());
		pstmt.setString(9, student.getSpecId());
		pstmt.setString(10, student.getStuDesc());
		return pstmt.executeUpdate();
	}
	/**
	 * 管理员修改--学生信息
	 *
	 */
	public int studentModify(Connection con,Student student)throws Exception{
		String sql="update t_student set stuNo=?,stuName=?,sex=?,birthday=?,gradeId=?,email=?,depaId=?,specId=?,inTime=?,stuDesc=? where stuId=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, student.getStuNo());
		pstmt.setString(2, student.getStuName());
		pstmt.setString(3, student.getSex());
		pstmt.setString(4, DateUtil.formatDate(student.getBirthday(), "yyyy-MM-dd"));
		pstmt.setString(5, student.getGradeId());
		pstmt.setString(6, student.getEmail());
		pstmt.setString(7, student.getDepaId());
		pstmt.setString(8, student.getSpecId());
		pstmt.setString(9, student.getInTime());
		pstmt.setString(10, student.getStuDesc());
		pstmt.setInt(11, student.getStuId());
		return pstmt.executeUpdate();
	}
	/**
	 * 学生修改--学生信息
	 * 
	 */
	public int stuModify(Connection con,Student student)throws Exception{
		String sql="update t_student set stuName=?,birthday=?,email=?,stuDesc=? where stuNo=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, student.getStuName());
		pstmt.setString(2, DateUtil.formatDate(student.getBirthday(), "yyyy-MM-dd"));
		pstmt.setString(3, student.getEmail());
		pstmt.setString(4, student.getInTime());
		pstmt.setString(5, student.getStuNo());
		return pstmt.executeUpdate();
	}
	/**
	 * 判断班级下是否有学生
	 * @param con
	 * @param gradeId
	 * @return
	 * @throws Exception
	 */
	public boolean getStudentByGradeId(Connection con,String gradeId)throws Exception{
		String sql="select * from t_student where gradeId=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, gradeId);
		ResultSet rs=pstmt.executeQuery();
		if(rs.next()){
			return true;
		}else{
			return false;
		}
	}
	/**
	 *查找学生对应的班级名称
	 */
    public Grade findGrade(Connection con,String id) throws Exception{
		
		Grade resultStudent=null;
		//String stuName = null;
		String sql = "select * from t_student s,t_grade g where s.gradeId=g.id and s.stuNo=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, id);
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()){
			resultStudent=new Grade();
			resultStudent.setGradeName(rs.getString("gradeName"));
		//  stuName = rs.getString(1);//只查询stuName，所以只有一个元素the first column is 1, the second is 2, 	
		//	stuName = rs.getString(2);
		}
		return resultStudent;
	}
    /**
	 *查找学生对应的专业名称
	 */
     public Spec findSpec(Connection con,String id) throws Exception{
 	
	Spec resultStudent=null;
	//String stuName = null;
	String sql = "select * from t_student s,t_spec sp where s.specId=sp.id and s.stuNo=?";
	PreparedStatement pstmt = con.prepareStatement(sql);
	pstmt.setString(1, id);
	ResultSet rs = pstmt.executeQuery();
	if(rs.next()){
		resultStudent=new Spec();
		resultStudent.setSpecName(rs.getString("specName"));
	//  stuName = rs.getString(1);//只查询stuName，所以只有一个元素the first column is 1, the second is 2, 	
	//	stuName = rs.getString(2);
	}
	return resultStudent;
}
     /**
 	 *查找学生对应的系别名称
 	 */
     public Depa findDepa(Connection con,String id) throws Exception{
	
	Depa resultStudent=null;
	//String stuName = null;
	String sql = "select * from t_student s,t_depa d where s.depaId=d.id and s.stuNo=?";
	PreparedStatement pstmt = con.prepareStatement(sql);
	pstmt.setString(1, id);
	ResultSet rs = pstmt.executeQuery();
	if(rs.next()){
		resultStudent=new Depa();
		resultStudent.setDepaName(rs.getString("depaName"));
	//  stuName = rs.getString(1);//只查询stuName，所以只有一个元素the first column is 1, the second is 2, 	
	//	stuName = rs.getString(2);
	}
	return resultStudent;
}
}
