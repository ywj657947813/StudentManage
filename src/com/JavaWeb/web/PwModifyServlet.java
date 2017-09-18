package com.JavaWeb.web;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;
import com.JavaWeb.dao.StuUserDao;
import com.JavaWeb.util.DbUtil;
import com.JavaWeb.util.ResponseUtil;
import com.JavaWeb.util.StringUtil;

public class PwModifyServlet extends HttpServlet{
	DbUtil dbUtil=new DbUtil();
	StuUserDao userDao=new StuUserDao();
  	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String password=request.getParameter("password1");       //输入的旧密码
		String newpassword=request.getParameter("newPassword"); //新密码
		String newpassword1=request.getParameter("newPassword1");//确认密码
		HttpSession session=request.getSession();
		String userId=(String)session.getAttribute("userName");//学生学号
		String pw=(String)session.getAttribute("password");    //用户真实的旧密码
		
		if(StringUtil.isEmpty(password)||StringUtil.isEmpty(newpassword)||StringUtil.isEmpty(newpassword1)){
			JSONObject result=new JSONObject();
			result.put("success", "true");
			result.put("errorMsg", "亲，信息不可以有空的哦！");
			try {
				ResponseUtil.write(response, result);
				return;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(password.equals(pw)){//判断旧密码是否正确
			if(password.equals(newpassword)){
				//判断是否输入了新的密码
				JSONObject result=new JSONObject();
				result.put("success", "true");
				result.put("errorMsg", "请您输入新的密码！");
				try {
					ResponseUtil.write(response, result);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else {
			if(newpassword.equals(newpassword1)){
				//两次密码一致
				Connection con=null;
				try{
					con=dbUtil.getCon();
					JSONObject result=new JSONObject();
					//修改密码，返回修改的记录条数，肯定为1
					int count=userDao.pwModify(con, userId, newpassword);
					if(count>0){
						result.put("success", "true");
						result.put("successMsg", "修改成功！");
						session.setAttribute("password", newpassword);//更新用户密码
					}else{
						result.put("success", "true");
						result.put("errorMsg", "修改失败！");
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
			}else{
				JSONObject result=new JSONObject();

				result.put("success", "true");
				result.put("errorMsg", "您两次输入的密码不一致，请重新输入！");
				try {
					ResponseUtil.write(response, result);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				   //request.setAttribute("fail", "两次输入的密码不一致！");
				   //request.getRequestDispatcher("modifyPas.jsp").forward(request, response);
		         }
			}
		
	    }else {
	    	JSONObject result=new JSONObject();
	    	result.put("success", "true");
			result.put("errorMsg", "您输入的旧密码错误，请重新输入！");
			try {
				ResponseUtil.write(response, result);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	     
	    }
	
	    
	}
}
