package com.xhs.ems.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.CenterBusiness;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.dao.CenterBusinessDAO;
import com.xhs.ems.service.CenterBusinessService;

/**
 * @datetime 2015年11月12日 下午1:01:04
 * @author 崔兴伟
 */
@Service
public class CenterBusinessServiceImpl implements CenterBusinessService {
	@Autowired
	private CenterBusinessDAO centerBusinessDAO;

	@Override
	public List<CenterBusiness> getCenterBusinesseData(Parameter parameter) {
		return centerBusinessDAO.getCenterBusinesseData(parameter);
	}

}
