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
import com.xhs.ems.service.StationTaskTypeService;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午4:26:20
 */
@Controller
@RequestMapping(value = "page/base")
public class StationTaskTypeController {
	private static final Logger logger = Logger
			.getLogger(StationTaskTypeController.class);

	@Autowired
	private StationTaskTypeService stationTaskTypeService;

	@RequestMapping(value = "/getStationTaskTypeDatas", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter) {
		logger.info("站点任务类型统计");
		return stationTaskTypeService.getData(parameter);
	}

	@RequestMapping(value = "/exportStationTaskType", method = RequestMethod.GET)
	public void export(Parameter parameter, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("导出站点任务类型统计到excel");
		response.setContentType("application/msexcel;charset=UTF-8");

		String title = "站点任务类型统计";
		String[] parents = new String[] { "", "总次数", "现场急救", "医院转诊" };// 父表头数组
		String[][] children = new String[][] { new String[] { "站点名称" },
				new String[] { "次数", "里程", "平均反应时间", "平均耗时" },
				new String[] { "次数", "里程", "平均反应时间", "平均耗时" },
				new String[] { "次数", "里程", "平均反应时间", "平均耗时" } };// 子表头数组
		String[] fields = new String[] { "station", "ztimes", "zdistance",
				"zaverageResponseTime", "zaverageTime", "xctimes",
				"xcdistance", "xcaverageResponseTime", "xcaverageTime",
				"yytimes", "yydistance", "yyaverageResponseTime",
				"yyaverageTime" };
		TableData td = ExcelUtils.createTableData(stationTaskTypeService
				.getData(parameter).getRows(), ExcelUtils.createTableHeader(
				parents, children), fields);
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
