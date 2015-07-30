package com.xhs.ems.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xhs.ems.bean.HungReason;
import com.xhs.ems.service.HungReasonService;

/**
 * @author 崔兴伟
 * @datetime 2015年4月15日 下午12:59:38
 */
@Controller
@RequestMapping(value = "page/base")
public class HungReasonController {
	private static final Logger logger = Logger
			.getLogger(HungReasonController.class);
	@Autowired
	private HungReasonService hungReasonService;

	@RequestMapping(value = "/getHungReasons", method = RequestMethod.GET)
	public @ResponseBody List<HungReason> getStation() {
		logger.info("获取挂起事件原因列表");
		return hungReasonService.getData();
	}

}
