package com.xhs.ems.service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;

/**
 * @author 崔兴伟
 * @datetime 2015年4月15日 下午8:20:03
 */
public interface AnswerAlarmService {
	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月15日 下午8:19:02
	 * @param parameter
	 * @return 中心接警情况统计
	 */
	public Grid getData(Parameter parameter);
}
