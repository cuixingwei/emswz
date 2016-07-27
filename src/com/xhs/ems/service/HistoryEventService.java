package com.xhs.ems.service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;

/**
 * @datetime 2016年7月27日 下午3:14:55
 * @author 崔兴伟
 */
public interface HistoryEventService {
	/**
	 * 历史事件查询
	 * @datetime 2016年7月27日 下午3:14:29
	 * @author 崔兴伟
	 * @param parameter
	 * @return
	 */
	public Grid getData(Parameter parameter);
}
