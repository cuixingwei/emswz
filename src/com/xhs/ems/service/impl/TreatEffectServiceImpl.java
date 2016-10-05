package com.xhs.ems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.dao.TreatEffectDAO;
import com.xhs.ems.service.TreatEffectService;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午5:32:54
 */
@Service
public class TreatEffectServiceImpl implements TreatEffectService {

	@Autowired
	private TreatEffectDAO treatEffectDAO;

	@Override
	public Grid getData(Parameter parameter) {
		return treatEffectDAO.getData(parameter);
	}

}
