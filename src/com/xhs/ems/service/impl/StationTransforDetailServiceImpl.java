package com.xhs.ems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.dao.StationTransforDetailDAO;
import com.xhs.ems.service.StationTransforDetailService;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午4:51:33
 */
@Service
public class StationTransforDetailServiceImpl implements
		StationTransforDetailService {

	@Autowired
	private StationTransforDetailDAO stationTransforDetailDAO;

	@Override
	public Grid getData(Parameter parameter) {
		return stationTransforDetailDAO.getData(parameter);
	}

}
