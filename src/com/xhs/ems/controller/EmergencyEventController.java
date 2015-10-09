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
import com.xhs.ems.service.EmergencyEventService;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午12:55:27
 */
@Controller
@RequestMapping(value = "page/base")
public class EmergencyEventController {
	private static final Logger logger = Logger
			.getLogger(EmergencyEventController.class);

	@Autowired
	private EmergencyEventService emergencyEventService;

	@RequestMapping(value = "/getEmergencyEventDatas", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter) {
		logger.info("突发时间统计");
		return emergencyEventService.getData(parameter);
	}

	@RequestMapping(value = "/exportEmergencyEvent", method = RequestMethod.GET)
	public void export(Parameter parameter,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("导出突发时间统计数据到excel");
		response.setContentType("application/msexcel;charset=UTF-8");

		String title = "突发时间统计";
		String[] headers = new String[] { "事件类型", "次数", "伤亡人数", "接回人数", "轻",
				"中", "重", "死亡", "里程", "反应时间", "总耗时" };
		String[] fields = new String[] { "eventType", "times", "casualties",
				"takeBacks", "light", "middle", "heavy", "death", "distance",
				"responseTime", "timeTotal" };
		int spanCount = 1; // 需要合并的列数。从第1列开始到指定列。
		TableData td = ExcelUtils.createTableData(emergencyEventService
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
