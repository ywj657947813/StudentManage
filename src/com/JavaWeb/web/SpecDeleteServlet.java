package com.JavaWeb.web;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.JavaWeb.dao.GradeDao;
import com.JavaWeb.dao.SpecDao;
import com.JavaWeb.dao.StudentDao;
import com.JavaWeb.util.DbUtil;
import com.JavaWeb.util.ResponseUtil;
import com.sun.org.apache.bcel.internal.generic.NEW;

public class SpecDeleteServlet extends HttpServlet{
	DbUtil dbUtil=new DbUtil();
	GradeDao gradeDao=new GradeDao();
	SpecDao specDao=new SpecDao();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String delIds=request.getParameter("delIds");
		Connection con=null;
		try{
			con=dbUtil.getCon();
			JSONObject result=new JSONObject();
			String str[]=delIds.split(",");
			/**
			 * ɾ��רҵǰ�ж��Ƿ���ڰ༶
			 */
			for(int i=0;i<str.length;i++){
				boolean f=gradeDao.getGradeByspecId(con, str[i]);
				if(f){
					result.put("errorIndex", i);
					result.put("errorMsg", "רҵ�����а༶������ɾ����");
					ResponseUtil.write(response, result);
					return;
				}
			}
			int delNums=specDao.specDelete(con, delIds);
			if(delNums>0){
				result.put("success", "true");//�ɹ�
				result.put("delNums", delNums);
			}else{
				result.put("errorMsg", "ɾ��ʧ��");//ʧ��
			}
			ResponseUtil.write(response, result);//��ҳ���������
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
