package com.xhs.ems.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xhs.ems.bean.PauseReason;
import com.xhs.ems.service.PauseReasonService;

/**
 * @author 崔兴伟
 * @datetime 2015年4月13日 下午5:19:36
 */
@Controller
@RequestMapping(value = "page/base")
public class PauseReasonController {
	private static final Logger logger = Logger.getLogger(PauseReasonController.class);
	@Autowired
	private PauseReasonService pauseReasonService;

	@RequestMapping(value = "/getPauseReason", method = RequestMethod.GET)
	public @ResponseBody List<PauseReason> getStation() {
		logger.info("车辆暂停调用原因列表");
		return pauseReasonService.getData();
	}

}
