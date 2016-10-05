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
import com.xhs.ems.service.DocterNurseDriverService;

/**
 * @author 崔兴伟
 * @datetime 2015年5月21日 下午10:11:02
 */
@Controller
@RequestMapping(value = "page/base")
public class DocterNurseDriverController {
	private static final Logger logger = Logger
			.getLogger(DocterNurseDriverController.class);

	@Autowired
	private DocterNurseDriverService docterNurseDriverService;

	@RequestMapping(value = "/getDocterNurseDriverDatas", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter) {
		logger.info("医护、司机工作统计");
		return docterNurseDriverService.getData(parameter);
	}

	@RequestMapping(value = "/exportDocterNurseDriver", method = RequestMethod.GET)
	public void export(Parameter parameter, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("导出医护、司机工作统计数据到excel");
		response.setContentType("application/msexcel;charset=UTF-8");

		String title1 = "医生工作统计";
		String title2 = "护士工作统计";
		String title3 = "司机工作统计";
		String[] headers = new String[] { "站点", "姓名", "出诊数", "接回数", "空车",
				"拒绝入院", "现场死亡", "救后死亡", "安全送出", "非急救任务完成", "其他", "里程合计",
				"收费合计", "平均反应时间", "平均出车时间", "出诊用时合计", "治疗数统计" };
		String[] fields = new String[] { "station", "name", "outCalls",
				"takeBacks", "emptyCars", "refuseHospitals", "spotDeaths",
				"afterDeaths", "safeOut", "noAmbulance", "others",
				"distanceTotal", "costToal", "averageResponseTime",
				"averageSendTime", "outCallTimeTotal", "cureNumbers" };
		TableData td = ExcelUtils.createTableData(docterNurseDriverService
				.getData(parameter).getRows(), ExcelUtils
				.createTableHeader(headers), fields);
		JsGridReportBase report = new JsGridReportBase(request, response);

		HttpSession session = request.getSession();
		SessionInfo sessionInfo = (SessionInfo) session
				.getAttribute("sessionInfo");
		if ("1".equals(parameter.getDoctorOrNurseOrDriver())) {
			if (null != sessionInfo) {
				report.exportToExcel(title1, sessionInfo.getUser().getName(),
						td, parameter);
			} else {
				report.exportToExcel(title1, "", td, parameter);
			}
		} else if ("2".equals(parameter.getDoctorOrNurseOrDriver())) {
			if (null != sessionInfo) {
				report.exportToExcel(title2, sessionInfo.getUser().getName(),
						td, parameter);
			} else {
				report.exportToExcel(title2, "", td, parameter);
			}
		} else if ("3".equals(parameter.getDoctorOrNurseOrDriver())) {
			if (null != sessionInfo) {
				report.exportToExcel(title3, sessionInfo.getUser().getName(),
						td, parameter);
			} else {
				report.exportToExcel(title3, "", td, parameter);
			}
		}

	}

}
