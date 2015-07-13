package com.xhs.ems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.dao.EmergencySafeguardDAO;
import com.xhs.ems.service.EmergencySafeguardService;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午1:09:40
 */
@Service
public class EmergencySafeguardServiceImpl implements EmergencySafeguardService {
	@Autowired
	private EmergencySafeguardDAO emergencySafeguardDAO;

	@Override
	public Grid getData(Parameter parameter) {
		return emergencySafeguardDAO.getData(parameter);
	}

}
