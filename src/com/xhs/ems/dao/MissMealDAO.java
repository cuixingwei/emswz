package com.xhs.ems.dao;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午2:02:31
 */
public interface MissMealDAO {
	/**
	 * @author 崔兴伟
	 * @datetime 2015年5月22日 下午2:02:54
	 * @param parameter
	 * @return 误餐统计
	 */
	public Grid getData(Parameter parameter);
}
