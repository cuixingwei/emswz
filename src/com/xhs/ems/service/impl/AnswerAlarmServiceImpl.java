package com.xhs.ems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.dao.AnswerAlarmDAO;
import com.xhs.ems.service.AnswerAlarmService;

/**
 * @author 崔兴伟
 * @datetime 2015年4月15日 下午8:21:03
 */
@Service
public class AnswerAlarmServiceImpl implements AnswerAlarmService {
	@Autowired
	private AnswerAlarmDAO answerAlarmDAO;

	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月15日 下午8:21:03
	 * @see com.xhs.ems.service.AnswerAlarmService#getData(com.xhs.ems.bean.Parameter)
	 */
	@Override
	public Grid getData(Parameter parameter) {
		return answerAlarmDAO.getData(parameter);
	}

}
