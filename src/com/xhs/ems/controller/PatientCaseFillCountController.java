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
import com.xhs.ems.service.PatientCaseFillCountService;

/**
 * @author 崔兴伟
 * @datetime 2015年4月22日 下午2:14:15
 */
@Controller
@RequestMapping(value = "/page/base")
public class PatientCaseFillCountController {
	private static final Logger logger = Logger
			.getLogger(PatientCaseFillCountController.class);

	@Autowired
	private PatientCaseFillCountService patientCaseFillCountService;

	@RequestMapping(value = "/getPatientCaseFillCountDatas", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter) {
		logger.info("病例回填率查询");
		return patientCaseFillCountService.getData(parameter);
	}

	@RequestMapping(value = "/exportPatientCaseFillCountDatas", method = RequestMethod.GET)
	public void export(Parameter parameter, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("导出病例回填率数据到excel");
		response.setContentType("application/msexcel;charset=UTF-8");
		String title = "病例回填率查询";
		String[] headers = new String[] { "分站", "120派诊数", "回填数", "回填率" };
		String[] fields = new String[] { "station", "sendNumbers",
				"fillNumbers", "rate" };
		int spanCount = 1; // 需要合并的列数。从第1列开始到指定列。
		TableData td = ExcelUtils.createTableData(patientCaseFillCountService
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
