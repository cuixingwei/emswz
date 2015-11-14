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

import com.xhs.ems.bean.CenterBusiness;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.bean.easyui.SessionInfo;
import com.xhs.ems.excelTools.ExcelUtils;
import com.xhs.ems.excelTools.JsGridReportBase;
import com.xhs.ems.excelTools.TableData;
import com.xhs.ems.service.CenterBusinessService;

/**
 * @datetime 2015年11月12日 下午6:10:57
 * @author 崔兴伟
 */
@Controller
@RequestMapping(value = "/page/base")
public class CenterBusinessController {
	private static final Logger logger = Logger
			.getLogger(CarWorkController.class);
	@Autowired
	private CenterBusinessService centerBusinessService;

	@RequestMapping(value = "/getCenterBusinessDatas", method = RequestMethod.POST)
	public @ResponseBody java.util.List<CenterBusiness> getData(
			Parameter parameter) {
		logger.info("中心业务数据统计");
		return centerBusinessService.getCenterBusinesseData(parameter);
	}

	@RequestMapping(value = "/exportCenterBusinessDatas", method = RequestMethod.GET)
	public void export(Parameter parameter, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("导出中心业务数据统计到excel");
		response.setContentType("application/msexcel;charset=GBK");

		String title = "中心业务数据统计";
		String[] parents = new String[] { "电话总量", "电话来源分类", "派车分类", "受理时限",
				"派车单位", "其他" };// 父表头数组
		String[][] children = new String[][] {
				new String[] { "呼入电话数", "呼出电话数", "合计" },
				new String[] { "呼救电话数", "110来电", "119来电", "其他" },
				new String[] { "现场急救派车", "医院转诊派车", "转运派车", "医疗保障派车", "非急救派车" },
				new String[] { "急救受理时长", "转诊受理时长" },
				new String[] { "中心派车", "分诊派车" },
				new String[] { "挂起事件计数", "中止任务计数" } };// 子表头数组
		String[] fields = new String[] { "inPhone", "outPhone", "totalPhone",
				"helpPhone", "phoneOf110", "phoneOf119", "phoneOfOther",
				"spotFirstAid", "stationTransfer", "inHospitalTransfer",
				"safeguard", "noFirstAid", "firstaidAcceptTime",
				"referralAcceptTime", "centerSendCar", "referralSendCar",
				"hungNumbers", "stopStaskNumbers" };
		TableData td = ExcelUtils.createTableData(
				centerBusinessService.getCenterBusinesseData(parameter),
				ExcelUtils.createTableHeader(parents, children), fields);
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
