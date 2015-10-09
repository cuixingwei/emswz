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
import com.xhs.ems.service.HungEventReasonService;

/**
 * @author 崔兴伟
 * @datetime 2015年4月15日 下午2:12:50
 */
@Controller
@RequestMapping(value = "/page/base")
public class HungEventReasonControler {
	private static final Logger logger = Logger
			.getLogger(HungEventReasonControler.class);

	@Autowired
	private HungEventReasonService hungEventReasonService;

	@RequestMapping(value = "/getHungEventReasonDatas", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter) {
		logger.info("挂起事件原因查询");
		return hungEventReasonService.getData(parameter);
	}

	@RequestMapping(value = "/exportHungEventReasonDatas", method = RequestMethod.GET)
	public void export(Parameter parameter, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("导出挂起事件原因数据到excel");
		response.setContentType("application/msexcel;charset=UTF-8");

		String title = "挂起事件原因查询";
		String[] headers = new String[] { "原因", "次数", "比率" };
		String[] fields = new String[] { "reason", "times", "rate" };
		TableData td = ExcelUtils.createTableData(hungEventReasonService
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
