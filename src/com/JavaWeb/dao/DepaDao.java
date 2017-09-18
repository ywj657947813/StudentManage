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
			//��ѯ�õ���������ϵ��ģ����ѯ
			sb.append(" and depaName like '%"+depa.getDepaName()+"%'");
		}
		/**
		 * sql��ҳ
		 * select * from tablename limit start��2��,size��4��start�Ǵ�0��ʼ��
		 * ��ȡ����3������6����4����¼
		 */
		if(pageBean!=null){
			sb.append(" limit "+pageBean.getStart()+","+pageBean.getRows());
		}
		PreparedStatement pstmt=con.prepareStatement(sb.toString().replaceFirst("and", "where"));
		return pstmt.executeQuery();
	}
	/**
	 * �������ݿ��ѯ���ļ�¼����
	 *
	 */
	public int depaCount(Connection con,Depa depa)throws Exception{
		StringBuffer sb=new StringBuffer("select count(*) as total from t_depa");
		/**
		 * ϵ��ģ����ѯ�����ز�ѯ���ļ�¼����
		 */
		if(StringUtil.isNotEmpty(depa.getDepaName())){
			sb.append(" and depaName like '%"+depa.getDepaName()+"%'");
		}
		PreparedStatement pstmt=con.prepareStatement(sb.toString().replaceFirst("and", "where"));
		ResultSet rs=pstmt.executeQuery();
		if(rs.next()){
			return rs.getInt("total");//��ȡ��ѯ����ϵ���ܼ�¼��
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
		//ɾ��ϵ������delIds���ϵ��
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
