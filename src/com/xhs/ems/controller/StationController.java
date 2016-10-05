package com.xhs.ems.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xhs.ems.bean.Station;
import com.xhs.ems.service.StationService;

/**
 * @author CUIXINGWEI
 * @datetime 2015年3月30日 下午5:10:42
 */
@Controller
@RequestMapping(value = "page/base")
public class StationController {
	private static final Logger logger = Logger
			.getLogger(StationController.class);
	@Autowired
	private StationService staionService;

	@RequestMapping(value = "/getStations", method = RequestMethod.GET)
	public @ResponseBody List<Station> getStation() {
		logger.info("获取分站列表");
		return staionService.getData();
	}
}
