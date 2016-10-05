package com.xhs.ems.service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;

/**
 * @author 崔兴伟
 * @datetime 2015年4月13日  下午4:27:49
 */
public interface CarPauseService {
	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月13日  下午4:28:11
	 * @param parameter
	 * @return 车辆暂停调用情况查询
	 */
	public Grid getData(Parameter parameter);
}
