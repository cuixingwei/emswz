package com.xhs.ems.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.EmptyReason;
import com.xhs.ems.dao.EmptyReasonDAO;
import com.xhs.ems.service.EmptyReasonService;

/**
 * @author 崔兴伟
 * @datetime 2015年4月14日 上午11:19:29
 */
@Service
public class EmptyReasonServiceImpl implements EmptyReasonService {
	@Autowired
	private EmptyReasonDAO emptyCarReason;

	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月14日 上午11:19:29
	 * @see com.xhs.ems.service.EmptyReasonService#getData()
	 */
	@Override
	public List<EmptyReason> getData() {
		return emptyCarReason.getData();
	}

}
