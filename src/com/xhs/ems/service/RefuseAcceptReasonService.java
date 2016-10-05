package com.xhs.ems.service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午3:10:28
 */
public interface RefuseAcceptReasonService {
	/**
	 * @author 崔兴伟
	 * @datetime 2015年5月22日 下午3:09:49
	 * @param parameter
	 * @return 分诊他院拒绝受理原因统计
	 */
	public Grid getData(Parameter parameter);
}
