package com.xhs.ems.service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;

/**
 * @author 崔兴伟
 * @datetime 2015年4月22日 下午2:09:23
 */
public interface PatientCaseFillCountService {
	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月22日 下午2:07:29
	 * @param parameter
	 * @return 返回病例回填率
	 */
	public Grid getData(Parameter parameter);
}
