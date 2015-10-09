package com.xhs.ems.dao;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;

/**
 * @author 崔兴伟
 * @datetime 2015年5月21日 下午9:12:41
 */
public interface DiseaseReasonDAO {
	/**
	 * @author 崔兴伟
	 * @datetime 2015年5月21日 下午9:13:08
	 * @param parameter
	 * @return  病因统计
	 */
	public Grid getData(Parameter parameter);
}
