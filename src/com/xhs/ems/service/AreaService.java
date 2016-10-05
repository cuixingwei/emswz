package com.xhs.ems.service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;

/**
 * @author 崔兴伟
 * @datetime 2015年5月21日 下午5:26:08
 */
public interface AreaService {
	/**
	 * @author 崔兴伟
	 * @datetime 2015年5月21日 下午5:25:04
	 * @param parameter
	 * @return 区域统计
	 */
	public Grid getData(Parameter parameter);
}
