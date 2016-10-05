package com.xhs.ems.service;

import java.util.List;

import com.xhs.ems.bean.Car;

/**
 * @author CUIXINGWEI
 * @datetime 2015年4月10日 下午2:27:08
 */
public interface CarService {
	/**
	 *@datetime 2015年4月10日 下午2:27:31
	 *@author CUIXINGWEI
	 *@return 车辆列表
	 */
	public List<Car> getData(String stationID);
}
