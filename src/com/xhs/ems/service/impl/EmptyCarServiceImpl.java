package com.xhs.ems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.dao.EmptyCarDAO;
import com.xhs.ems.service.EmptyCarService;

/**
 * @author 崔兴伟
 * @datetime 2015年4月14日 上午8:57:06
 */
@Service
public class EmptyCarServiceImpl implements EmptyCarService {
	@Autowired
	private EmptyCarDAO emptyCarDAO;

	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月14日 上午8:57:06
	 * @see com.xhs.ems.service.EmptyCarService#getData(com.xhs.ems.bean.Parameter)
	 */
	@Override
	public Grid getData(Parameter parameter) {
		return emptyCarDAO.getData(parameter);
	}

}
