package com.xhs.ems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.dao.DiseaseReasonDAO;
import com.xhs.ems.service.DiseaseReasonService;

/**
 * @author 崔兴伟
 * @datetime 2015年5月21日 下午9:15:21
 */
@Service
public class DiseaseReasonServiceImpl implements DiseaseReasonService {

	@Autowired
	private DiseaseReasonDAO diseaseReasonDAO;

	@Override
	public Grid getData(Parameter parameter) {
		return diseaseReasonDAO.getData(parameter);
	}

}
