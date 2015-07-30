package com.xhs.ems.service;

import java.util.List;

import com.xhs.ems.bean.PauseReason;

/**
 * @author 崔兴伟
 * @datetime 2015年4月13日  下午5:15:14
 */
public interface PauseReasonService {
	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月13日  下午5:15:38
	 * @return 车辆暂停调用原因列表
	 */
	public List<PauseReason> getData();
}
