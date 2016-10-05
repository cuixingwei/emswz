package com.xhs.ems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.dao.PatientAgeSpanDAO;
import com.xhs.ems.service.PatientAgeSpanService;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午2:49:37
 */
@Service
public class PatientAgeSpanServiceImpl implements PatientAgeSpanService {

	@Autowired
	private PatientAgeSpanDAO patientAgeSpanDAO;

	@Override
	public Grid getData(Parameter parameter) {
		return patientAgeSpanDAO.getData(parameter);
	}

}
