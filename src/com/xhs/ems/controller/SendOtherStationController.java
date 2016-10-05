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
import com.xhs.ems.service.SendOtherStationService;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午3:44:05
 */
@Controller
@RequestMapping(value = "page/base")
public class SendOtherStationController {
	private static final Logger logger = Logger
			.getLogger(SendOtherStationController.class);

	@Autowired
	private SendOtherStationService sendOtherStationService;

	@RequestMapping(value = "/getSendOtherStationDatas", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter) {
		logger.info("其他医院调度分诊");
		return sendOtherStationService.getData(parameter);
	}

	@RequestMapping(value = "/exportSendOtherStation", method = RequestMethod.GET)
	public void export(Parameter parameter, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("导出其他医院调度分诊到excel");
		response.setContentType("application/msexcel;charset=UTF-8");

		String title = "其他医院调度分诊";
		String[] headers = new String[] { "医院名称", "受理次数", "接回", "未受理次数" };
		String[] fields = new String[] { "station", "acceptTimes", "takeBacks",
				"noAcceptTimes" };
		int spanCount = 1; // 需要合并的列数。从第1列开始到指定列。
		TableData td = ExcelUtils.createTableData(sendOtherStationService
				.getData(parameter).getRows(), ExcelUtils.createTableHeader(
				headers, spanCount), fields);
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
