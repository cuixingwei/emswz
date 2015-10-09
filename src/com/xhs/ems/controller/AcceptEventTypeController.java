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
import com.xhs.ems.service.AcceptEventTypeService;

@Controller
@RequestMapping(value = "/page/base")
public class AcceptEventTypeController {
	private static final Logger logger = Logger
			.getLogger(AcceptEventTypeController.class);
	@Autowired
	private AcceptEventTypeService acceptEventTypeService;

	@RequestMapping(value = "/getAcceptEventTypeDatas", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter) {
		logger.info("事件类型统计");
		return acceptEventTypeService.getData(parameter);
	}

	@RequestMapping(value = "/exportAcceptEventTypeDatas", method = RequestMethod.GET)
	public @ResponseBody void exportAcceptEventTypeDatas(Parameter parameter,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("事件类型统计");
		response.setContentType("application/msexcel;charset=GBK");

		String title = "事件类型统计";
		String[] parents = new String[] { "", "", "", "受理类型", "出车结果" };// 父表头数组
		String[][] children = new String[][] {
				new String[] { "调度员" },
				new String[] { "接电话数" },
				new String[] { "派车数" },
				new String[] { "正常派车", "正常挂起", "增援派车", "增援挂起", "中止任务", "特殊事件",
						"欲派无车", "转发中心", "拒绝出车", "唤醒待派" },
				new String[] { "中止任务", "中止任务比率", "放空车", "空车比率", "正常完成",
						"正常完成比率" } };// 子表头数组
		String[] fields = new String[] { "dispatcher", "numbersOfPhone",
				"numbersOfSendCar", "numbersOfNormalSendCar",
				"numbersOfNormalHangUp", "numbersOfReinforceSendCar",
				"numbersOfReinforceHangUp", "numbersOfStopTask",
				"specialEvent", "noCar", "transmitCenter", "refuseSendCar",
				"wakeSendCar", "stopTask", "ratioStopTask", "emptyCar",
				"ratioEmptyCar", "nomalComplete", "ratioComplete" };
		TableData td = ExcelUtils.createTableData(acceptEventTypeService
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
