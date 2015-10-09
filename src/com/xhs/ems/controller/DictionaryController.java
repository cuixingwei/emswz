package com.xhs.ems.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xhs.ems.bean.Dictionary;
import com.xhs.ems.service.DictionaryService;

/**
 * @author 崔兴伟
 * @datetime 2015年10月9日 下午2:02:51
 */
@Controller
@RequestMapping(value = "page/base")
public class DictionaryController {

	@Autowired
	private DictionaryService dictionaryService;

	@RequestMapping(value = "/getTaskResult", method = RequestMethod.GET)
	public @ResponseBody List<Dictionary> getData() {
		return dictionaryService.getTaskResult();
	}
	
	@RequestMapping(value = "/getArea", method = RequestMethod.GET)
	public @ResponseBody List<Dictionary> getArea() {
		return dictionaryService.getArea();
	}
}
