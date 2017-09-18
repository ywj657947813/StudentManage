package com.JavaWeb.web;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.JavaWeb.dao.GradeDao;
import com.JavaWeb.dao.SpecDao;
import com.JavaWeb.model.Grade;
import com.JavaWeb.model.PageBean;
import com.JavaWeb.model.Spec;
import com.JavaWeb.util.DbUtil;
import com.JavaWeb.util.JsonUtil;
import com.JavaWeb.util.ResponseUtil;
import com.sun.org.apache.bcel.internal.generic.NEW;



public class SpecListServlet extends HttpServlet{

	DbUtil dbUtil=new DbUtil();
	SpecDao  specDao=new SpecDao();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String page=request.getParameter("page");
		String rows=request.getParameter("rows");
		String specName=request.getParameter("specName");
		String depaId=request.getParameter("depaId");
		if(specName==null){
			specName="";
		}
		if(depaId==null){
			depaId="";
		}
		Spec spec=new Spec();
		spec.setSpecName(specName);
		spec.setDepaId(depaId);
		PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
		Connection con=null;
		try{
			con=dbUtil.getCon();
			JSONObject result=new JSONObject();
			JSONArray jsonArray=JsonUtil.formatRsToJsonArray(specDao.specList(con, pageBean,spec));
			int total=specDao.specCount(con,spec);//查询到的专业总记录数
			result.put("rows", jsonArray);
			result.put("total", total);
			ResponseUtil.write(response, result);//向页面输出查询到的内容，页面可获取
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
