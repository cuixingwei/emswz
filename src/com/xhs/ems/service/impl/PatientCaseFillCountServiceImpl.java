package com.xhs.ems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.dao.PatientCaseFillCountDAO;
import com.xhs.ems.service.PatientCaseFillCountService;

/**
 * @author 崔兴伟
 * @datetime 2015年4月22日 下午2:09:59
 */
@Service
public class PatientCaseFillCountServiceImpl implements PatientCaseFillCountService {
	@Autowired
	private PatientCaseFillCountDAO patientCaseFillCountDAO;

	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月22日 下午2:09:59
	 * @see com.xhs.ems.service.PatientCaseFillCountService#getData(com.xhs.ems.bean.Parameter)
	 */
	@Override
	public Grid getData(Parameter parameter) {
		return patientCaseFillCountDAO.getData(parameter);
	}

}
