package com.xhs.ems.dao;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午2:08:37
 */
public interface NonEmergencyVehiclesDAO {
	/**
	 * @author 崔兴伟
	 * @datetime 2015年5月22日 下午2:08:52
	 * @param parameter
	 * @return 非急救用车统计
	 */
	public Grid getData(Parameter parameter);
}
