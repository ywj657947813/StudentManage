package com.JavaWeb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.JavaWeb.model.Depa;

import com.JavaWeb.model.PageBean;
import com.JavaWeb.util.StringUtil;

public class DepaDao {

	public ResultSet depaList(Connection con,PageBean pageBean,Depa depa)throws Exception{
		StringBuffer sb=new StringBuffer("select * from t_depa");
		if(depa!=null && StringUtil.isNotEmpty(depa.getDepaName())){
			//查询用到的条件，系别模糊查询
			sb.append(" and depaName like '%"+depa.getDepaName()+"%'");
		}
		/**
		 * sql分页
		 * select * from tablename limit start（2）,size（4）start是从0开始，
		 * 即取出第3条至第6条，4条记录
		 */
		if(pageBean!=null){
			sb.append(" limit "+pageBean.getStart()+","+pageBean.getRows());
		}
		PreparedStatement pstmt=con.prepareStatement(sb.toString().replaceFirst("and", "where"));
		return pstmt.executeQuery();
	}
	/**
	 * 返回数据库查询到的记录总数
	 *
	 */
	public int depaCount(Connection con,Depa depa)throws Exception{
		StringBuffer sb=new StringBuffer("select count(*) as total from t_depa");
		/**
		 * 系别模糊查询，返回查询到的记录条数
		 */
		if(StringUtil.isNotEmpty(depa.getDepaName())){
			sb.append(" and depaName like '%"+depa.getDepaName()+"%'");
		}
		PreparedStatement pstmt=con.prepareStatement(sb.toString().replaceFirst("and", "where"));
		ResultSet rs=pstmt.executeQuery();
		if(rs.next()){
			return rs.getInt("total");//获取查询到的系别总记录数
		}else{
			return 0;
		}
	}
	
	/**
	 * delete from tableName where field in (1,3,5)
	 * @param con
	 * @param delIds
	 * @return
	 * @throws Exception
	 */
	public int depaDelete(Connection con,String delIds)throws Exception{
		//删除系别编号在delIds里的系别
		String sql="delete from t_depa where deId in("+delIds+")";
		PreparedStatement pstmt=con.prepareStatement(sql);
		return pstmt.executeUpdate();
	}
	
	public int depaAdd(Connection con,Depa depa)throws Exception{
		String sql="insert into t_depa values(null,?,?)";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, depa.getDeId());
		pstmt.setString(2, depa.getDepaName());
		return pstmt.executeUpdate();
	}
	
	public int depaModify(Connection con,Depa depa)throws Exception{
		String sql="update t_depa set deId=?,depaName=? where dId=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, depa.getDeId());
		pstmt.setString(2, depa.getDepaName());
		pstmt.setString(3, depa.getdId());
		return pstmt.executeUpdate();
	}
	
}
