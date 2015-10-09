package com.xhs.ems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.dao.DocterNurseDriverDAO;
import com.xhs.ems.service.DocterNurseDriverService;

/**
 * @author 崔兴伟
 * @datetime 2015年5月21日 下午10:02:07
 */
@Service
public class DocterNurseDriverServiceImpl implements DocterNurseDriverService {
	@Autowired
	private DocterNurseDriverDAO docterNurseDriverDAO;

	@Override
	public Grid getData(Parameter parameter) {
		return docterNurseDriverDAO.getData(parameter);
	}

}
