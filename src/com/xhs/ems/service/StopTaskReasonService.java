package com.xhs.ems.service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;

/**
 * @author 崔兴伟
 * @datetime 2015年4月13日  下午2:53:47
 */
public interface StopTaskReasonService {
	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月13日  下午2:54:16
	 * @param parameter
	 * @return  中止任务原因查询
	 */
	public Grid getData(Parameter parameter);
}
