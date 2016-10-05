package com.xhs.ems.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.HungReason;
import com.xhs.ems.dao.HungReasonDAO;
import com.xhs.ems.service.HungReasonService;

/**
 * @author 崔兴伟
 * @datetime 2015年4月15日 下午12:58:11
 */
@Service
public class HungReasonServiceImpl implements HungReasonService {
	@Autowired
	private HungReasonDAO hungReason;

	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月15日 下午12:58:11
	 * @see com.xhs.ems.service.HungReasonService#getData()
	 */
	@Override
	public List<HungReason> getData() {
		return hungReason.getData();
	}

}
