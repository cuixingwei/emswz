package com.xhs.ems.dao;

import java.util.List;

import com.xhs.ems.bean.StopReason;

/**
 * @author CUIXINGWEI
 * @datetime 2015年4月10日 下午2:53:22
 */
public interface StopReasonDAO {
	/**
	 *@datetime 2015年4月10日 下午2:53:51
	 *@author CUIXINGWEI
	 *@return 中止任务原因列表
	 */
	public List<StopReason> getData();
}
