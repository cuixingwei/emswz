package com.xhs.ems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.dao.DriverOutCallDAO;
import com.xhs.ems.service.DriverOutCallService;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 上午9:17:46
 */
@Service
public class DriverOutCallServiceImpl implements DriverOutCallService {

	@Autowired
	private DriverOutCallDAO driverOutCallDAO;

	@Override
	public Grid getData(Parameter parameter) {
		return driverOutCallDAO.getData(parameter);
	}

	@Override
	public Grid getDriverDetail(Parameter parameter) {
		return driverOutCallDAO.getDriverDetail(parameter);
	}

	@Override
	public Grid getDoctorNurseDetail(Parameter parameter) {
		return driverOutCallDAO.getDoctorNurseDetail(parameter);
	}

	@Override
	public Grid getCenterHospitalOutDetail(Parameter parameter) {
		return driverOutCallDAO.getCenterHospitalOutDetail(parameter);
	}

}
