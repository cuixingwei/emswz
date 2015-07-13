package com.xhs.ems.service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午12:47:24
 */
public interface EmergencyEventService {
	/**
	 * @author 崔兴伟
	 * @datetime 2015年5月22日 下午12:45:33
	 * @param parameter
	 * @return 突发时间统计
	 */
	public Grid getData(Parameter parameter);
}
