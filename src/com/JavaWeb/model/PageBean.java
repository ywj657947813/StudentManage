package com.JavaWeb.model;
/**
 * 获取从后台查询到的数据
 * 算法：每行数据start=(page-1)*rows;
 * page从1开始，start从0开始
 * @author Yu
 *
 */
public class PageBean {
	 private int page;//第几页
	 private int rows;//每页的记录数
	 private int start;//起始数据记录数
	 
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
