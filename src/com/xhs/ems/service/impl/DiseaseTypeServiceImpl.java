package com.xhs.ems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.dao.DiseaseTypeDAO;
import com.xhs.ems.service.DiseaseTypeService;

/**
 * @author 崔兴伟
 * @datetime 2015年5月21日 下午9:31:35
 */
@Service
public class DiseaseTypeServiceImpl implements DiseaseTypeService {
	@Autowired
	private DiseaseTypeDAO diseaseTypeDAO;

	@Override
	public Grid getData(Parameter parameter) {
		return diseaseTypeDAO.getData(parameter);
	}
}
