package com.JavaWeb.web;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.JavaWeb.dao.DepaDao;
import com.JavaWeb.dao.SpecDao;
import com.JavaWeb.util.DbUtil;
import com.JavaWeb.util.ResponseUtil;

public class DepaDeleteServlet extends HttpServlet{
	DbUtil dbUtil=new DbUtil();
	SpecDao specDao=new SpecDao();
	DepaDao depaDao=new DepaDao();
 	
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
			 * 可利用外键关联抛出异常来提示系别下有专业；下面的for循环为再写一个判断方法；
			 */
			for(int i=0;i<str.length;i++){
				boolean f=specDao.getSpecByDepaId(con, str[i]);
				if(f){
					result.put("errorIndex", i);
					result.put("errorMsg", "系别下面有专业，不能删除！");
					ResponseUtil.write(response, result);
					return;
				}
			}
			int delNums=depaDao.depaDelete(con, delIds);//返回删除的数据条数
			if(delNums>0){
				result.put("success", "true");
				result.put("delNums", delNums);
			}else{
				result.put("errorMsg", "删除失败");
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
	}

	
	
}
