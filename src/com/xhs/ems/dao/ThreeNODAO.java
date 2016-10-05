package com.xhs.ems.dao;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午5:18:14
 */
public interface ThreeNODAO {
	/**
	 * @author 崔兴伟
	 * @datetime 2015年5月22日 下午5:18:30
	 * @param parameter
	 * @return 三无统计
	 */
	public Grid getData(Parameter parameter);
}
