package com.xhs.ems.service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午4:51:00
 */
public interface StationTransforDetailService {
	/**
	 * @author 崔兴伟
	 * @datetime 2015年5月22日 下午4:50:35
	 * @param parameter
	 * @return 医院转诊明细
	 */
	public Grid getData(Parameter parameter);
}
