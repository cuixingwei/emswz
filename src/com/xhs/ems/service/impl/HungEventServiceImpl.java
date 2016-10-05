package com.xhs.ems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.dao.HungEventDAO;
import com.xhs.ems.service.HungEventService;

/**
 * @author 崔兴伟
 * @datetime 2015年4月15日 下午12:20:24
 */
@Service
public class HungEventServiceImpl implements HungEventService {
	@Autowired
	private HungEventDAO hungEventDAO;

	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月15日 下午12:20:24
	 * @see com.xhs.ems.service.HungEventService#getData(com.xhs.ems.bean.Parameter)
	 */
	@Override
	public Grid getData(Parameter parameter) {
		return hungEventDAO.getData(parameter);
	}

}
