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
import com.xhs.ems.service.EmptyCarService;

/**
 * @author 崔兴伟
 * @datetime 2015年4月14日 上午8:58:13
 */
@Controller
@RequestMapping(value = "/page/base")
public class EmptyCarController {
	private static final Logger logger = Logger
			.getLogger(EmptyCarController.class);
	@Autowired
	private EmptyCarService emptyCarService;

	@RequestMapping(value = "getEmptyCarData", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter) {
		logger.info("放空车统计");
		return emptyCarService.getData(parameter);
	}

	@RequestMapping(value = "exportEmptyCarData", method = RequestMethod.GET)
	public void export(Parameter parameter, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("导出放空车统计到Excel");
		response.setContentType("application/msexcel;charset=UTF-8");

		String title = "放空车统计";
		String[] headers = new String[] { "受理时间", "患者地址", "调度员", "空跑时间", "空炮原因" };
		String[] fields = new String[] { "acceptTime", "sickAddress",
				"dispatcher", "emptyRunTimes", "emptyReason" };
		TableData td = ExcelUtils.createTableData(
				emptyCarService.getData(parameter).getRows(),
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
