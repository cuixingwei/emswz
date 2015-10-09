package com.xhs.ems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.dao.SubstationLateVisitDAO;
import com.xhs.ems.service.SubstationLateVisitService;

/**
 * @author 崔兴伟
 * @datetime 2015年4月16日 下午8:03:45
 */
@Service
public class SubstationLateVisitServiceImpl implements
		SubstationLateVisitService {
	@Autowired
	private SubstationLateVisitDAO substationLateVisitDAO;

	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月16日 下午8:03:45
	 * @see com.xhs.ems.service.SubstationLateVisitService#getData(com.xhs.ems.bean.Parameter)
	 */
	@Override
	public Grid getData(Parameter parameter) {
		return substationLateVisitDAO.getData(parameter);
	}

}
