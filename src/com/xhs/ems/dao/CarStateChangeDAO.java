package com.xhs.ems.dao;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;

/**
 * @author 崔兴伟
 * @datetime 2015年4月13日  下午7:42:47
 */
public interface CarStateChangeDAO {
	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月13日  下午7:43:12
	 * @param parameter
	 * @return 车辆状态变化
	 */
	public Grid getData(Parameter parameter);
}
