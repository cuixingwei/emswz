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
import com.xhs.ems.service.AcceptMarkService;

@Controller
@RequestMapping(value = "/page/base")
public class AcceptMarkController {
	private static final Logger logger = Logger
			.getLogger(AcceptMarkController.class);

	@Autowired
	private AcceptMarkService acceptMarkService;

	@RequestMapping(value = "/getAcceptMarkDatas", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter) {
		logger.info("受理备注查询");
		return acceptMarkService.getData(parameter);
	}

	@RequestMapping(value = "/exportAcceptMarkDatas", method = RequestMethod.GET)
	public @ResponseBody void exportAcceptMarkDatas(Parameter parameter,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("导出受理备注查询到excel");
		response.setContentType("application/msexcel;charset=UTF-8");

		String title = "受理备注查询";
		String[] headers = new String[] { "受理时间", "呼救电话", "调度员", "现场地址",
				"任务备注", "受理备注" };
		String[] fields = new String[] { "acceptTime", "callPhone",
				"dispatcher", "spotAddress", "taskRemark", "acceptRemark" };
		TableData td = ExcelUtils.createTableData(
				acceptMarkService.getData(parameter).getRows(),
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
