package com.xhs.ems.dao;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;

/**
 * @author 崔兴伟
 * @datetime 2015年4月16日 下午8:01:34
 */
public interface SubstationLateVisitDAO {
	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月16日 下午8:02:02
	 * @param parameter
	 * @return 急救站3分钟未出诊
	 */
	public Grid getData(Parameter parameter);
}
