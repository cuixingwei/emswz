package com.xhs.ems.dao;

import java.util.List;

import com.xhs.ems.bean.OutResult;
import com.xhs.ems.bean.easyui.Parameter;

/**
 * @datetime 2015年11月14日 下午1:30:58
 * @author 崔兴伟
 */
public interface OutResultDAO {
	/**
	 * 出诊结果统计
	 * 
	 * @datetime 2015年11月14日 下午1:31:34
	 * @author 崔兴伟
	 * @param parameter
	 * @return
	 */
	public List<OutResult> getData(Parameter parameter);
}
