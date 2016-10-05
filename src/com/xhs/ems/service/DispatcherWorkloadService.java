package com.xhs.ems.service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;

/**
 * @author 崔兴伟
 * @datetime 2015年8月18日 上午9:49:32
 */
public interface DispatcherWorkloadService {
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
