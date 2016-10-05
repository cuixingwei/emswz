package com.xhs.ems.dao;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午1:46:08
 */
public interface InStationTransforDetailDAO {
	/**
	 * @author 崔兴伟
	 * @datetime 2015年5月22日 下午1:46:33
	 * @param parameter
	 * @return 院内转运明细
	 */
	public Grid getData(Parameter parameter);
}
