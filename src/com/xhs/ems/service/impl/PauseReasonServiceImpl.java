package com.xhs.ems.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.PauseReason;
import com.xhs.ems.dao.PauseReasonDAO;
import com.xhs.ems.service.PauseReasonService;

/**
 * @author 崔兴伟
 * @datetime 2015年4月13日  下午5:17:14
 */
@Service
public class PauseReasonServiceImpl implements PauseReasonService {
	@Autowired
	private PauseReasonDAO pauseReasonDAO;
	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月13日  下午5:17:14
	 * @see com.xhs.ems.service.PauseReasonService#getData()
	 */
	@Override
	public List<PauseReason> getData() {
		return pauseReasonDAO.getData();
	}

}
