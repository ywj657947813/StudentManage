package com.JavaWeb.web;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.JavaWeb.dao.AdminUserDao;
import com.JavaWeb.dao.DepaDao;
import com.JavaWeb.dao.GradeDao;
import com.JavaWeb.dao.SpecDao;
import com.JavaWeb.dao.StudentDao;
import com.JavaWeb.dao.StuUserDao;
import com.JavaWeb.model.Admin;
import com.JavaWeb.model.AdminUser;
import com.JavaWeb.model.Student;
import com.JavaWeb.model.StuUser;
import com.JavaWeb.util.DbUtil;
import com.JavaWeb.util.StringUtil;



/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DbUtil dbUtil=new DbUtil();
	StuUserDao stuUserDao=new StuUserDao();
	AdminUserDao adminUserDao=new AdminUserDao();
	StudentDao studentDao=new StudentDao();
 
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session=request.getSession();
		String userName=request.getParameter("userName");
		String password=request.getParameter("password");
		String peopletype=request.getParameter("buttonbox");
		session.setAttribute("userName", userName);//设置信息
		session.setAttribute("password", password);//设置信息 
		
		if(StringUtil.isEmpty(userName)||StringUtil.isEmpty(password)){
			request.setAttribute("error", "用户名或密码不可以为空！");//服务器端跳转，带有信息
			request.getRequestDispatcher("login.jsp").forward(request, response);
			return;
		}
		Connection con=null;
				
		try {
			con=dbUtil.getCon();
			if(peopletype.equals("manager")){
				AdminUser adminUser=new AdminUser(userName,password);
				AdminUser currentUser=adminUserDao.login(con, adminUser);//查询管理员是否存在，并且判断是否正确
				if(currentUser==null){
					request.setAttribute("error", "用户名或密码错误！");
					// 服务器跳转
					request.getRequestDispatcher("login.jsp").forward(request, response);
				}else{
				
					Admin name= adminUserDao.find(con, userName);//获取管理员的信息
					session.setAttribute("name", name);			//name为一个Admin对象  ，里面保存了这个管理员的信息					
					session.setAttribute("currentUser", currentUser);
	
					request.getRequestDispatcher("main.jsp").forward(request, response);
			
				}
			}
			else if(peopletype.equals("student")){
				
					StuUser stuUser=new StuUser(userName,password);//创建一个stuUser对象，以便验证
					StuUser currentUser=stuUserDao.login(con, stuUser);//查询学生是否存在，并且判断是否正确
					if(currentUser==null){
						request.setAttribute("error", "用户名或密码错误！");
						// 服务器跳转
						request.getRequestDispatcher("login.jsp").forward(request, response);
					}else{
				
					Student name = stuUserDao.find(con, userName);//获取学生对应的信息
					session.setAttribute("name", name);			//name为一个student对象  ，里面保存了这个学生的信息					
					session.setAttribute("currentUser", currentUser);
					
					
					//Grade gradeName=studentDao.findGrade(con, userName);
					//Spec specName=studentDao.findSpec(con, userName);
					//Depa depaName=studentDao.findDepa(con, userName);
					//request.setAttribute("gradeName", gradeName);
					//request.setAttribute("specName", specName);
					//request.setAttribute("depaName", depaName);
					
					request.getRequestDispatcher("student.jsp").forward(request, response);
				
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
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


