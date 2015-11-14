package com.xhs.ems.service;

import java.util.List;

import com.xhs.ems.bean.DocterNurseDriverOutCarTime;
import com.xhs.ems.bean.easyui.Parameter;

/**
 * @datetime 2015年11月14日 下午3:59:19
 * @author 崔兴伟
 */
public interface DocterNurseDriverOutCarTimeService {
	/**
	 * 医生、护士、驾驶员出车时间表
	 * 
	 * @datetime 2015年11月14日 下午3:58:42
	 * @author 崔兴伟
	 * @param parameter
	 * @return
	 */
	public List<DocterNurseDriverOutCarTime> getData(Parameter parameter);
}
