package com.xhs.ems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.dao.TeleBookDAO;
import com.xhs.ems.service.TeleBookService;

@Service
public class TeleBookServiceImpl implements TeleBookService {

	@Autowired
	private TeleBookDAO teleBookDAO;
	@Override
	public Grid getData(Parameter parameter) {
		return teleBookDAO.getData(parameter);
	}

}
