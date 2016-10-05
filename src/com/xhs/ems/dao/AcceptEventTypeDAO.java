package com.xhs.ems.dao;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;

public interface AcceptEventTypeDAO {
	/**
	 * @author CUIXINGWEI
	 * @param parameter
	 * @return 事件类型统计
	 */
	public Grid getData(Parameter parameter);
}
