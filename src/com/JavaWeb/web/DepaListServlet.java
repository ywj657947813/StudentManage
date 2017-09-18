package com.JavaWeb.web;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.JavaWeb.dao.DepaDao;
import com.JavaWeb.model.Depa;
import com.JavaWeb.model.PageBean;
import com.JavaWeb.util.DbUtil;
import com.JavaWeb.util.JsonUtil;
import com.JavaWeb.util.ResponseUtil;



public class DepaListServlet extends HttpServlet{

	DbUtil dbUtil=new DbUtil();
	DepaDao  depaDao=new DepaDao();
	
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
		String depaName=request.getParameter("depaName");
		if(depaName==null){
			//���ʲô��û���룬depaName��ֵΪnull�������á�����depaName
			depaName="";
		}
		Depa depa=new Depa();
		depa.setDepaName(depaName);//���û��ѯ����������Ϊ���������ᱨ��ָ���쳣
		PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
		Connection con=null;
		try{
			con=dbUtil.getCon();
			JSONObject result=new JSONObject();
			JSONArray jsonArray=JsonUtil.formatRsToJsonArray(depaDao.depaList(con, pageBean,depa));
			int total=depaDao.depaCount(con,depa);//��ѯ����ϵ���ܼ�¼��
			result.put("rows", jsonArray);
			result.put("total", total);
			ResponseUtil.write(response, result);//��ҳ�������ѯ�������ݣ�ҳ��ɻ�ȡ
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
