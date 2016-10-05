package com.xhs.ems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.dao.ThreeNODAO;
import com.xhs.ems.service.ThreeNOService;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午5:19:29
 */
@Service
public class ThreeNOServiceImpl implements ThreeNOService {

	@Autowired
	private ThreeNODAO threeNODAO;

	@Override
	public Grid getData(Parameter parameter) {
		return threeNODAO.getData(parameter);
	}

}
