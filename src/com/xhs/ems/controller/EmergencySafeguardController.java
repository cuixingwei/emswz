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
import com.xhs.ems.service.EmergencySafeguardService;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午1:13:37
 */
@Controller
@RequestMapping(value = "page/base")
public class EmergencySafeguardController {
	private static final Logger logger = Logger
			.getLogger(EmergencySafeguardController.class);

	@Autowired
	private EmergencySafeguardService emergencySafeguardService;

	@RequestMapping(value = "/getEmergencySafeguardDatas", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter) {
		logger.info("急救保障明细");
		return emergencySafeguardService.getData(parameter);
	}

	@RequestMapping(value = "/exportEmergencySafeguard", method = RequestMethod.GET)
	public void export(Parameter parameter, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("导出急救保障明细到excel");
		response.setContentType("application/msexcel;charset=UTF-8");

		String title = "急救保障明细";
		String[] headers = new String[] { "日期", "性质", "事件", "地址", "里程", "医生",
				"护士", "司机", "保障时长" };
		String[] fields = new String[] { "date", "nature", "event", "address",
				"distance", "doctor", "nurse", "driver", "safeTime" };
		int spanCount = 1; // 需要合并的列数。从第1列开始到指定列。
		TableData td = ExcelUtils.createTableData(emergencySafeguardService
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
