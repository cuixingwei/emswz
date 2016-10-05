package com.xhs.ems.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xhs.ems.bean.DocterNurseDriverOutCarTime;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.bean.easyui.SessionInfo;
import com.xhs.ems.excelTools.ExcelUtils;
import com.xhs.ems.excelTools.JsGridReportBase;
import com.xhs.ems.excelTools.TableData;
import com.xhs.ems.service.DocterNurseDriverOutCarTimeService;

/**
 * @datetime 2015年11月14日 下午4:32:07
 * @author 崔兴伟
 */
@Controller
@RequestMapping(value = "page/base")
public class DocterNurseDriverOutCarTimeController {
	private static final Logger logger = Logger
			.getLogger(DocterNurseDriverOutCarTimeController.class);

	@Autowired
	private DocterNurseDriverOutCarTimeService docterNurseDriverOutCarTimeService;

	@RequestMapping(value = "/getDocterNurseDriverOutCarTimeDatas", method = RequestMethod.POST)
	public @ResponseBody List<DocterNurseDriverOutCarTime> getData(
			Parameter parameter) {
		logger.info("医生护士驾驶员出车时间表");
		return docterNurseDriverOutCarTimeService.getData(parameter);
	}

	@RequestMapping(value = "/exportDocterNurseDriverOutCarTimeDatas", method = RequestMethod.GET)
	public void export(Parameter parameter, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("导出医生护士驾驶员出车时间表到excel");
		response.setContentType("application/msexcel;charset=UTF-8");

		String title = "医生护士驾驶员出车时间表";
		if (StringUtils.equals("1", parameter.getDoctorOrNurseOrDriver())) {
			title = "医生出车时间表";
		} else if (StringUtils
				.equals("2", parameter.getDoctorOrNurseOrDriver())) {
			title = "护士出车时间表";
		} else if (StringUtils
				.equals("3", parameter.getDoctorOrNurseOrDriver())) {
			title = "驾驶员出车时间表";
		} else {
		}
		String[] headers = new String[] { "姓名", "急救次数", "急救标准次数", "占比",
				"急救蜗牛出车次数", "占比", "转诊次数", "转诊标准次数", "占比", "转诊蜗牛出车次数", "占比" };
		String[] fields = new String[] { "name", "helpNumbers",
				"helpNormalNumbers", "rate1", "helpLateNumbers", "rate2",
				"tranforNumbers", "tranforNormalNumbers", "rate3",
				"tranforLateNumbers", "rate4" };
		TableData td = ExcelUtils.createTableData(
				docterNurseDriverOutCarTimeService.getData(parameter),
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
