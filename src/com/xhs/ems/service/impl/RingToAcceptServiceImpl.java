package com.xhs.ems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.dao.RingToAcceptDAO;
import com.xhs.ems.service.RingToAcceptService;
@Service
public class RingToAcceptServiceImpl implements RingToAcceptService {
	
	@Autowired
	private RingToAcceptDAO ringToAcceptDAO;
	
	@Override
	public Grid getData(Parameter parameter) {
		return ringToAcceptDAO.getData(parameter);
	}

}
