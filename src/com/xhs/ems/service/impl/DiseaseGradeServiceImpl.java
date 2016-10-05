package com.xhs.ems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.dao.DiseaseGradeDAO;
import com.xhs.ems.service.DiseaseGradeService;

/**
 * @author 崔兴伟
 * @datetime 2015年5月21日 下午8:54:24
 */
@Service
public class DiseaseGradeServiceImpl implements DiseaseGradeService {

	@Autowired
	private DiseaseGradeDAO diseaseGradeDAO;

	@Override
	public Grid getData(Parameter parameter) {
		return diseaseGradeDAO.getData(parameter);
	}

}
