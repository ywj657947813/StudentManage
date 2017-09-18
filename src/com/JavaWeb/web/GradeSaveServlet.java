package com.JavaWeb.web;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

import net.sf.json.JSONObject;

import com.JavaWeb.dao.GradeDao;
import com.JavaWeb.model.Grade;
import com.JavaWeb.util.DbUtil;
import com.JavaWeb.util.ResponseUtil;
import com.JavaWeb.util.StringUtil;

public class GradeSaveServlet extends HttpServlet{
	DbUtil dbUtil=new DbUtil();
	GradeDao gradeDao=new GradeDao();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		//�޸ģ����Ӱ༶�õ��ı���
		String gradeName=request.getParameter("gradeName");//�༶����
		String specId=request.getParameter("specId"); //����רҵ��id
		String depaId=request.getParameter("depaId"); //����ϵ���id
		String id=request.getParameter("gId");//�޸Ĵ�����id
		String grId=request.getParameter("grId");//�༶���
		
		Grade grade=new Grade(grId,specId,gradeName,depaId);
		
		if(StringUtil.isNotEmpty(id)){
			grade.setgId(id);
			
		}
		Connection con=null;
		try{
			con=dbUtil.getCon();
			int saveNums=0;
			JSONObject result=new JSONObject();
			if(StringUtil.isNotEmpty(id)){
				saveNums=gradeDao.gradeModify(con, grade);
			}else{
				saveNums=gradeDao.gradeAdd(con, grade);
			}
			if(saveNums>0){
				result.put("success", "true");//û��success��ǰ̨û�����յ�success��Ϣ���ͽ��ܲ���result
				result.put("successMsg", "true");
			}else{
				result.put("success", "true");
				result.put("errorMsg", "����ʧ��");
			}
			ResponseUtil.write(response, result);
		}catch(Exception e){
			//ֻ���½�һ��JSONObject����   �����result������
			e.printStackTrace();
			JSONObject result1=new JSONObject();
			result1.put("success", "true");
			result1.put("errorMsg", "�༶����Ѵ��ڣ��������룡");
			try {
				ResponseUtil.write(response, result1);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		  
			//������Ϊ ����İ༶���  �Ѿ�����   �༶���Ϊѧ�����������Ψһ�ԣ��鿴swing��ε����쳣��Ϣ������༶���Ϊ  רҵ��ſ�ͷ ��  0401  0201
			//e.printStackTrace();
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
