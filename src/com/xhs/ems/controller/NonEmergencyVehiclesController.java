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
import com.xhs.ems.service.NonEmergencyVehiclesService;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午2:14:25
 */
@Controller
@RequestMapping(value = "page/base")
public class NonEmergencyVehiclesController {
	private static final Logger logger = Logger
			.getLogger(NonEmergencyVehiclesController.class);

	@Autowired
	private NonEmergencyVehiclesService nonEmergencyVehiclesService;

	@RequestMapping(value = "/getNonEmergencyVehiclesDatas", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter) {
		logger.info("非急救用车统计");
		return nonEmergencyVehiclesService.getData(parameter);
	}

	@RequestMapping(value = "/exportNonEmergencyVehicles", method = RequestMethod.GET)
	public void export(Parameter parameter, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("导出非急救用车统计到excel");
		response.setContentType("application/msexcel;charset=UTF-8");

		String title = "非急救用车统计";
		String[] parents = new String[] { "院内转运", "保障", "义诊会诊", "演习培训", "本院内",
				"行后用车", "其他" };// 父表头数组
		String[][] children = new String[][] { new String[] { "次数", "里程" },
				new String[] { "次数", "里程" }, new String[] { "次数", "里程" },
				new String[] { "次数", "里程" }, new String[] { "次数", "里程" },
				new String[] { "次数", "里程" }, new String[] { "次数", "里程" },
				new String[] { "次数", "里程" } };// 子表头数组
		String[] fields = new String[] { "hospital_times", "hospital_distance",
				"safeguard_times", "safeguard_distance", "clinic_times",
				"clinic_distance", "practice_times", "practice_distance",
				"inHospital_times", "inHospital_distance", "xh_times",
				"xh_distance", "other_times", "other_distance" };
		TableData td = ExcelUtils.createTableData(nonEmergencyVehiclesService
				.getData(parameter).getRows(), ExcelUtils.createTableHeader(
				parents, children), fields);
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
