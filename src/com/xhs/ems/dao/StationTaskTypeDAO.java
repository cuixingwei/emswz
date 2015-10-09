package com.xhs.ems.dao;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午4:18:34
 */
public interface StationTaskTypeDAO {
	/**
	 * @author 崔兴伟
	 * @datetime 2015年5月22日 下午4:19:24
	 * @param parameter
	 * @return 站点任务类型统计
	 */
	public Grid getData(Parameter parameter);
}
