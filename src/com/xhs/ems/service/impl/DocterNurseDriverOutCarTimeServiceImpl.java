package com.xhs.ems.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.DocterNurseDriverOutCarTime;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.dao.DocterNurseDriverOutCarTimeDAO;
import com.xhs.ems.service.DocterNurseDriverOutCarTimeService;

/**
 * @datetime 2015年11月14日 下午4:00:08
 * @author 崔兴伟
 */
@Service
public class DocterNurseDriverOutCarTimeServiceImpl implements
		DocterNurseDriverOutCarTimeService {
	@Autowired
	private DocterNurseDriverOutCarTimeDAO docterNurseDriverOutCarTimeDAO;

	@Override
	public List<DocterNurseDriverOutCarTime> getData(Parameter parameter) {
		return docterNurseDriverOutCarTimeDAO.getData(parameter);
	}

}
