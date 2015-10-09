package com.xhs.ems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.dao.CarPauseDAO;
import com.xhs.ems.service.CarPauseService;

/**
 * @author 崔兴伟
 * @datetime 2015年4月13日 下午4:29:24
 */
@Service
public class CarPauseServiceImpl implements CarPauseService {
	@Autowired
	private CarPauseDAO carPauseDAO;

	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月13日 下午4:29:24
	 * @see com.xhs.ems.service.CarPauseService#getData(com.xhs.ems.bean.Parameter)
	 */
	@Override
	public Grid getData(Parameter parameter) {
		return carPauseDAO.getData(parameter);
	}

}
