package com.xhs.ems.dao;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午3:56:16
 */
public interface StationOutCallDAO {
	/**
	 * @author 崔兴伟
	 * @datetime 2015年5月22日 下午3:56:31
	 * @param parameter
	 * @return 站点出诊情况
	 */
	public Grid getData(Parameter parameter);
}
