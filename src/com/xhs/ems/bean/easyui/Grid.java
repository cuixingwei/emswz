package com.xhs.ems.bean.easyui;

import java.util.ArrayList;
import java.util.List;

/**
 * EasyUI DataGrid模型
 * 
 * @author 崔兴伟
 * 
 */
public class Grid {

	private int total = 0;
	private List<?> rows = new ArrayList<Object>();

	public int getTotal() {
		return total;
	}

	public void setTotal(int i) {
		this.total = i;
	}

	public List<?> getRows() {
		return rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}

}
