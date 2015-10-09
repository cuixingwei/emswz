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
import com.xhs.ems.service.RingToAcceptService;

@Controller
@RequestMapping(value = "/page/base")
public class RingToAcceptController {
	private static final Logger logger = Logger
			.getLogger(RingToAcceptController.class);

	@Autowired
	private RingToAcceptService ringToAcceptService;

	@RequestMapping(value = "/getRingToAcceptDatas", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter) {
		logger.info("响铃到接听大于X秒");
		return ringToAcceptService.getData(parameter);
	}

	@RequestMapping(value = "/exportRingToAcceptDatas", method = RequestMethod.GET)
	public void exportRingToAcceptDatas(Parameter parameter,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("导出响铃到接听大于X秒数据到excel");
		response.setContentType("application/msexcel;charset=UTF-8");

		String title = "响铃到接听大于X秒";
		String[] headers = new String[] { "调度员", "电话振铃时刻", "通话时刻", "响铃时长(秒)",
				"受理台号", "受理备注" };
		String[] fields = new String[] { "dispatcher", "ringTime", "callTime",
				"ringDuration", "acceptCode", "acceptRemark" };
		int spanCount = 1; // 需要合并的列数。从第1列开始到指定列。
		TableData td = ExcelUtils.createTableData(
				ringToAcceptService.getData(parameter).getRows(),
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
