package com.xhs.ems.service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午2:38:11
 */
public interface OutCallSpanService {
	/**
	 * @author 崔兴伟
	 * @datetime 2015年5月22日 下午2:37:45
	 * @param parameter
	 * @return 24小时出诊强度
	 */
	public Grid getData(Parameter parameter);
}
