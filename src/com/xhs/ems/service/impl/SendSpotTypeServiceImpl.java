package com.xhs.ems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.dao.SendSpotTypeDAO;
import com.xhs.ems.service.SendSpotTypeService;

/**
 * @author 崔兴伟
 * @datetime 2015年4月15日 下午3:47:10
 */
@Service
public class SendSpotTypeServiceImpl implements SendSpotTypeService {
	@Autowired
	private SendSpotTypeDAO sendSpotTypeDAO;

	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月15日 下午3:47:10
	 * @see com.xhs.ems.service.SendSpotTypeService#getData(com.xhs.ems.bean.Parameter)
	 */
	@Override
	public Grid getData(Parameter parameter) {
		return sendSpotTypeDAO.getData(parameter);
	}

}
