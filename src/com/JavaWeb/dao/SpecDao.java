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
		if(spec!=null && StringUtil.isNotEmpty(spec.getSpecName())){//��ѯ�õ�������
			sb.append(" and specName like '%"+spec.getSpecName()+"%'");
		}if(spec!=null && StringUtil.isNotEmpty(spec.getDepaId())){//��ѯ�õ�������
			sb.append(" and depaId like '%"+spec.getDepaId()+"%'");
		}
		/**
		 * sql��ҳ
		 * select * from tablename limit start��2��,size��4��start�Ǵ�0��ʼ��
		 * ��ȡ����3������6����4����¼
		 */
		if(pageBean!=null){
			sb.append(" limit "+pageBean.getStart()+","+pageBean.getRows());
		}
		PreparedStatement pstmt=con.prepareStatement(sb.toString());
		return pstmt.executeQuery();
	}
	/**
	 * ���ݿ��ѯ���ļ�¼����
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
			return rs.getInt("total");//��ȡ��ѯ����רҵ�ܼ�¼��
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
	 * �ж�ϵ�����Ƿ��а༶
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
