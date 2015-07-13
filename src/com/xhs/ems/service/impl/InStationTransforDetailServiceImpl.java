package com.xhs.ems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.dao.InStationTransforDetailDAO;
import com.xhs.ems.service.InStationTransforDetailService;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午1:47:45
 */
@Service
public class InStationTransforDetailServiceImpl implements
		InStationTransforDetailService {

	@Autowired
	private InStationTransforDetailDAO inStationTransforDetailDAO;

	@Override
	public Grid getData(Parameter parameter) {
		return inStationTransforDetailDAO.getData(parameter);
	}

}
