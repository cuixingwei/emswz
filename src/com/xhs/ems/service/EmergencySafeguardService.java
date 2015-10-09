package com.xhs.ems.service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午1:09:09
 */
public interface EmergencySafeguardService {
	/**
	 * @author 崔兴伟
	 * @datetime 2015年5月22日 下午1:08:49
	 * @param parameter
	 * @return 急救保障明细
	 */
	public Grid getData(Parameter parameter);
}
