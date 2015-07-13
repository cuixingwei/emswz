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
import com.xhs.ems.service.DriverOutCallService;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 上午9:25:19
 */
@Controller
@RequestMapping(value = "page/base")
public class DriverOutCallController {
	private static final Logger logger = Logger
			.getLogger(DriverOutCallController.class);

	@Autowired
	private DriverOutCallService driverOutCallService;

	@RequestMapping(value = "/getDriverOutCallDatas", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter) {
		logger.info("司机出诊表");
		return driverOutCallService.getData(parameter);
	}

	@RequestMapping(value = "/exportDriverOutCall", method = RequestMethod.GET)
	public void export(Parameter parameter, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("导出司机出诊表到excel");
		response.setContentType("application/msexcel;charset=UTF-8");

		String title = "司机出诊表";
		String[] headers = new String[] { "姓名", "出诊数", "接回数", "里程", "平均反应时间",
				"出诊用时合计", "平均耗时" };
		String[] fields = new String[] { "name", "outCalls", "takeBacks",
				"distance", "averageResponseTime", "outCallTimeTotal",
				"averageTime" };
		int spanCount = 1; // 需要合并的列数。从第1列开始到指定列。
		TableData td = ExcelUtils.createTableData(
				driverOutCallService.getData(parameter).getRows(),
				ExcelUtils.createTableHeader(headers, spanCount), fields);
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
