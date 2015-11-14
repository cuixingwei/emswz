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
import com.xhs.ems.service.StationTransforDetailService;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午4:54:39
 */
@Controller
@RequestMapping(value = "page/base")
public class StationTransforDetailController {
	private static final Logger logger = Logger
			.getLogger(StationTransforDetailController.class);

	@Autowired
	private StationTransforDetailService stationTransforDetailService;

	@RequestMapping(value = "/getStationTransforDetailDatas", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter) {
		logger.info("医院转诊明细");
		return stationTransforDetailService.getData(parameter);
	}

	@RequestMapping(value = "/exportStationTransforDetail", method = RequestMethod.GET)
	public void export(Parameter parameter, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("导出医院转诊明细到excel");
		response.setContentType("application/msexcel;charset=UTF-8");

		String title = "医院转诊明细";
		String[] headers = new String[] { "日期", "病人姓名", "年龄", "性别", "诊断",
				"出诊分站", "送达科室", "现场地址", "里程", "出诊结果" };
		String[] fields = new String[] { "date", "patientName", "age",
				"gender", "diagnose", "outCallAddress", "sendClass",
				"spotAddress", "distance", "outResult" };
		int spanCount = 1; // 需要合并的列数。从第1列开始到指定列。
		TableData td = ExcelUtils.createTableData(stationTransforDetailService
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
