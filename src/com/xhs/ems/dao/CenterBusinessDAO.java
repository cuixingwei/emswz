package com.xhs.ems.dao;

import java.util.List;

import com.xhs.ems.bean.CenterBusiness;
import com.xhs.ems.bean.easyui.Parameter;

/**
 * @datetime 2015年11月12日 下午12:57:53
 * @author 崔兴伟
 */
public interface CenterBusinessDAO {
	/**
	 * 中心业务数据统计
	 * 
	 * @datetime 2015年11月12日 下午12:59:38
	 * @author 崔兴伟
	 * @param parameter
	 * @return
	 */
	public List<CenterBusiness> getCenterBusinesseData(Parameter parameter);
}
