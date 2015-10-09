package com.xhs.ems.service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午3:50:10
 */
public interface SendSpotService {
	/**
	 * @author 崔兴伟
	 * @datetime 2015年5月22日 下午3:49:46
	 * @param parameter
	 * @return 送往地点统计
	 */
	public Grid getData(Parameter parameter);
}
