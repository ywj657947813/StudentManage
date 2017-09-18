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
import com.JavaWeb.model.Spec;
import com.JavaWeb.util.DbUtil;
import com.JavaWeb.util.JsonUtil;
import com.JavaWeb.util.ResponseUtil;

public class SpecComboListServlet extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	DbUtil dbUtil=new DbUtil();
	GradeDao gradeDao=new GradeDao();
	SpecDao specDao=new SpecDao();
	Spec spec=new Spec();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String depaId=request.getParameter("depaId");
		if(depaId!=null){
			/**
			 * 实现系别，专业，班级三级联动
			 */
			spec.setDepaId(depaId);
			Connection con=null;
			try{
				con=dbUtil.getCon();
				JSONArray jsonArray=new JSONArray();
				JSONObject jsonObject=new JSONObject();
				jsonObject.put("spId", "");
				jsonObject.put("specName", "请选择...");
				jsonArray.add(jsonObject);
				//将查询到的所有班级添加进jsonArray
				jsonArray.addAll(JsonUtil.formatRsToJsonArray(specDao.specList(con, null,spec)));
				
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
		
		}else {
			
		
		Connection con=null;
		try{
			con=dbUtil.getCon();
			JSONArray jsonArray=new JSONArray();
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("spId", "");
			jsonObject.put("specName", "请选择...");
			jsonArray.add(jsonObject);
			//将查询到的所有班级添加进jsonArray
			jsonArray.addAll(JsonUtil.formatRsToJsonArray(specDao.specList(con, null,null)));
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
