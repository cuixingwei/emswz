package com.xhs.ems.dao;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;

/**
 * @author 崔兴伟
 * @datetime 2015年5月21日 下午7:32:52
 */
public interface CallNoAcceptDAO {
	/**
	 * @author 崔兴伟
	 * @datetime 2015年5月21日 下午7:33:40
	 * @param parameter
	 * @return  呼救未受理
	 */
	public Grid getData(Parameter parameter);
}
