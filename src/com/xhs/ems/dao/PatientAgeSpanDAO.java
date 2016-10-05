package com.xhs.ems.dao;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午2:48:25
 */
public interface PatientAgeSpanDAO {
	/**
	 * @author 崔兴伟
	 * @datetime 2015年5月22日 下午2:48:45
	 * @param parameter
	 * @return 病人年龄分段统计
	 */
	public Grid getData(Parameter parameter);
}
