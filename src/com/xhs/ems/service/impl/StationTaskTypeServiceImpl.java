package com.xhs.ems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.dao.StationTaskTypeDAO;
import com.xhs.ems.service.StationTaskTypeService;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午4:20:51
 */
@Service
public class StationTaskTypeServiceImpl implements StationTaskTypeService {

	@Autowired
	private StationTaskTypeDAO stationTaskTypeDAO;

	@Override
	public Grid getData(Parameter parameter) {
		return stationTaskTypeDAO.getData(parameter);
	}

}
