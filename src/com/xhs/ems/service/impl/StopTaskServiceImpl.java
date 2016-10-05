package com.xhs.ems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.dao.StopTaskDAO;
import com.xhs.ems.service.StopTaskService;

/**
 * @author CUIXINGWEI
 * @datetime 2015年4月10日 下午3:12:44
 */
@Service
public class StopTaskServiceImpl implements StopTaskService {
	@Autowired
	private StopTaskDAO stopTaskDAO;

	/** 
	 * @author CUIXINGWEI
	 * @see com.xhs.ems.service.StopTaskService#getData(com.xhs.ems.bean.Parameter)
	 * @datetime 2015年4月10日 下午3:14:56
	 */
	@Override
	public Grid getData(Parameter parameter) {
		return stopTaskDAO.getData(parameter);
	}

}
