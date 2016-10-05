package com.xhs.ems.dao;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;

/**
 * @author 崔兴伟
 * @datetime 2015年4月15日 下午8:18:26
 */
public interface AnswerAlarmDAO {
	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月15日 下午8:19:02
	 * @param parameter
	 * @return 中心接警情况统计
	 */
	public Grid getData(Parameter parameter);
}
