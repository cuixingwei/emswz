package com.xhs.ems.dao;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午3:49:28
 */
public interface SendSpotDAO {
	/**
	 * @author 崔兴伟
	 * @datetime 2015年5月22日 下午3:49:46
	 * @param parameter
	 * @return  送往地点统计
	 */
	public Grid getData(Parameter parameter);
}
