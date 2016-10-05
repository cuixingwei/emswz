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
import com.xhs.ems.service.PatientAgeSpanService;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午2:52:12
 */
@Controller
@RequestMapping(value = "page/base")
public class PatientAgeSpanController {
	private static final Logger logger = Logger
			.getLogger(PatientAgeSpanController.class);

	@Autowired
	private PatientAgeSpanService patientAgeSpanService;

	@RequestMapping(value = "/getPatientAgeSpanDatas", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter) {
		logger.info("病人年龄分段统计");
		return patientAgeSpanService.getData(parameter);
	}

	@RequestMapping(value = "/exportPatientAgeSpan", method = RequestMethod.GET)
	public void export(Parameter parameter, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("导出病人年龄分段统计到excel");
		response.setContentType("application/msexcel;charset=UTF-8");

		String title = "病人年龄分段统计";
		String[] headers = new String[] { "年龄段", "次数", "比率" };
		String[] fields = new String[] { "span", "times", "rate" };
		int spanCount = 1; // 需要合并的列数。从第1列开始到指定列。
		TableData td = ExcelUtils.createTableData(patientAgeSpanService
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
