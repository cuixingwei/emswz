package com.xhs.ems.dao;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;

public interface RingToAcceptDAO {
	/**
	 * 返回振铃到接听大于X秒的相关数据
	 * 
	 * @param parameter
	 * @return
	 */
	public Grid getData(Parameter parameter);
}
