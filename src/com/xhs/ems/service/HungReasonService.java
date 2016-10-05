package com.xhs.ems.service;

import java.util.List;

import com.xhs.ems.bean.HungReason;

/**
 * @author 崔兴伟
 * @datetime 2015年4月15日 下午12:57:05
 */
public interface HungReasonService {
	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月15日 下午12:57:26
	 * @return 挂起事件原因列表
	 */
	public List<HungReason> getData();
}
