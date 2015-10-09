package com.xhs.ems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.dao.AreaDAO;
import com.xhs.ems.service.AreaService;

/**
 * @author 崔兴伟
 * @datetime 2015年5月21日 下午5:26:41
 */
@Service
public class AreaServiceImpl implements AreaService {
	@Autowired
	private AreaDAO areaDAO;

	@Override
	public Grid getData(Parameter parameter) {
		return areaDAO.getData(parameter);
	}

}
