package com.xhs.ems.service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;

public interface CaseQualityService {
	/**
	 * @author 崔兴伟
	 * @datetime 2015年5月21日 下午8:25:44
	 * @param parameter
	 * @return 病历质量统计
	 */
	public Grid getData(Parameter parameter);
}
