package com.xhs.ems.dao;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;

/**
 * @author CUIXINGWEI
 * @date 2015年3月30日
 */
public interface StateChangeDAO {
	/**
	 * @date 2015年3月30日
	 * @author CUIXINGWEI
	 * @param parameter
	 * @return 状态变化统计
	 */
	public Grid getData(Parameter parameter);
}
