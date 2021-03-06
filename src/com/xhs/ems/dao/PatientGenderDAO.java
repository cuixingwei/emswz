package com.xhs.ems.dao;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午2:55:54
 */
public interface PatientGenderDAO {
	/**
	 * @author 崔兴伟
	 * @datetime 2015年5月22日 下午2:56:16
	 * @param parameter
	 * @return 病人性别统计
	 */
	public Grid getData(Parameter parameter);
}
