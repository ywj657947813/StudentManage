package com.JavaWeb.web;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.JavaWeb.dao.StudentDao;
import com.JavaWeb.model.Student;
import com.JavaWeb.util.DateUtil;
import com.JavaWeb.util.DbUtil;
import com.JavaWeb.util.ResponseUtil;
import com.JavaWeb.util.StringUtil;

public class StudentSaveServlet extends HttpServlet{
	DbUtil dbUtil=new DbUtil();
	StudentDao studentDao=new StudentDao();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String stuNo=request.getParameter("stuNo");         //学号
		String stuName=request.getParameter("stuName");     //姓名
		String sex=request.getParameter("sex");             //性别 
		String birthday=request.getParameter("birthday");   //出生日期
		String gradeId=request.getParameter("gradeId");     //班级id
		String depaId=request.getParameter("depaId");       //系别id
		String specId=request.getParameter("specId");       //专业id
		String inTime=request.getParameter("inTime");       //入学时间
		String email=request.getParameter("email");         //email
		String stuDesc=request.getParameter("stuDesc");     //学生简述
		
		String stuId=request.getParameter("stuId");
		
		Student student=null;
		try {
			student = new Student(stuNo, stuName, sex,DateUtil.formatString(birthday, "yyyy-MM-dd"),gradeId, email, inTime,depaId,specId,stuDesc);
		}  catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(StringUtil.isNotEmpty(stuId)){
			student.setStuId(Integer.parseInt(stuId));
		}
		Connection con=null;
		try{
			con=dbUtil.getCon();
			int saveNums=0;
			JSONObject result=new JSONObject();
			if(StringUtil.isNotEmpty(stuId)){
				saveNums=studentDao.studentModify(con, student);
			}else{
				saveNums=studentDao.studentAdd(con, student);
			}
			if(saveNums>0){
				result.put("success", "true");
				result.put("successMsg", "true");
			}else{
				result.put("success", "true");
				result.put("errorMsg", "保存失败");
			}
			ResponseUtil.write(response, result);
		}catch(Exception e){
			JSONObject result1=new JSONObject();
			result1.put("success", "true");
			result1.put("errorMsg", "该学号已存在，请另输入！");
			try {
				ResponseUtil.write(response, result1);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//e.printStackTrace();
		}finally{
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
	
}
