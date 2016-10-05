package com.xhs.ems.service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;

/**
 * 
 * @author CUIXINGWEI
 *
 */
public interface TeleBookService {
	/**
	 * 返回通迅录
	 * 
	 * @param parameter
	 * @return
	 */
	public Grid getData(Parameter parameter);
}
