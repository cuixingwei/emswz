package com.xhs.ems.dao;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;

/**
 * @author 崔兴伟
 * @datetime 2015年4月14日 下午1:45:49
 */
public interface EmptyCarReasonDAO {
	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月14日 下午1:46:16
	 * @param parameter
	 * @return 放空车原因统计
	 */
	public Grid getData(Parameter parameter);
}
