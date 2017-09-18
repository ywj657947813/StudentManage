package com.JavaWeb.web;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sun.nio.cs.ext.ISCII91;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.JavaWeb.dao.GradeDao;
import com.JavaWeb.model.Grade;
import com.JavaWeb.util.DbUtil;
import com.JavaWeb.util.JsonUtil;
import com.JavaWeb.util.ResponseUtil;

public class GradeComboListServlet extends HttpServlet{
	DbUtil dbUtil=new DbUtil();
	GradeDao gradeDao=new GradeDao();
	Grade grade=new Grade();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String specId=request.getParameter("specId");
		if(specId!=null){
			/**
			 * 三级联动
			 */
			grade.setSpecId(specId);
			Connection con=null;
			try{
				con=dbUtil.getCon();
				JSONArray jsonArray=new JSONArray();
				JSONObject jsonObject=new JSONObject();
				jsonObject.put("grId", "");
				jsonObject.put("gradeName", "请选择...");
				jsonArray.add(jsonObject);
				//将查询到的所有班级添加进jsonArray
				jsonArray.addAll(JsonUtil.formatRsToJsonArray(gradeDao.gradeList(con, null,grade)));
				ResponseUtil.write(response, jsonArray);
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
		Connection con=null;
		try{
			con=dbUtil.getCon();
			JSONArray jsonArray=new JSONArray();
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("grId", "");
			jsonObject.put("gradeName", "请选择...");
			jsonArray.add(jsonObject);
			//将查询到的所有班级添加进jsonArray
			jsonArray.addAll(JsonUtil.formatRsToJsonArray(gradeDao.gradeList(con, null,null)));
			ResponseUtil.write(response, jsonArray);
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
	
}
