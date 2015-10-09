package com.xhs.ems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.dao.CallSpotTypeDAO;
import com.xhs.ems.service.CallSpotTypeService;

/**
 * @author 崔兴伟
 * @datetime 2015年4月15日 下午3:07:25
 */
@Service
public class CallSpotTypeServiceImpl implements CallSpotTypeService {
	@Autowired
	private CallSpotTypeDAO callSpotTypeDAO;

	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月15日 下午3:07:25
	 * @see com.xhs.ems.service.CallSpotTypeService#getData(com.xhs.ems.bean.Parameter)
	 */
	@Override
	public Grid getData(Parameter parameter) {
		return callSpotTypeDAO.getData(parameter);
	}

}
