package com.xhs.ems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.dao.ReturnVisitDAO;
import com.xhs.ems.service.ReturnVisitService;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午3:20:39
 */
@Service
public class ReturnVisitServiceImpl implements ReturnVisitService {

	@Autowired
	private ReturnVisitDAO returnVisitDAO;

	@Override
	public Grid getData(Parameter parameter) {
		return returnVisitDAO.getData(parameter);
	}

}
