package com.xhs.ems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.dao.StopTaskReasonDAO;
import com.xhs.ems.service.StopTaskReasonService;

/**
 * @author 崔兴伟
 * @datetime 2015年4月13日  下午2:55:15
 */
@Service
public class StopTaskReasonServiceImpl implements StopTaskReasonService {
	@Autowired
	private StopTaskReasonDAO stopTaskReasonDAO;
	

	/**
	 * (non-Javadoc)
	 * @author 崔兴伟
	 * @datetime 2015年4月13日  下午2:57:07
	 * @see com.xhs.ems.dao.StopTaskReasonDAO#getData(com.xhs.ems.bean.Parameter)
	 */
	@Override
	public Grid getData(Parameter parameter) {
		return stopTaskReasonDAO.getData(parameter);
	}

}
