package com.xhs.ems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.dao.NonEmergencyVehiclesDAO;
import com.xhs.ems.service.NonEmergencyVehiclesService;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午2:10:23
 */
@Service
public class NonEmergencyVehiclesServiceImpl implements
		NonEmergencyVehiclesService {

	@Autowired
	private NonEmergencyVehiclesDAO nonEmergencyVehiclesDAO;

	@Override
	public Grid getData(Parameter parameter) {
		return nonEmergencyVehiclesDAO.getData(parameter);
	}

}
