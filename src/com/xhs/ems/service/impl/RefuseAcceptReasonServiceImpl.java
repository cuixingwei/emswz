package com.xhs.ems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.dao.RefuseAcceptReasonDAO;
import com.xhs.ems.service.RefuseAcceptReasonService;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午3:10:56
 */
@Service
public class RefuseAcceptReasonServiceImpl implements RefuseAcceptReasonService {

	@Autowired
	private RefuseAcceptReasonDAO refuseAcceptReasonDAO;

	@Override
	public Grid getData(Parameter parameter) {
		return refuseAcceptReasonDAO.getData(parameter);
	}

}
