package com.xhs.ems.dao;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;

/**
 * @author 崔兴伟
 * @datetime 2015年4月22日 下午2:07:07
 */
public interface PatientCaseFillCountDAO {
	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月22日 下午2:07:29
	 * @param parameter
	 * @return 返回病例回填率
	 */
	public Grid getData(Parameter parameter);
}
