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
import com.xhs.ems.service.CaseQualityService;

/**
 * @author 崔兴伟
 * @datetime 2015年5月21日 下午8:32:57
 */
@Controller
@RequestMapping(value = "page/base")
public class CaseQualityController {
	private static final Logger logger = Logger
			.getLogger(CaseQualityController.class);

	@Autowired
	private CaseQualityService caseQualityService;

	@RequestMapping(value = "/getCaseQualityDatas", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter) {
		logger.info("病历质量统计");
		return caseQualityService.getData(parameter);
	}

	@RequestMapping(value = "/exportCaseQuality", method = RequestMethod.GET)
	public void export(Parameter parameter,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("导出病历质量统计数据到excel");
		response.setContentType("application/msexcel;charset=UTF-8");

		String title = "病历质量统计";
		String[] headers = new String[] { "姓名", "完整性", "及时性", "召回次数", "得分统计" };
		String[] fields = new String[] { "name", "wholeness", "timely",
				"recallTimes", "score" };
		int spanCount = 1; // 需要合并的列数。从第1列开始到指定列。
		TableData td = ExcelUtils.createTableData(
				caseQualityService.getData(parameter).getRows(),
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
