package com.xhs.ems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.dao.StationOutCallDAO;
import com.xhs.ems.service.StationOutCallService;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午3:57:27
 */
@Service
public class StationOutCallServiceImpl implements StationOutCallService {

	@Autowired
	private StationOutCallDAO stationOutCallDAO;

	@Override
	public Grid getData(Parameter parameter) {
		return stationOutCallDAO.getData(parameter);
	}

}
