package com.xhs.ems.dao;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午4:40:14
 */
public interface StationTransforDAO {
	/**
	 * @author 崔兴伟
	 * @datetime 2015年5月22日 下午4:40:30
	 * @param parameter
	 * @return 医院转诊统计
	 */
	public Grid getData(Parameter parameter);
}
