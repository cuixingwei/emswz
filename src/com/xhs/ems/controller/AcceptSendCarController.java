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

import com.xhs.ems.bean.AcceptSendCarDetail;
import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.bean.easyui.SessionInfo;
import com.xhs.ems.excelTools.ExcelUtils;
import com.xhs.ems.excelTools.JsGridReportBase;
import com.xhs.ems.excelTools.TableData;
import com.xhs.ems.service.AcceptSendCarService;

/**
 * @author 崔兴伟
 * @datetime 2015年5月7日 下午3:21:21
 */
@Controller
@RequestMapping(value = "/page/base")
public class AcceptSendCarController {
	private static final Logger logger = Logger
			.getLogger(AcceptSendCarController.class);

	@Autowired
	private AcceptSendCarService acceptSendCarService;

	@RequestMapping(value = "/getAcceptSendCarDatas", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter) {
		logger.info("开始受理到派车大于X秒统计");
		return acceptSendCarService.getData(parameter);
	}

	@RequestMapping(value = "/getDetail", method = RequestMethod.POST)
	public @ResponseBody AcceptSendCarDetail getDetail(Parameter parameter,
			HttpServletRequest request) {
		logger.info("事件详情查询");
		
		return acceptSendCarService.getDetail(parameter.getId(),request);
	}

	@RequestMapping(value = "/exportAcceptSendCarDatas", method = RequestMethod.GET)
	public void export(Parameter parameter, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("开始受理到派车大于X秒统计");
		response.setContentType("application/msexcel;charset=UTF-8");

		String title = "开始受理到派车大于X秒统计";
		String[] headers = new String[] { "调度员", "开始受理时刻", "派车时刻", "受理类型",
				"呼救号码", "派车时长", "受理备注" };
		String[] fields = new String[] { "dispatcher", "startAcceptTime",
				"sendCarTime", "acceptType", "ringPhone", "sendCarTimes",
				"remark" };
		int spanCount = 1; // 需要合并的列数。从第1列开始到指定列。
		TableData td = ExcelUtils.createTableData(
				acceptSendCarService.getData(parameter).getRows(),
				ExcelUtils.createTableHeader(headers, spanCount), fields);
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
