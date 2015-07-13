package com.xhs.ems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.dao.CallNoAcceptDAO;
import com.xhs.ems.service.CallNoAcceptService;

@Service
public class CallNoAcceptServiceImpl implements CallNoAcceptService {
	@Autowired
	private CallNoAcceptDAO callNoAcceptDAO;
	@Override
	public Grid getData(Parameter parameter) {
		return callNoAcceptDAO.getData(parameter);
	}

}
