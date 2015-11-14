package com.xhs.ems.service;

import java.util.List;

import com.xhs.ems.bean.OutOfThree;
import com.xhs.ems.bean.easyui.Parameter;

/**
 * @datetime 2015年11月14日 下午3:25:18
 * @author 崔兴伟
 */
public interface OutOfThreeService {
	/**
	 * 出诊时间大于3分钟统计表
	 * 
	 * @datetime 2015年11月14日 下午3:24:49
	 * @author 崔兴伟
	 * @param parameter
	 * @return
	 */
	public List<OutOfThree> getData(Parameter parameter);
}
