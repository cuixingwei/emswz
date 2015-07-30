package com.xhs.ems.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.Car;
import com.xhs.ems.dao.CarDAO;
import com.xhs.ems.service.CarService;

/**
 * @author CUIXINGWEI
 * @datetime 2015年4月10日 下午2:28:17
 */
@Service
public class CarServiceImpl implements CarService {

	@Autowired
	private CarDAO carDAO;

	/**
	 * @author CUIXINGWEI
	 * @see com.xhs.ems.service.CarService#getData()
	 * @datetime 2015年4月10日 下午2:28:17
	 */
	@Override
	public List<Car> getData(String stationID) {
		return carDAO.getData(stationID);
	}

}
