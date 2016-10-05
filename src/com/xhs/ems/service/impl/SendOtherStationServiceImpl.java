package com.xhs.ems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.dao.SendOtherStationDAO;
import com.xhs.ems.service.SendOtherStationService;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午3:40:49
 */
@Service
public class SendOtherStationServiceImpl implements SendOtherStationService {

	@Autowired
	private SendOtherStationDAO sendOtherStationDAO;

	@Override
	public Grid getData(Parameter parameter) {
		return sendOtherStationDAO.getData(parameter);
	}

}
