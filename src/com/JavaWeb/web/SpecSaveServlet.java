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
		//修改，增加专业用到的变量
		String specName=request.getParameter("specName");//专业名字
		String depaId=request.getParameter("depaId");//所属系别编号
		
		String spId=request.getParameter("spId");//专业编号
		String sId=request.getParameter("sId");//这个id为  修改按钮传过来的的id，即专业编号
		Spec spec=new Spec(spId,specName,depaId);
		 //    spec.setDepaId(depaId);       //设置所属系别编号
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
				 * 如果专业编号存在，则修改数据；
				 * 不存在则增加专业
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
				result.put("errorMsg", "保存失败");
			}
			ResponseUtil.write(response, result);
		}catch(Exception e){
			//只能新建一个JSONObject对象   上面的result不可用
			//e.printStackTrace();
			JSONObject result1=new JSONObject();
			result1.put("success", "true");
			result1.put("errorMsg", "专业编号已经存在，请另输入！");
			try {
				ResponseUtil.write(response, result1);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
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
