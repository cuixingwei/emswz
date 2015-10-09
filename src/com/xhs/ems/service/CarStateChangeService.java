package com.xhs.ems.service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;

/**
 * @author 崔兴伟
 * @datetime 2015年4月13日 下午7:44:38
 */
public interface CarStateChangeService {
	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月13日 下午7:45:02
	 * @param parameter
	 * @return 车辆状态变化统计
	 */
	public Grid getData(Parameter parameter);
}
