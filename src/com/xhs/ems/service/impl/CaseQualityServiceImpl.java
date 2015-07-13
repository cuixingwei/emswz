package com.xhs.ems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.dao.CaseQualityDAO;
import com.xhs.ems.service.CaseQualityService;

/**
 * @author 崔兴伟
 * @datetime 2015年5月21日 下午8:29:45
 */
@Service
public class CaseQualityServiceImpl implements CaseQualityService {
	@Autowired
	private CaseQualityDAO caseQualityDAO;

	@Override
	public Grid getData(Parameter parameter) {
		return caseQualityDAO.getData(parameter);
	}

}
