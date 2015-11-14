package com.xhs.ems.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.OutResult;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.dao.OutResultDAO;
import com.xhs.ems.service.OutResultService;

/**
 * @datetime 2015年11月14日 下午1:32:57
 * @author 崔兴伟
 */
@Service
public class OutResultServiceImpl implements OutResultService {
	@Autowired
	private OutResultDAO outResultDAO;

	@Override
	public List<OutResult> getData(Parameter parameter) {
		return outResultDAO.getData(parameter);
	}

}
