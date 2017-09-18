package com.JavaWeb.model;
/**
 * ��ȡ�Ӻ�̨��ѯ��������
 * �㷨��ÿ������start=(page-1)*rows;
 * page��1��ʼ��start��0��ʼ
 * @author Yu
 *
 */
public class PageBean {
	 private int page;//�ڼ�ҳ
	 private int rows;//ÿҳ�ļ�¼��
	 private int start;//��ʼ���ݼ�¼��
	 
	 public PageBean(int page, int rows) {
			super();
			this.page = page;
			this.rows = rows;
		}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public int getStart() {
		return (page-1)*rows;
	}

}
