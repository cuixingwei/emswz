package com.xhs.ems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.dao.MedicalTreatmentDAO;
import com.xhs.ems.service.MedicalTreatmentService;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午1:56:33
 */
@Service
public class MedicalTreatmentServiceImpl implements MedicalTreatmentService {

	@Autowired
	private MedicalTreatmentDAO medicalTreatmentDAO;

	@Override
	public Grid getData(Parameter parameter) {
		return medicalTreatmentDAO.getData(parameter);
	}

}
