package com.xhs.ems.service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午1:35:46
 */
public interface InStationTransforService {
	/**
	 * @author 崔兴伟
	 * @datetime 2015年5月22日 下午1:36:02
	 * @param parameter
	 * @return  院内转运统计
	 */
	public Grid getData(Parameter parameter);
}
