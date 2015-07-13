package com.xhs.ems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.dao.StationTransforDAO;
import com.xhs.ems.service.StationTransforService;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午4:41:35
 */
@Service
public class StationTransforServiceImpl implements StationTransforService {

	@Autowired
	private StationTransforDAO stationTransforDAO;

	@Override
	public Grid getData(Parameter parameter) {
		return stationTransforDAO.getData(parameter);
	}

}
