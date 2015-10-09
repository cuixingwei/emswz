package com.xhs.ems.dao;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;

/**
 * @author 崔兴伟
 * @datetime 2015年5月21日 下午9:58:36
 */
public interface DocterNurseDriverDAO {
	/**
	 * @author 崔兴伟
	 * @datetime 2015年5月21日 下午9:58:56
	 * @param parameter
	 * @return 医护、司机工作统计
	 */
	public Grid getData(Parameter parameter);
}
