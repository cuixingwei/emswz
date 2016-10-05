package com.xhs.ems.dao;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午1:22:00
 */
public interface InStationOutCallDetailDAO {
	/**
	 * @author 崔兴伟
	 * @datetime 2015年5月22日 下午1:22:18
	 * @param parameter
	 * @return  本院内出诊明细
	 */
	public Grid getData(Parameter parameter);
}
