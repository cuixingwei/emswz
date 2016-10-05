package com.xhs.ems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.dao.PatientGenderDAO;
import com.xhs.ems.service.PatientGenderService;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午2:57:09
 */
@Service
public class PatientGenderServiceImpl implements PatientGenderService {

	@Autowired
	private PatientGenderDAO patientGenderDAO;

	@Override
	public Grid getData(Parameter parameter) {
		return patientGenderDAO.getData(parameter);
	}

}
