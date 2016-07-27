package com.xhs.ems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.dao.HistoryEventDAO;
import com.xhs.ems.service.HistoryEventService;

/**
 * @datetime 2016年7月27日 下午3:15:28
 * @author 崔兴伟
 */
@Service
public class HistoryEventSeriveImple implements HistoryEventService {

	@Autowired
	private HistoryEventDAO historyEventDAO;
	@Override
	public Grid getData(Parameter parameter) {
		return historyEventDAO.getData(parameter);
	}

}
