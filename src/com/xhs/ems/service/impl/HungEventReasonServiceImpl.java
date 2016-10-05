package com.xhs.ems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.dao.HungEventReasonDAO;
import com.xhs.ems.service.HungEventReasonService;

/**
 * @author 崔兴伟
 * @datetime 2015年4月15日 下午2:11:22
 */
@Service
public class HungEventReasonServiceImpl implements HungEventReasonService {
	@Autowired
	private HungEventReasonDAO hungEventReasonDAO;

	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月15日 下午2:11:22
	 * @see com.xhs.ems.service.HungEventReasonService#getData(com.xhs.ems.bean.Parameter)
	 */
	@Override
	public Grid getData(Parameter parameter) {
		return hungEventReasonDAO.getData(parameter);
	}

}
