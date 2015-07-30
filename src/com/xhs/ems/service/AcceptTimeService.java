package com.xhs.ems.service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;

public interface AcceptTimeService {
	/**
	 * @author CUIXINGWEI
	 * @param parameter
	 * @return 受理时间查询
	 */
	public Grid getData(Parameter parameter);
}
