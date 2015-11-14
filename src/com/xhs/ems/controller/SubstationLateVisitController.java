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
import com.xhs.ems.service.SubstationLateVisitService;

/**
 * @author 崔兴伟
 * @datetime 2015年4月16日 下午8:07:25
 */
@Controller
@RequestMapping(value = "/page/base")
public class SubstationLateVisitController {
	private static final Logger logger = Logger
			.getLogger(SubstationLateVisitController.class);

	@Autowired
	private SubstationLateVisitService substationLateVisitService;

	@RequestMapping(value = "/getSubstationLateVisitDatas", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter) {
		logger.info("急救站3分钟未出诊统计");
		return substationLateVisitService.getData(parameter);
	}

	@RequestMapping(value = "/exportSubstationLateVisitDatas", method = RequestMethod.GET)
	public void export(Parameter parameter, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("导出急救站3分钟未出诊数据到Excel");
		response.setContentType("application/msexcel;charset=UTF-8");
		String title = "急救站3分钟未出诊统计";
		String[] headers = new String[] { "现场地址", "事件类型", "车辆标识", "受理时刻",
				"接收命令时刻", "出车时刻", "出车时长", "出车结果", "任务备注", "调度员", "医生", "护士",
				"司机", "分站" };
		String[] fields = new String[] { "siteAddress", "eventType", "carCode",
				"acceptTime", "acceptTaskTime", "outCarTime", "outCarTimes",
				"taskResult", "remark", "dispatcher", "docter", "nurse",
				"driver", "station" };
		TableData td = ExcelUtils.createTableData(substationLateVisitService
				.getData(parameter).getRows(), ExcelUtils
				.createTableHeader(headers), fields);
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
