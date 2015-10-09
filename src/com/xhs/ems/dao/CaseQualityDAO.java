package com.xhs.ems.dao;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;

/**
 * @author 崔兴伟
 * @datetime 2015年5月21日 下午8:25:20
 */
public interface CaseQualityDAO {
	/**
	 * @author 崔兴伟
	 * @datetime 2015年5月21日 下午8:25:44
	 * @param parameter
	 * @return 病历质量统计
	 */
	public Grid getData(Parameter parameter);
}
