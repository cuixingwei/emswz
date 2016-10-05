package com.xhs.ems.service;

import java.util.List;

import com.xhs.ems.bean.StopReason;

/**
 * @author CUIXINGWEI
 * @datetime 2015年4月10日 下午2:54:53
 */
public interface StopReasonService {
	/**
	 * @datetime 2015年4月10日 下午2:55:18
	 * @author CUIXINGWEI
	 * @return 中止任务原因列表
	 */
	public List<StopReason> getData();
}
