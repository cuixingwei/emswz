package com.xhs.ems.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.OutOfThree;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.dao.OutOfThreeDAO;
import com.xhs.ems.service.OutOfThreeService;

/**
 * @datetime 2015年11月14日 下午3:25:55
 * @author 崔兴伟
 */
@Service
public class OutOfThreeServiceImpl implements OutOfThreeService {

	@Autowired
	private OutOfThreeDAO outOfThreeDAO;

	@Override
	public List<OutOfThree> getData(Parameter parameter) {
		return outOfThreeDAO.getData(parameter);
	}

}
