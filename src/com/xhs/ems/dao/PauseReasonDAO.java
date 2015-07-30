package com.xhs.ems.dao;

import java.util.List;

import com.xhs.ems.bean.PauseReason;

/**
 * @author 崔兴伟
 * @datetime 2015年4月13日  下午5:09:34
 */
public interface PauseReasonDAO {
	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月13日  下午5:10:07
	 * @return 车辆暂停调用原因列表
	 */
	public List<PauseReason> getData();
}
