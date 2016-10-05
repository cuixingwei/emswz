package com.xhs.ems.dao;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;

/**
 * @author 崔兴伟
 * @datetime 2015年8月18日 上午9:39:26
 */
public interface DispatcherWorkloadDAO {
	/**
	 * 调度员工作量统计
	 * 
	 * @author 崔兴伟
	 * @datetime 2015年8月18日 上午9:39:41
	 * @param parameter
	 * @return
	 */
	public Grid getData(Parameter parameter);
}
