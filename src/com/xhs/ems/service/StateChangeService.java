package com.xhs.ems.service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;

/**
 * @author CUIXINGWEI
 * @date 2015年3月30日
 */
public interface StateChangeService {
	/**
	 *@date 2015年3月30日
	 *@author CUIXINGWEI
	 *@param parameter
	 *@return 返回状态变化统计数据
	 */
	public Grid getData(Parameter parameter);
}
