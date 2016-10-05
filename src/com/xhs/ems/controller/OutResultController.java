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

import com.xhs.ems.bean.OutResult;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.bean.easyui.SessionInfo;
import com.xhs.ems.excelTools.ExcelUtils;
import com.xhs.ems.excelTools.JsGridReportBase;
import com.xhs.ems.excelTools.TableData;
import com.xhs.ems.service.OutResultService;

/**
 * @datetime 2015年11月14日 下午1:48:37
 * @author 崔兴伟
 */
@Controller
@RequestMapping(value = "page/base")
public class OutResultController {
	private static final Logger logger = Logger
			.getLogger(OutResultController.class);

	@Autowired
	private OutResultService outResultService;

	@RequestMapping(value = "/getOutResultDatas", method = RequestMethod.POST)
	public @ResponseBody List<OutResult> getData(Parameter parameter) {
		logger.info("转归结果查询");
		return outResultService.getData(parameter);
	}

	@RequestMapping(value = "/exportOutResultDatas", method = RequestMethod.GET)
	public void export(Parameter parameter, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("导出转归结果数据到excel");
		response.setContentType("application/msexcel;charset=UTF-8");

		String title = "转归结果统计";
		String[] headers = new String[] { "转归结果", "次数", "比率" };
		String[] fields = new String[] { "resultName", "times", "rate" };
		TableData td = ExcelUtils.createTableData(
				outResultService.getData(parameter),
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
