package com.xhs.ems.dao;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;

/**
 * @author 崔兴伟
 * @datetime 2015年4月13日  下午4:25:10
 */
public interface CarPauseDAO {
	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月13日  下午4:25:48
	 * @param parameter
	 * @return 车辆暂停情况查询
	 */
	public Grid getData(Parameter parameter);
}
