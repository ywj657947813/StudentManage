package com.JavaWeb.web;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.JavaWeb.dao.DepaDao;
import com.JavaWeb.dao.GradeDao;
import com.JavaWeb.model.Depa;
import com.JavaWeb.model.Grade;
import com.JavaWeb.util.DbUtil;
import com.JavaWeb.util.ResponseUtil;
import com.JavaWeb.util.StringUtil;
import com.sun.org.apache.bcel.internal.generic.NEW;

public class DepaSaveServlet extends HttpServlet{
	DbUtil dbUtil=new DbUtil();
	DepaDao depaDao=new DepaDao();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		//修改，增加系别用到的变量
		String depaName=request.getParameter("depaName");
		String deId=request.getParameter("deId");
		//String id1=request.getParameter("deId");//此id为  添加按钮传过来的id
		String id=request.getParameter("dId");//这个id为修改按钮传过来的的id，即系别编号
		Depa depa=new Depa(depaName,deId);
		
		if(StringUtil.isNotEmpty(id)){
			
			depa.setdId(id);
		}
		Connection con=null;
		try{
			con=dbUtil.getCon();
			int saveNums=0;
			JSONObject result=new JSONObject();
			if(StringUtil.isNotEmpty(id)){
				/**
				 * 如果系别编号存在，则修改数据；
				 * 不存在则增加系别
				 */
				saveNums=depaDao.depaModify(con, depa);
			}else{
				saveNums=depaDao.depaAdd(con, depa);
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
			//e.printStackTrace();
			JSONObject result=new JSONObject();
			result.put("success", "true");
			result.put("errorMsg", "该系别编号已存在，请另输入！");
			try {
				ResponseUtil.write(response, result);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
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
