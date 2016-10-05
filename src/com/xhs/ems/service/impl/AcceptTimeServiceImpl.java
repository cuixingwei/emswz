package com.xhs.ems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.dao.AcceptTimeDAO;
import com.xhs.ems.service.AcceptTimeService;

@Service
public class AcceptTimeServiceImpl implements AcceptTimeService {
	
	@Autowired
	private AcceptTimeDAO acceptTimeDAO;
	@Override
	public Grid getData(Parameter parameter) {
		return acceptTimeDAO.getData(parameter);
	}

}
