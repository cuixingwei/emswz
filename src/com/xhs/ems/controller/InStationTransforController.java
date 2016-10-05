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
import com.xhs.ems.service.InStationTransforService;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午1:40:26
 */
@Controller
@RequestMapping(value = "page/base")
public class InStationTransforController {

	private static final Logger logger = Logger
			.getLogger(InStationTransforController.class);

	@Autowired
	private InStationTransforService inStationTransforService;

	@RequestMapping(value = "/getInStationTransforDatas", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter) {
		logger.info("院内转运统计");
		return inStationTransforService.getData(parameter);
	}

	@RequestMapping(value = "/exportInStationTransfor", method = RequestMethod.GET)
	public void export(Parameter parameter, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("导出院内转运统计到excel");
		response.setContentType("application/msexcel;charset=UTF-8");

		String title = "院内转运统计";
		String[] headers = new String[] { "出诊分站", "转运次数", "里程", "转运耗时" };
		String[] fields = new String[] { "station", "transforTimes",
				"distance", "transforTime" };
		int spanCount = 1; // 需要合并的列数。从第1列开始到指定列。
		TableData td = ExcelUtils.createTableData(inStationTransforService
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
