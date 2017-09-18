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
		String stuNo=request.getParameter("stuNo");         //ѧ��
		String stuName=request.getParameter("stuName");     //����
		String sex=request.getParameter("sex");             //�Ա� 
		String birthday=request.getParameter("birthday");   //��������
		String gradeId=request.getParameter("gradeId");     //�༶id
		String depaId=request.getParameter("depaId");       //ϵ��id
		String specId=request.getParameter("specId");       //רҵid
		String inTime=request.getParameter("inTime");       //��ѧʱ��
		String email=request.getParameter("email");         //email
		String stuDesc=request.getParameter("stuDesc");     //ѧ������
		
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
				result.put("errorMsg", "����ʧ��");
			}
			ResponseUtil.write(response, result);
		}catch(Exception e){
			JSONObject result1=new JSONObject();
			result1.put("success", "true");
			result1.put("errorMsg", "��ѧ���Ѵ��ڣ��������룡");
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
