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
import com.xhs.ems.service.AcceptTimeService;

@Controller
@RequestMapping(value = "/page/base")
public class AcceptTimeController {
	private static final Logger logger = Logger
			.getLogger(AcceptTimeController.class);
	@Autowired
	private AcceptTimeService acceptTimeService;

	@RequestMapping(value = "/getAcceptTimeDatas", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter) {
		logger.info("受理时间统计");
		return acceptTimeService.getData(parameter);
	}

	@RequestMapping(value = "/exportAcceptTimeDatas", method = RequestMethod.GET)
	public @ResponseBody void exportAcceptMarkDatas(Parameter parameter,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("导出受理时间统计到excel");
		response.setContentType("application/msexcel;charset=UTF-8");

		String title = "受理时间统计";
		String[] headers = new String[] { "调度员", "平均摘机时长(秒)", "平均派车时长(秒)",
				"平均受理时长(秒)", "就绪时长", "离席时长" };
		String[] fields = new String[] { "dispatcher", "averageOffhookTime",
				"averageOffSendCar", "averageAccept", "readyTime", "leaveTime" };
		TableData td = ExcelUtils.createTableData(
				acceptTimeService.getData(parameter).getRows(),
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
