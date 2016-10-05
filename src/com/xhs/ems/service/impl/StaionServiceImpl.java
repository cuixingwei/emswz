package com.xhs.ems.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.Station;
import com.xhs.ems.dao.StationDAO;
import com.xhs.ems.service.StationService;

/**
 * @author CUIXINGWEI
 * @datetime 2015年3月30日 下午5:19:12
 */
@Service
public class StaionServiceImpl implements StationService {
	@Autowired
	private StationDAO stationDAO;

	/** 
	 * @author CUIXINGWEI
	 * @see com.xhs.ems.service.StationService#getData()
	 * @datetime 2015年3月30日 下午5:19:12
	 */
	@Override
	public List<Station> getData() {
		return stationDAO.getData();
	}

}
