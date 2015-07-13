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
import com.xhs.ems.service.MedicalTreatmentService;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午1:58:53
 */
@Controller
@RequestMapping(value = "page/base")
public class MedicalTreatmentController {
	private static final Logger logger = Logger
			.getLogger(MedicalTreatmentController.class);

	@Autowired
	private MedicalTreatmentService medicalTreatmentService;

	@RequestMapping(value = "/getMedicalTreatmentDatas", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter) {
		logger.info("医疗处置统计");
		return medicalTreatmentService.getData(parameter);
	}

	@RequestMapping(value = "/exportMedicalTreatment", method = RequestMethod.GET)
	public void export(Parameter parameter, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("导出医疗处置统计到excel");
		response.setContentType("application/msexcel;charset=UTF-8");

		String title = "医疗处置统计";
		String[] headers = new String[] { "医疗处置名称", "次数", "占比" };
		String[] fields = new String[] { "name", "times", "rate" };
		int spanCount = 1; // 需要合并的列数。从第1列开始到指定列。
		TableData td = ExcelUtils.createTableData(medicalTreatmentService
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
