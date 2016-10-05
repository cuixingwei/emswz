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
import com.xhs.ems.service.StateChangeService;

/**
 * @author CUIXINGWEI
 * @date 2015年3月30日
 */
@Controller
@RequestMapping(value = "/page/base")
public class StateChangeController {
	private static final Logger logger = Logger
			.getLogger(StateChangeController.class);

	@Autowired
	private StateChangeService stateChangeService;

	@RequestMapping(value = "/getStateChangeDatas", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter) {
		logger.info("状态变化统计");
		return stateChangeService.getData(parameter);
	}

	@RequestMapping(value = "/exportStateChangeDatas", method = RequestMethod.GET)
	public @ResponseBody void exportStateChangeDatas(Parameter parameter,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("导出状态变化统计到excel");
		response.setContentType("application/msexcel;charset=UTF-8");

		String title = "状态变化统计";
		String[] headers = new String[] { "调度员", "座席号", "座席状态", "开始时间", "结束时间" };
		String[] fields = new String[] { "dispatcher", "seatCode", "seatState",
				"startTime", "endTime" };
		int spanCount = 1; // 需要合并的列数。从第1列开始到指定列。
		TableData td = ExcelUtils.createTableData(
				stateChangeService.getData(parameter).getRows(),
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
