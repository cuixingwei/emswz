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
import com.xhs.ems.service.TaskNatureService;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午5:09:44
 */
@Controller
@RequestMapping(value = "page/base")
public class TaskNatureController {
	private static final Logger logger = Logger
			.getLogger(TaskNatureController.class);

	@Autowired
	private TaskNatureService taskNatureService;

	@RequestMapping(value = "/getTaskNatureDatas", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter) {
		logger.info("任务性质统计");
		return taskNatureService.getData(parameter);
	}

	@RequestMapping(value = "/exportTaskNature", method = RequestMethod.GET)
	public void export(Parameter parameter, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("导出任务性质统计到excel");
		response.setContentType("application/msexcel;charset=UTF-8");

		String title = "任务性质统计";
		String[] headers = new String[] { "出诊类型", "次数", "接回数", "接回率", "里程",
				"平均反应时间", "平均用时", "空车", "拒绝入院", "死亡", "市区", "万州区", "其他" };
		String[] fields = new String[] { "outCallType", "times", "takeBacks",
				"takeBackRate", "distance", "averageResponseTime",
				"averageTime", "emptyCars", "refuseToHospitals", "deaths",
				"shiqu", "wanzhou", "others" };
		int spanCount = 1; // 需要合并的列数。从第1列开始到指定列。
		TableData td = ExcelUtils.createTableData(
				taskNatureService.getData(parameter).getRows(),
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
