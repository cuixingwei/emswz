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
import com.xhs.ems.service.HistoryEventService;

/**
 * @datetime 2016年7月27日 下午3:21:40
 * @author 崔兴伟
 */
@Controller
@RequestMapping(value = "/page/base")
public class HistoryEventController {
	private static final Logger logger = Logger
			.getLogger(HistoryEventController.class);

	@Autowired
	private HistoryEventService historyEventService;

	@RequestMapping(value = "/getHistoryEventDatas", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter) {
		logger.info("历史事件查询");
		return historyEventService.getData(parameter);
	}

	@RequestMapping(value = "/exportHistoryEventDatas", method = RequestMethod.GET)
	public void export(Parameter parameter, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("导出历史事件查询数据到excel");
		response.setContentType("application/msexcel;charset=UTF-8");

		String title = "历史事件";
		String[] headers = new String[] { "事件名称", "呼救电话", "受理时刻", "事件类型", "联动来源",
				"调度员", "受理次数" ,"任务次数","病历个数"};
		String[] fields = new String[] { "eventName", "alarmPhone", "acceptTime",
				"eventType", "eventSource", "dispatcher", "acceptNumbers","taskNumbers","caseNumbers" };
		TableData td = ExcelUtils.createTableData(
				historyEventService.getData(parameter).getRows(),
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
