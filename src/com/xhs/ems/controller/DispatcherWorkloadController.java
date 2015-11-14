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
import com.xhs.ems.service.DispatcherWorkloadService;

/**
 * @author 崔兴伟
 * @datetime 2015年8月18日 上午10:03:42
 */
@Controller
@RequestMapping(value = "page/base")
public class DispatcherWorkloadController {
	private Logger logger = Logger.getLogger(DispatcherWorkloadController.class);
	@Autowired
	private DispatcherWorkloadService dispatcherWorkloadService;

	@RequestMapping(value = "/getDispatcherWorkloadDatas", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter) {
		logger.info("调度员工作统计");
		return dispatcherWorkloadService.getData(parameter);
	}

	@RequestMapping(value = "/exportDispatcherWorkload", method = RequestMethod.GET)
	public void export(Parameter parameter, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("导出调度员工作统计数据到excel");
		response.setContentType("application/msexcel;charset=UTF-8");

		String title = "调度员工作统计";
		String[] headers = new String[] { "调度员", "电话总数", "接入电话", "打出电话",
				"有效出车", "正常完成", "空车", "中止任务", "拒绝出车", "接回病人数", "分诊数" };
		String[] fields = new String[] { "dispatcher", "numbersOfPhone",
				"inOfPhone", "outOfPhone", "numbersOfSendCar",
				"numbersOfNormalSendCar", "emptyCar", "numbersOfStopTask",
				"refuseCar", "takeBacks", "triageNumber" };
		int spanCount = 1; // 需要合并的列数。从第1列开始到指定列。
		TableData td = ExcelUtils.createTableData(dispatcherWorkloadService
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
