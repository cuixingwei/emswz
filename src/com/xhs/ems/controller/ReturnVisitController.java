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
import com.xhs.ems.service.ReturnVisitService;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午3:24:37
 */
@Controller
@RequestMapping(value = "page/base")
public class ReturnVisitController {
	private static final Logger logger = Logger
			.getLogger(ReturnVisitController.class);

	@Autowired
	private ReturnVisitService returnVisitService;

	@RequestMapping(value = "/getReturnVisitDatas", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter) {
		logger.info("回访统计");
		return returnVisitService.getData(parameter);
	}

	@RequestMapping(value = "/exportReturnVisit", method = RequestMethod.GET)
	public void export(Parameter parameter, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("导出回访统计到excel");
		response.setContentType("application/msexcel;charset=UTF-8");

		String title = "回访统计";
		String[] headers = new String[] { "日期", "姓名", "电话", "地址", "初步诊断", "收费",
				"出诊医生", "出诊护士", "出诊司机", "调度员", "满意计数", "一般计数", "不满意计数", "评分合计" };
		String[] fields = new String[] { "date", "name", "phone", "address",
				"PreDisgnose", "cost", "doctor", "nurse", "driver",
				"dispatcher", "satisfyCount", "commonCount", "unsatisfyCount",
				"totalScore" };
		int spanCount = 1; // 需要合并的列数。从第1列开始到指定列。
		TableData td = ExcelUtils.createTableData(
				returnVisitService.getData(parameter).getRows(),
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
