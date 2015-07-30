package com.xhs.ems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.dao.StateChangeDAO;
import com.xhs.ems.service.StateChangeService;

/**
 * @author CUIXINGWEI
 * @date 2015年3月30日
 */
@Service
public class StateChangeServiceImpl implements StateChangeService {
	@Autowired
	private StateChangeDAO stateChangeDAO;
	
	/**
	 * @author CUIXINGWEI
	 * @see com.xhs.ems.service.StateChangeService#getData(com.xhs.ems.bean.Parameter)
	 * @date 2015年3月30日
	 */
	@Override
	public Grid getData(Parameter parameter) {
		return stateChangeDAO.getData(parameter);
	}

}
