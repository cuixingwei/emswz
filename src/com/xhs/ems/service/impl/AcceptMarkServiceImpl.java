package com.xhs.ems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.dao.AcceptMarkDAO;
import com.xhs.ems.service.AcceptMarkService;

@Service
public class AcceptMarkServiceImpl implements AcceptMarkService {
	
	@Autowired
	private AcceptMarkDAO acceptMarkDAO;

	@Override
	public Grid getData(Parameter parameter) {
		
		return acceptMarkDAO.getData(parameter);
	}

}
