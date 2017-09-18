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
		//�޸ģ�����ϵ���õ��ı���
		String depaName=request.getParameter("depaName");
		String deId=request.getParameter("deId");
		//String id1=request.getParameter("deId");//��idΪ  ��Ӱ�ť��������id
		String id=request.getParameter("dId");//���idΪ�޸İ�ť�������ĵ�id����ϵ����
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
				 * ���ϵ���Ŵ��ڣ����޸����ݣ�
				 * ������������ϵ��
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
				result.put("errorMsg", "����ʧ��");
			}
			ResponseUtil.write(response, result);
		}catch(Exception e){
			//e.printStackTrace();
			JSONObject result=new JSONObject();
			result.put("success", "true");
			result.put("errorMsg", "��ϵ�����Ѵ��ڣ��������룡");
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
