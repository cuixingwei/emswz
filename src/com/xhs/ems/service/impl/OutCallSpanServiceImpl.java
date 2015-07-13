package com.xhs.ems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.dao.OutCallSpanDAO;
import com.xhs.ems.service.OutCallSpanService;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午2:38:43
 */
@Service
public class OutCallSpanServiceImpl implements OutCallSpanService {

	@Autowired
	private OutCallSpanDAO outCallSpanDAO;

	@Override
	public Grid getData(Parameter parameter) {
		return outCallSpanDAO.getData(parameter);
	}

}
