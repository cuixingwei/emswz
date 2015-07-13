package com.xhs.ems.dao;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午5:04:48
 */
public interface TaskNatureDAO {
	/**
	 * @author 崔兴伟
	 * @datetime 2015年5月22日 下午5:05:15
	 * @param parameter
	 * @return 任务性质统计
	 */
	public Grid getData(Parameter parameter);
}
