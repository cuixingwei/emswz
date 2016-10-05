package com.xhs.ems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.dao.CarWorkDAO;
import com.xhs.ems.service.CarWorkService;

/**
 * @author 崔兴伟
 * @datetime 2015年4月17日 下午12:21:20
 */
@Service
public class CarWorkServiceImpl implements CarWorkService {

	@Autowired
	private CarWorkDAO carWorkDAO;

	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月17日 下午12:21:20
	 * @see com.xhs.ems.service.CarWorkService#getData(com.xhs.ems.bean.Parameter)
	 */
	@Override
	public Grid getData(Parameter parameter) {
		return carWorkDAO.getData(parameter);
	}

}
