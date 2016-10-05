package com.xhs.ems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.dao.EmptyCarReasonDAO;
import com.xhs.ems.service.EmptyCarReasonService;

/**
 * @author 崔兴伟
 * @datetime 2015年4月14日 下午2:25:44
 */
@Service
public class EmptyCarReasonServiceImpl implements EmptyCarReasonService {
	@Autowired
	private EmptyCarReasonDAO emptyCarReasonDAO;

	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月14日 下午2:25:44
	 * @see com.xhs.ems.service.EmptyCarReasonService#getData(com.xhs.ems.bean.Parameter)
	 */
	@Override
	public Grid getData(Parameter parameter) {
		return emptyCarReasonDAO.getData(parameter);
	}

}
