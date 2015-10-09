package com.xhs.ems.dao;

import java.util.List;

import com.xhs.ems.bean.HungReason;

/**
 * @author 崔兴伟
 * @datetime 2015年4月15日 下午12:49:13
 */
public interface HungReasonDAO {
	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月15日 下午12:49:40
	 * @return 返回挂起原因列表
	 */
	public List<HungReason> getData();
}
