package com.xhs.ems.dao;

import java.util.List;

import com.xhs.ems.bean.Dictionary;

/**
 * @author 崔兴伟
 * @datetime 2015年10月9日 下午1:13:27
 */
public interface DictionaryDAO {
	/**
	 * 获取任务结果字典表
	 * 
	 * @author 崔兴伟
	 * @datetime 2015年10月9日 下午1:14:05
	 * @return
	 */
	public List<Dictionary> getTaskResult();
	
	/**
	 * 获取区域字典表
	 * 
	 * @author 崔兴伟
	 * @datetime 2015年10月9日 下午2:49:38
	 * @return
	 */
	public List<Dictionary> getArea();
}
