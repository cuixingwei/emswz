package com.xhs.ems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.dao.SendSpotDAO;
import com.xhs.ems.service.SendSpotService;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午3:50:39
 */
@Service
public class SendSpotServiceImpl implements SendSpotService {

	@Autowired
	private SendSpotDAO sendSpotDAO;

	@Override
	public Grid getData(Parameter parameter) {
		return sendSpotDAO.getData(parameter);
	}

}
