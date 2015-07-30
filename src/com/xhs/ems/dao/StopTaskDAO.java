package com.xhs.ems.dao;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;

/**
 * @author CUIXINGWEI
 * @datetime 2015年4月10日 下午3:09:52
 */
public interface StopTaskDAO {
	/**
	 * @datetime 2015年4月10日 下午3:10:36
	 * @author CUIXINGWEI
	 * @param parameter
	 * @return 中止任务原因信息
	 */
	public Grid getData(Parameter parameter);
}
