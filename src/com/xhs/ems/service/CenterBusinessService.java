package com.xhs.ems.service;

import java.util.List;

import com.xhs.ems.bean.CenterBusiness;
import com.xhs.ems.bean.easyui.Parameter;

/**
 * @datetime 2015年11月12日 下午1:00:31
 * @author 崔兴伟
 */
public interface CenterBusinessService {
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
