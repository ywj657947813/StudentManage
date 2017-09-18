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
		//修改，增加班级用到的变量
		String gradeName=request.getParameter("gradeName");//班级名字
		String specId=request.getParameter("specId"); //所属专业的id
		String depaId=request.getParameter("depaId"); //所属系别的id
		String id=request.getParameter("gId");//修改传来的id
		String grId=request.getParameter("grId");//班级编号
		
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
				result.put("success", "true");//没有success，前台没法接收到success信息，就接受不到result
				result.put("successMsg", "true");
			}else{
				result.put("success", "true");
				result.put("errorMsg", "保存失败");
			}
			ResponseUtil.write(response, result);
		}catch(Exception e){
			//只能新建一个JSONObject对象   上面的result不可用
			e.printStackTrace();
			JSONObject result1=new JSONObject();
			result1.put("success", "true");
			result1.put("errorMsg", "班级编号已存在，请另输入！");
			try {
				ResponseUtil.write(response, result1);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		  
			//这里因为 输入的班级编号  已经存在   班级编号为学生的外键，有唯一性，查看swing如何弹出异常信息，建议班级编号为  专业编号开头 如  0401  0201
			//e.printStackTrace();
			//此报错表示有外键异常，如果在catch设置错误信息的话，这报错要省略，   不知道是客户端不会运行？还是没接收到result，反正客户端没反应
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
