package com.xhs.ems.service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;

/**
 * @author 崔兴伟
 * @datetime 2015年5月21日 下午9:30:40
 */
public interface DiseaseTypeService {
	/**
	 * @author 崔兴伟
	 * @datetime 2015年5月21日 下午9:30:03
	 * @param parameter
	 * @return 疾病类型统计
	 */
	public Grid getData(Parameter parameter);
}
