package com.xhs.ems.service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 上午9:14:57
 */
public interface DriverOutCallService {
	/**
	 * @author 崔兴伟
	 * @datetime 2015年5月22日 上午9:14:30
	 * @param parameter
	 * @return 司机出诊表
	 */
	public Grid getData(Parameter parameter);
}
