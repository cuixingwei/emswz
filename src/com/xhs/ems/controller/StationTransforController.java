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
import com.xhs.ems.service.StationTransforService;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午4:44:15
 */
@Controller
@RequestMapping(value = "page/base")
public class StationTransforController {
	private static final Logger logger = Logger
			.getLogger(StationTransforController.class);

	@Autowired
	private StationTransforService stationTransforService;

	@RequestMapping(value = "/getStationTransforDatas", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter) {
		logger.info("医院转诊统计");
		return stationTransforService.getData(parameter);
	}

	@RequestMapping(value = "/exportStationTransfor", method = RequestMethod.GET)
	public void export(Parameter parameter, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("导出医院转诊统计到excel");
		response.setContentType("application/msexcel;charset=UTF-8");

		String title = "医院转诊统计";
		String[] headers = new String[] { "区域", "医院名称", "出诊数", "接回数", "里程合计",
				"耗时" };
		String[] fields = new String[] { "area", "station", "outCalls",
				"takeBacks", "distance", "time" };
		int spanCount = 1; // 需要合并的列数。从第1列开始到指定列。
		TableData td = ExcelUtils.createTableData(stationTransforService
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
