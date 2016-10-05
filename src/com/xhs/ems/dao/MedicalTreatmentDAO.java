package com.xhs.ems.dao;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午1:55:22
 */
public interface MedicalTreatmentDAO {
	/**
	 * @author 崔兴伟
	 * @datetime 2015年5月22日 下午1:55:37
	 * @param parameter
	 * @return 医疗处置统计
	 */
	public Grid getData(Parameter parameter);
}
