package com.xhs.ems.service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午2:03:15
 */
public interface MissMealService {
	/**
	 * @author 崔兴伟
	 * @datetime 2015年5月22日 下午2:02:54
	 * @param parameter
	 * @return 误餐统计
	 */
	public Grid getData(Parameter parameter);
}
