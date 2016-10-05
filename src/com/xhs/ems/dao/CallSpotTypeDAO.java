package com.xhs.ems.dao;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;

/**
 * @author 崔兴伟
 * @datetime 2015年4月15日  下午3:04:23
 */
public interface CallSpotTypeDAO {
	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月15日  下午3:04:52
	 * @param parameter
	 * @return  呼救现场地点类型统计
	 */
	public Grid getData(Parameter parameter);
}
