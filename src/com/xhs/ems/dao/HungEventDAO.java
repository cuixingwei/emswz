package com.xhs.ems.dao;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;

/**
 * @author 崔兴伟
 * @datetime 2015年4月15日 下午12:17:09
 */
public interface HungEventDAO {
	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月15日 下午12:17:42
	 * @param parameter
	 * @return 挂起事件流水统计
	 */
	public Grid getData(Parameter parameter);
}
