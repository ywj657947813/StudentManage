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
import com.JavaWeb.dao.SpecDao;
import com.JavaWeb.model.Depa;
import com.JavaWeb.model.Grade;
import com.JavaWeb.model.Spec;
import com.JavaWeb.util.DbUtil;
import com.JavaWeb.util.ResponseUtil;
import com.JavaWeb.util.StringUtil;
import com.sun.org.apache.bcel.internal.generic.NEW;

public class SpecSaveServlet extends HttpServlet{
	DbUtil dbUtil=new DbUtil();
	SpecDao specDao=new SpecDao();  
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		//�޸ģ�����רҵ�õ��ı���
		String specName=request.getParameter("specName");//רҵ����
		String depaId=request.getParameter("depaId");//����ϵ����
		
		String spId=request.getParameter("spId");//רҵ���
		String sId=request.getParameter("sId");//���idΪ  �޸İ�ť�������ĵ�id����רҵ���
		Spec spec=new Spec(spId,specName,depaId);
		 //    spec.setDepaId(depaId);       //��������ϵ����
		if(StringUtil.isNotEmpty(sId)){
			spec.setsId(sId);
		}
		Connection con=null;
		try{
			con=dbUtil.getCon();
			int saveNums=0;
			JSONObject result=new JSONObject();
			if(StringUtil.isNotEmpty(sId)){
				/**
				 * ���רҵ��Ŵ��ڣ����޸����ݣ�
				 * ������������רҵ
				 */
				saveNums=specDao.specModify(con, spec);
			}else{
				saveNums=specDao.specAdd(con, spec);
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
			//ֻ���½�һ��JSONObject����   �����result������
			//e.printStackTrace();
			JSONObject result1=new JSONObject();
			result1.put("success", "true");
			result1.put("errorMsg", "רҵ����Ѿ����ڣ��������룡");
			try {
				ResponseUtil.write(response, result1);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//�˱����ʾ������쳣�������catch���ô�����Ϣ�Ļ����ⱨ��Ҫʡ�ԣ�   ��֪���ǿͻ��˲������У�����û���յ�result�������ͻ���û��Ӧ
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
