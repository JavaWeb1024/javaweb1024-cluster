package com.dinghao.entity.vo.template;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.dinghao.entity.vo.manage.PageVo;

public class JqGridVo<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JqGridVo(Object t){
		this.pageNum= ((PageVo) t).getPageNum();
		this.rows=((PageVo) t).getRows();
	}

	private List<T> list = new ArrayList<T>();
	/**
	 * 页码
	 */
	private int pageNum;
	/**
	 * 每页显示数量
	 */
	private int rows;
	/**
	 * 总数
	 */
	private int records;

	public JSONObject getJSONObject() {
		JSONObject obj = new JSONObject();
		obj.put("rows", list);
		// 总数
		obj.put("total", records % rows == 0 ? records / rows : records / rows + 1);
		// 页码
		obj.put("page", pageNum);
		// 显示数量
		obj.put("records", records);

		return obj;

	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getRecords() {
		return records;
	}

	public void setRecords(int records) {
		this.records = records;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

}
