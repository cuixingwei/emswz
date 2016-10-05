package com.xhs.ems.service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;

/**
 * @author CUIXINGWEI
 * @datetime 2015年4月10日 下午3:11:22
 */
public interface StopTaskService {
	/**
	 *@datetime 2015年4月10日 下午3:11:55
	 *@author CUIXINGWEI
	 *@param parameter
	 *@return 中止任务信息
	 */
	public Grid getData(Parameter parameter);
}
