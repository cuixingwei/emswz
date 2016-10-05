package com.xhs.ems.service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;

/**
 * 
 * @author CUIXINGWEI
 *
 */
public interface AcceptMarkService {
	/**
	 * @param parameter
	 * @return 受理备注数据
	 */
	public Grid getData(Parameter parameter);
}
