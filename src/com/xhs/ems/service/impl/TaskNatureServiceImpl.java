package com.xhs.ems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.dao.TaskNatureDAO;
import com.xhs.ems.service.TaskNatureService;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午5:06:27
 */
@Service
public class TaskNatureServiceImpl implements TaskNatureService {

	@Autowired
	private TaskNatureDAO taskNatureDAO;

	@Override
	public Grid getData(Parameter parameter) {
		return taskNatureDAO.getData(parameter);
	}

}
