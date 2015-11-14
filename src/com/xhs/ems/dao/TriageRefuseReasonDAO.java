package com.xhs.ems.dao;

import java.util.List;

import com.xhs.ems.bean.TriageRefuseReason;
import com.xhs.ems.bean.easyui.Parameter;

/**
 * @datetime 2015年11月14日 下午2:04:54
 * @author 崔兴伟
 */
public interface TriageRefuseReasonDAO {
	/**
	 * 分诊他院拒绝原因统计
	 * 
	 * @datetime 2015年11月14日 下午2:05:34
	 * @author 崔兴伟
	 * @param parameter
	 * @return
	 */
	public List<TriageRefuseReason> getData(Parameter parameter);
}
