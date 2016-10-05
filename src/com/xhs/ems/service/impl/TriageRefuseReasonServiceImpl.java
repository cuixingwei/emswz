package com.xhs.ems.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.TriageRefuseReason;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.dao.TriageRefuseReasonDAO;
import com.xhs.ems.service.TriageRefuseReasonService;

/**
 * @datetime 2015年11月14日 下午2:06:36
 * @author 崔兴伟
 */
@Service
public class TriageRefuseReasonServiceImpl implements TriageRefuseReasonService {
	@Autowired
	private TriageRefuseReasonDAO triageRefuseReasonDAO;

	@Override
	public List<TriageRefuseReason> getData(Parameter parameter) {
		return triageRefuseReasonDAO.getData(parameter);
	}

}
