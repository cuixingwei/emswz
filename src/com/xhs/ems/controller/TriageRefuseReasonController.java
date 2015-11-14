package com.xhs.ems.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xhs.ems.bean.TriageRefuseReason;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.bean.easyui.SessionInfo;
import com.xhs.ems.excelTools.ExcelUtils;
import com.xhs.ems.excelTools.JsGridReportBase;
import com.xhs.ems.excelTools.TableData;
import com.xhs.ems.service.TriageRefuseReasonService;

/**
 * @datetime 2015年11月14日 下午2:16:53
 * @author 崔兴伟
 */
@Controller
@RequestMapping(value = "page/base")
public class TriageRefuseReasonController {
	private static final Logger logger = Logger
			.getLogger(TriageRefuseReasonController.class);

	@Autowired
	private TriageRefuseReasonService triageRefuseReasonService;

	@RequestMapping(value = "/getTriageRefuseReasonDatas", method = RequestMethod.POST)
	public @ResponseBody List<TriageRefuseReason> getData(Parameter parameter) {
		logger.info("分诊他院拒绝原因统计查询");
		return triageRefuseReasonService.getData(parameter);
	}

	@RequestMapping(value = "/exportTriageRefuseReasonDatas", method = RequestMethod.GET)
	public void export(Parameter parameter, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("导出分诊他院拒绝原因统计到excel");
		response.setContentType("application/msexcel;charset=UTF-8");

		String title = "分诊他院拒绝原因统计";
		String[] headers = new String[] { "转归结果", "次数", "比率" };
		String[] fields = new String[] { "resultName", "times", "rate" };
		TableData td = ExcelUtils.createTableData(
				triageRefuseReasonService.getData(parameter),
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
