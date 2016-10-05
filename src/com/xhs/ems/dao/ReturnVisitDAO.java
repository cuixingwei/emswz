package com.xhs.ems.dao;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午3:19:24
 */
public interface ReturnVisitDAO {
	/**
	 * @author 崔兴伟
	 * @datetime 2015年5月22日 下午3:19:43
	 * @param parameter
	 * @return 回访统计
	 */
	public Grid getData(Parameter parameter);
}
