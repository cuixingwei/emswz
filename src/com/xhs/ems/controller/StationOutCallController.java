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
import com.xhs.ems.service.StationOutCallService;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午4:02:07
 */
@Controller
@RequestMapping(value = "page/base")
public class StationOutCallController {
	private static final Logger logger = Logger
			.getLogger(StationOutCallController.class);

	@Autowired
	private StationOutCallService stationOutCallService;

	@RequestMapping(value = "/getStationOutCallDatas", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter) {
		logger.info("站点出诊情况");
		return stationOutCallService.getData(parameter);
	}

	@RequestMapping(value = "/exportStationOutCall", method = RequestMethod.GET)
	public void export(Parameter parameter, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("导出站点出诊情况到excel");
		response.setContentType("application/msexcel;charset=UTF-8");

		String title = "站点出诊情况";
		String[] headers = new String[] { "站点名称", "现场急救", "医院转诊", "院内转运",
				"送出病人", "保障", "行政用车", "义诊", "培训", "演习", "其他", "出诊数合计", "接回数合计",
				"里程", "空车", "拒绝", "死亡" };
		String[] fields = new String[] { "station", "spotFirstAid",
				"stationTransfer", "inHospitalTransfer", "sendOutPatient",
				"safeguard", "auv", "volunteer", "train", "practice", "other",
				"outCallTotal", "tackBackTotal", "distance", "emptyCars",
				"refuses", "death" };
		int spanCount = 1; // 需要合并的列数。从第1列开始到指定列。
		TableData td = ExcelUtils.createTableData(stationOutCallService
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
