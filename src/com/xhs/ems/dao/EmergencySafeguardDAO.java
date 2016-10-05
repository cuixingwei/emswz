package com.xhs.ems.dao;

import com.xhs.ems.bean.easyui.Parameter;

import com.xhs.ems.bean.easyui.Grid;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午1:08:29
 */
public interface EmergencySafeguardDAO {
	/**
	 * @author 崔兴伟
	 * @datetime 2015年5月22日 下午1:08:49
	 * @param parameter
	 * @return 急救保障明细
	 */
	public Grid getData(Parameter parameter);
}
