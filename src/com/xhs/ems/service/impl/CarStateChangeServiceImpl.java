package com.xhs.ems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.dao.CarStateChangeDAO;
import com.xhs.ems.service.CarStateChangeService;

/**
 * @author 崔兴伟
 * @datetime 2015年4月13日 下午7:46:04
 */
@Service
public class CarStateChangeServiceImpl implements CarStateChangeService {
	@Autowired
	private CarStateChangeDAO carStateChangeDAO;

	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月13日 下午7:46:04
	 * @see com.xhs.ems.service.CarStateChangeService#getData(com.xhs.ems.bean.Parameter)
	 */
	@Override
	public Grid getData(Parameter parameter) {
		return carStateChangeDAO.getData(parameter);
	}

}
