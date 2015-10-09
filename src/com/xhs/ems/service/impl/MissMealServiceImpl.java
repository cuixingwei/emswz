package com.xhs.ems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.dao.MissMealDAO;
import com.xhs.ems.service.MissMealService;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午2:03:39
 */
@Service
public class MissMealServiceImpl implements MissMealService {

	@Autowired
	private MissMealDAO missMealDAO;

	@Override
	public Grid getData(Parameter parameter) {
		return missMealDAO.getData(parameter);
	}

}
