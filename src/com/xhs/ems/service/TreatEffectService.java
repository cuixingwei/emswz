package com.xhs.ems.service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午5:32:15
 */
public interface TreatEffectService {
	/**
	 * @author 崔兴伟
	 * @datetime 2015年5月22日 下午5:31:44
	 * @param parameter
	 * @return 救治效果统计
	 */
	public Grid getData(Parameter parameter);
}
