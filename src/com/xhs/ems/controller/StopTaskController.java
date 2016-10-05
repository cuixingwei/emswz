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
import com.xhs.ems.service.StopTaskService;

/**
 * @author CUIXINGWEI
 * @datetime 2015年4月10日 下午3:16:51
 */
@Controller
@RequestMapping(value = "/page/base")
public class StopTaskController {
	private static final Logger logger = Logger
			.getLogger(StopTaskController.class);
	@Autowired
	private StopTaskService stopTaskService;

	@RequestMapping(value = "/getStopTaskDatas", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter) {
		logger.info("中止任务信息");
		return stopTaskService.getData(parameter);
	}

	@RequestMapping(value = "/exportStopTaskDatas", method = RequestMethod.GET)
	public @ResponseBody void export(Parameter parameter,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("导出中止任务信息到excel");
		response.setContentType("application/msexcel;charset=UTF-8");

		String title = "中止任务信息";
		String[] headers = new String[] { "受理时间", "患者地址", "呼救电话", "调度员",
				"车辆编码", "出车时间", "空跑时长", "分站", "中止原因", "备注说明" };
		String[] fields = new String[] { "acceptTime", "sickAddress", "phone",
				"dispatcher", "carCode", "drivingTime", "emptyRunTime",
				"staion", "stopReason", "remark" };
		TableData td = ExcelUtils.createTableData(
				stopTaskService.getData(parameter).getRows(),
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
