package com.xhs.ems.service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午1:22:59
 */
public interface InStationOutCallDetailService {
	/**
	 * @author 崔兴伟
	 * @datetime 2015年5月22日 下午1:22:18
	 * @param parameter
	 * @return 本院内出诊明细
	 */
	public Grid getData(Parameter parameter);
}
