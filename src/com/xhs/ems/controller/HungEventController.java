package com.xhs.ems.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.bean.easyui.SessionInfo;
import com.xhs.ems.excelTools.ExcelUtils;
import com.xhs.ems.excelTools.JsGridReportBase;
import com.xhs.ems.excelTools.TableData;
import com.xhs.ems.service.HungEventService;

/**
 * @author 崔兴伟
 * @datetime 2015年4月15日 下午12:21:59
 */
@Controller
@RequestMapping(value = "/page/base")
public class HungEventController {
	private static final Logger logger = Logger
			.getLogger(HungEventController.class);
	@Autowired
	private HungEventService hungEventService;

	@RequestMapping(value = "gethungEventData", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter) {
		logger.info("挂起事件流水统计");
		return hungEventService.getData(parameter);
	}

	@RequestMapping(value = "exporthungEventData", method = RequestMethod.GET)
	public void export(Parameter parameter, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("导出挂起事件流水统计到Excel");
		response.setContentType("application/msexcel;charset=UTF-8");

		String title = "挂起事件流水统计";
		String[] headers = new String[] { "事件名称", "受理类型", "操作人", "挂起时刻",
				"挂起原因", "结束时刻", "时长", "区域", "出诊类型" };
		String[] fields = new String[] { "eventName", "acceptType",
				"dispatcher", "hungTime", "hungReason", "endTime", "hungtimes",
				"area", "eventType" };
		TableData td = ExcelUtils.createTableData(
				hungEventService.getData(parameter).getRows(),
				ExcelUtils.createTableHeader(headers), fields);
		JsGridReportBase report = new JsGridReportBase(request, response);

		HttpSession session = request.getSession();
		SessionInfo sessionInfo = (SessionInfo) session
				.getAttribute("sessionInfo");
		if (null != sessionInfo) {
			report.exportToExcel(title, sessionInfo.getUser().getName(), td,
					parameter);
		} else {
			report.exportToExcel(title, "", td, parameter);
		}
	}

}
