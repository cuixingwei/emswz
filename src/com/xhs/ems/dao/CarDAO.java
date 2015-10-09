package com.xhs.ems.dao;

import java.util.List;

import com.xhs.ems.bean.Car;

/**
 * @author CUIXINGWEI
 * @datetime 2015年4月10日 下午2:25:20
 */
public interface CarDAO {
	/**
	 *@datetime 2015年4月10日 下午2:26:07
	 *@author CUIXINGWEI
	 *@return  车辆列表
	 */
	public List<Car> getData(String stationID);
}
