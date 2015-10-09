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
import com.xhs.ems.service.RefuseAcceptReasonService;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午3:13:56
 */
@Controller
@RequestMapping(value = "page/base")
public class RefuseAcceptReasonController {
	private static final Logger logger = Logger
			.getLogger(RefuseAcceptReasonController.class);

	@Autowired
	private RefuseAcceptReasonService refuseAcceptReasonService;

	@RequestMapping(value = "/getRefuseAcceptReasonDatas", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter) {
		logger.info("分诊他院拒绝受理原因统计");
		return refuseAcceptReasonService.getData(parameter);
	}

	@RequestMapping(value = "/exportRefuseAcceptReason", method = RequestMethod.GET)
	public void export(Parameter parameter, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("导出分诊他院拒绝受理原因统计到excel");
		response.setContentType("application/msexcel;charset=UTF-8");

		String title = "分诊他院拒绝受理原因统计";
		String[] headers = new String[] { "医院名称", "分诊总数", "拒绝受理原因", "次数" };
		String[] fields = new String[] { "station", "totals", "reason", "times" };
		int spanCount = 1; // 需要合并的列数。从第1列开始到指定列。
		TableData td = ExcelUtils.createTableData(refuseAcceptReasonService
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
