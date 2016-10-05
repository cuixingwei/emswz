package com.xhs.ems.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xhs.ems.bean.Car;
import com.xhs.ems.service.CarService;

/**
 * @author CUIXINGWEI
 * @datetime 2015年4月10日 下午2:35:42
 */
@Controller
@RequestMapping(value = "page/base")
public class CarController {
	private static final Logger logger = Logger.getLogger(CarController.class);
	@Autowired
	private CarService carService;

	@RequestMapping(value = "/getCars", method = RequestMethod.GET)
	public @ResponseBody List<Car> getStation(String id) {
		logger.info("获取车辆列表");
		return carService.getData(id);
	}
}
