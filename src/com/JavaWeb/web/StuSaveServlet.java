package com.JavaWeb.web;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import com.JavaWeb.dao.StudentDao;
import com.JavaWeb.model.Student;
import com.JavaWeb.util.DateUtil;
import com.JavaWeb.util.DbUtil;
import com.JavaWeb.util.ResponseUtil;
import com.JavaWeb.util.StringUtil;

public class StuSaveServlet extends HttpServlet{
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
		String stuNo=request.getParameter("stuNo");
		String stuName=request.getParameter("stuName");
		String birthday=request.getParameter("birthday");
		String email=request.getParameter("email");
		String stuDesc=request.getParameter("stuDesc");
		String sex=request.getParameter("sex");
		if(StringUtil.isEmpty(email)){
			email="";
		}
		if(StringUtil.isEmpty(stuDesc)){
			stuDesc="";
		}
		
		Student student=null;
		try {
			student = new Student(stuNo, stuName,sex,email, DateUtil.formatString(birthday, "yyyy-MM-dd"), stuDesc);
		}  catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Connection con=null;
		try{
			con=dbUtil.getCon();
			JSONObject result=new JSONObject();
			int saveNums=0;			
				saveNums=studentDao.stuModify(con, student);
				if(saveNums>0){
					result.put("success", "true");
					result.put("successMsg", "true");
					
				}else{
					result.put("success", "true");
					result.put("errorMsg", "±£¥Ê ß∞‹£°");
				}
			ResponseUtil.write(response, result);
		}catch(Exception e){
			e.printStackTrace();
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
