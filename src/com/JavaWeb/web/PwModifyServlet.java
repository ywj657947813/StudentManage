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
		String password=request.getParameter("password1");       //����ľ�����
		String newpassword=request.getParameter("newPassword"); //������
		String newpassword1=request.getParameter("newPassword1");//ȷ������
		HttpSession session=request.getSession();
		String userId=(String)session.getAttribute("userName");//ѧ��ѧ��
		String pw=(String)session.getAttribute("password");    //�û���ʵ�ľ�����
		
		if(StringUtil.isEmpty(password)||StringUtil.isEmpty(newpassword)||StringUtil.isEmpty(newpassword1)){
			JSONObject result=new JSONObject();
			result.put("success", "true");
			result.put("errorMsg", "�ף���Ϣ�������пյ�Ŷ��");
			try {
				ResponseUtil.write(response, result);
				return;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(password.equals(pw)){//�жϾ������Ƿ���ȷ
			if(password.equals(newpassword)){
				//�ж��Ƿ��������µ�����
				JSONObject result=new JSONObject();
				result.put("success", "true");
				result.put("errorMsg", "���������µ����룡");
				try {
					ResponseUtil.write(response, result);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else {
			if(newpassword.equals(newpassword1)){
				//��������һ��
				Connection con=null;
				try{
					con=dbUtil.getCon();
					JSONObject result=new JSONObject();
					//�޸����룬�����޸ĵļ�¼�������϶�Ϊ1
					int count=userDao.pwModify(con, userId, newpassword);
					if(count>0){
						result.put("success", "true");
						result.put("successMsg", "�޸ĳɹ���");
						session.setAttribute("password", newpassword);//�����û�����
					}else{
						result.put("success", "true");
						result.put("errorMsg", "�޸�ʧ�ܣ�");
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
				result.put("errorMsg", "��������������벻һ�£����������룡");
				try {
					ResponseUtil.write(response, result);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				   //request.setAttribute("fail", "������������벻һ�£�");
				   //request.getRequestDispatcher("modifyPas.jsp").forward(request, response);
		         }
			}
		
	    }else {
	    	JSONObject result=new JSONObject();
	    	result.put("success", "true");
			result.put("errorMsg", "������ľ�����������������룡");
			try {
				ResponseUtil.write(response, result);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	     
	    }
	
	    
	}
}
