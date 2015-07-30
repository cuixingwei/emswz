package com.xhs.ems.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.StopReason;
import com.xhs.ems.dao.StopReasonDAO;
import com.xhs.ems.service.StopReasonService;

/**
 * @author CUIXINGWEI
 * @datetime 2015年4月10日 下午2:55:59
 */
@Service
public class StopReasonServiceImpl implements StopReasonService {
	@Autowired
	private StopReasonDAO stopReasonDAO;

	/**
	 * @author CUIXINGWEI
	 * @see com.xhs.ems.service.StopReasonService#getData()
	 * @datetime 2015年4月10日 下午2:55:59
	 */
	@Override
	public List<StopReason> getData() {
		return stopReasonDAO.getData();
	}

}
