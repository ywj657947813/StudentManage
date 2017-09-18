package com.JavaWeb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.JavaWeb.model.PageBean;
import com.JavaWeb.model.Spec;
import com.JavaWeb.util.StringUtil;

public class SpecDao {

	public ResultSet specList(Connection con,PageBean pageBean,Spec spec)throws Exception{
		StringBuffer sb=new StringBuffer("select * from t_spec s,t_depa d where s.depaId=d.deId");
		if(spec!=null && StringUtil.isNotEmpty(spec.getSpecName())){//查询用到的条件
			sb.append(" and specName like '%"+spec.getSpecName()+"%'");
		}if(spec!=null && StringUtil.isNotEmpty(spec.getDepaId())){//查询用到的条件
			sb.append(" and depaId like '%"+spec.getDepaId()+"%'");
		}
		/**
		 * sql分页
		 * select * from tablename limit start（2）,size（4）start是从0开始，
		 * 即取出第3条至第6条，4条记录
		 */
		if(pageBean!=null){
			sb.append(" limit "+pageBean.getStart()+","+pageBean.getRows());
		}
		PreparedStatement pstmt=con.prepareStatement(sb.toString());
		return pstmt.executeQuery();
	}
	/**
	 * 数据库查询到的记录总数
	 *
	 */
	public int specCount(Connection con,Spec spec)throws Exception{
		StringBuffer sb=new StringBuffer("select count(*) as total from t_spec");
		if(StringUtil.isNotEmpty(spec.getSpecName())){
			sb.append(" and specName like '%"+spec.getSpecName()+"%'");
		}
		PreparedStatement pstmt=con.prepareStatement(sb.toString().replaceFirst("and", "where"));
		ResultSet rs=pstmt.executeQuery();
		if(rs.next()){
			return rs.getInt("total");//获取查询到的专业总记录数
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
	public int specDelete(Connection con,String delIds)throws Exception{
		String sql="delete from t_spec where spId in("+delIds+")";
		PreparedStatement pstmt=con.prepareStatement(sql);
		return pstmt.executeUpdate();
	}
	
	public int specAdd(Connection con,Spec spec)throws Exception{
		String sql="insert into t_spec values(null,?,?,?)";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, spec.getSpId());
		pstmt.setString(2, spec.getSpecName());
		pstmt.setString(3, spec.getDepaId());
		return pstmt.executeUpdate();
	}
	
	public int specModify(Connection con,Spec spec)throws Exception{
		String sql="update t_spec set spId=?,specName=?,depaId=? where sId=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, spec.getSpId());
		pstmt.setString(2, spec.getSpecName());
		pstmt.setString(3, spec.getDepaId());
		pstmt.setString(4, spec.getsId());
		return pstmt.executeUpdate();
	}
	/**
	 * 判断系别下是否有班级
	 * 
	 */
	public boolean getSpecByDepaId(Connection con,String depaId)throws Exception{
		String sql="select * from t_spec where depaId=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, depaId);
		ResultSet rs=pstmt.executeQuery();
		if(rs.next()){
			return true;
		}else{
			return false;
		}
	}
	
}
