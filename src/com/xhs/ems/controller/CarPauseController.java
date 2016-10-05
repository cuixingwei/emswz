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
import com.xhs.ems.service.CarPauseService;

/**
 * @author 崔兴伟
 * @datetime 2015年4月13日 下午4:44:44
 */
@Controller
@RequestMapping(value = "/page/base")
public class CarPauseController {
	private static final Logger logger = Logger
			.getLogger(CarPauseController.class);

	@Autowired
	private CarPauseService carPauseService;

	@RequestMapping(value = "/getCarPauseDatas", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter) {
		logger.info("车辆暂停调用原因查询");
		return carPauseService.getData(parameter);
	}

	@RequestMapping(value = "/exportCarPauseDatas", method = RequestMethod.GET)
	public void export(Parameter parameter, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("导出车辆暂停调用原因数据到excel");
		response.setContentType("application/msexcel;charset=UTF-8");

		String title = "车辆暂停调用原因";
		String[] headers = new String[] { "车辆", "司机", "暂停时长", "暂停时刻", "结束时刻",
				"操作人", "暂停原因" };
		String[] fields = new String[] { "carCode", "driver", "pauseTimes",
				"pauseTime", "endTime", "dispatcher", "pauseReason" };
		TableData td = ExcelUtils.createTableData(
				carPauseService.getData(parameter).getRows(),
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
