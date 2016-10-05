package com.xhs.ems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.dao.DispatcherWorkloadDAO;
import com.xhs.ems.service.DispatcherWorkloadService;

/**
 * @author 崔兴伟
 * @datetime 2015年8月18日 上午9:50:19
 */
@Service
public class DispatcherWorkloadServiceImpl implements DispatcherWorkloadService {
	@Autowired
	private DispatcherWorkloadDAO dispatcherWorkloadDAO;

	@Override
	public Grid getData(Parameter parameter) {
		return dispatcherWorkloadDAO.getData(parameter);
	}

}
