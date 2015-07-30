package com.xhs.ems.service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;

/**
 * @author 崔兴伟
 * @datetime 2015年4月15日 下午12:19:23
 */
public interface HungEventService {
	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月15日 下午12:19:41
	 * @param parameter
	 * @return 挂起事件流水
	 */
	public Grid getData(Parameter parameter);
}
