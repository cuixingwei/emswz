package com.xhs.ems.service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;

/**
 * @author 崔兴伟
 * @datetime 2015年4月15日 下午3:46:22
 */
public interface SendSpotTypeService {
	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月15日 下午3:45:49
	 * @param parameter
	 * @return 送往地点类型统计
	 */
	public Grid getData(Parameter parameter);
}
