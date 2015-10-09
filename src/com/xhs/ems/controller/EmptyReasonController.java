package com.xhs.ems.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xhs.ems.bean.EmptyReason;
import com.xhs.ems.service.EmptyReasonService;

/**
 * @author 崔兴伟
 * @datetime 2015年4月14日 上午11:20:51
 */
@Controller
@RequestMapping(value = "page/base")
public class EmptyReasonController {
	private static final Logger logger = Logger
			.getLogger(EmptyReasonController.class);
	@Autowired
	private EmptyReasonService emptyCarReasonService;

	@RequestMapping(value = "/getEmptyReasons", method = RequestMethod.GET)
	public @ResponseBody List<EmptyReason> getStation() {
		logger.info("获取放空车原因列表");
		return emptyCarReasonService.getData();
	}

}
