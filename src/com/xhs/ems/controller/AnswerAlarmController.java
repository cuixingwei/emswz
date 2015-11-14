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
import com.xhs.ems.service.AnswerAlarmService;

/**
 * @author 崔兴伟
 * @datetime 2015年4月15日 下午8:22:33
 */
@Controller
@RequestMapping(value = "/page/base")
public class AnswerAlarmController {
	private static final Logger logger = Logger
			.getLogger(AnswerAlarmController.class);

	@Autowired
	private AnswerAlarmService answerAlarmService;

	@RequestMapping(value = "/getAnswerAlarmDatas", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter) {
		logger.info("中心接警统计");
		return answerAlarmService.getData(parameter);
	}

	@RequestMapping(value = "/exportAnswerAlarmDatas", method = RequestMethod.GET)
	public void export(Parameter parameter, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("中心接警统计");
		response.setContentType("application/msexcel;charset=UTF-8");
		String title = "中心接警统计";
		String[] headers = new String[] { "接诊时间", "报警电话", "相关电话", "报警地址",
				"电话判断", "出诊分站", "派车时间", "调度员", "出诊结果", "接回人数" };
		String[] fields = new String[] { "answerAlarmTime", "alarmPhone",
				"relatedPhone", "siteAddress", "judgementOnPhone", "station",
				"sendCarTime", "dispatcher", "outResult", "takeBacks" };
		TableData td = ExcelUtils.createTableData(
				answerAlarmService.getData(parameter).getRows(),
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
