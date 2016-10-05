package com.xhs.ems.service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;

/**
 * @author 崔兴伟
 * @datetime 2015年5月21日 下午8:53:42
 */
public interface DiseaseGradeService {
	/**
	 * @author 崔兴伟
	 * @datetime 2015年5月21日 下午8:52:22
	 * @param parameter
	 * @return 病情分级
	 */
	public Grid getData(Parameter parameter);
}
