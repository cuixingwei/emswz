package com.xhs.ems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.dao.InStationOutCallDetailDAO;
import com.xhs.ems.service.InStationOutCallDetailService;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午1:23:30
 */
@Service
public class InStationOutCallDetailServiceImpl implements
		InStationOutCallDetailService {

	@Autowired
	private InStationOutCallDetailDAO inStationOutCallDetailDAO;

	@Override
	public Grid getData(Parameter parameter) {
		return inStationOutCallDetailDAO.getData(parameter);
	}

}
