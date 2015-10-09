package com.xhs.ems.service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午3:40:19
 */
public interface SendOtherStationService {
	/**
	 * @author 崔兴伟
	 * @datetime 2015年5月22日 下午3:39:51
	 * @param parameter
	 * @return 其他医院调度分诊
	 */
	public Grid getData(Parameter parameter);
}
