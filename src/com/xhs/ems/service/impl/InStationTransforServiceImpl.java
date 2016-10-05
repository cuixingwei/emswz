package com.xhs.ems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.dao.InStationTransforDAO;
import com.xhs.ems.service.InStationTransforService;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午1:37:01
 */
@Service
public class InStationTransforServiceImpl implements InStationTransforService {

	@Autowired
	private InStationTransforDAO inStationTransforDAO;

	@Override
	public Grid getData(Parameter parameter) {
		return inStationTransforDAO.getData(parameter);
	}

}
