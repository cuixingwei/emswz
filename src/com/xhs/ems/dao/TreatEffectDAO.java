package com.xhs.ems.dao;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午5:31:28
 */
public interface TreatEffectDAO {
	/**
	 * @author 崔兴伟
	 * @datetime 2015年5月22日 下午5:31:44
	 * @param parameter
	 * @return 救治效果统计
	 */
	public Grid getData(Parameter parameter);
}
