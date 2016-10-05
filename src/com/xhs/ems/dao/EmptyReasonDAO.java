package com.xhs.ems.dao;

import java.util.List;

import com.xhs.ems.bean.EmptyReason;

/**
 * @author 崔兴伟
 * @datetime 2015年4月14日 上午11:12:34
 */
public interface EmptyReasonDAO {
	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月14日 上午11:13:37
	 * @return 放空车原因列表
	 */
	public List<EmptyReason> getData();
}
