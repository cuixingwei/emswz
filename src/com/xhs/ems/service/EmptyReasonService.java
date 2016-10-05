package com.xhs.ems.service;

import java.util.List;

import com.xhs.ems.bean.EmptyReason;

/**
 * @author 崔兴伟
 * @datetime 2015年4月14日 上午11:18:00
 */
public interface EmptyReasonService {
	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月14日 上午11:18:26
	 * @return 放空车原因列表
	 */
	public List<EmptyReason> getData();
}
