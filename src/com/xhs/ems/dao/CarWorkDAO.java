package com.xhs.ems.dao;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;

/**
 * @author 崔兴伟
 * @datetime 2015年4月17日 下午12:19:29
 */
public interface CarWorkDAO {
	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月17日 下午12:20:09
	 * @param parameter
	 * @return 车辆工作情况统计
	 */
	public Grid getData(Parameter parameter);
}
