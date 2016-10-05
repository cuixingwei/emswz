package com.xhs.ems.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xhs.ems.bean.StopReason;
import com.xhs.ems.service.StopReasonService;

/**
 * @author CUIXINGWEI
 * @datetime 2015年4月10日 下午3:04:30
 */
@Controller
@RequestMapping(value = "page/base")
public class StopReasonController {
	private static final Logger logger = Logger
			.getLogger(StopReasonController.class);
	@Autowired
	private StopReasonService stopReasonService;

	@RequestMapping(value = "/getStopReasons", method = RequestMethod.GET)
	public @ResponseBody List<StopReason> getStation() {
		logger.info("获取中止任务原因列表");
		return stopReasonService.getData();
	}
}
