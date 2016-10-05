package com.xhs.ems.service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;

/**
 * @author 崔兴伟
 * @datetime 2015年4月15日 下午2:10:05
 */
public interface HungEventReasonService {
	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月15日 下午2:10:33
	 * @param parameter
	 * @return 挂起原因统计
	 */
	public Grid getData(Parameter parameter);
}
