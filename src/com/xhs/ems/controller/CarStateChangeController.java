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
import com.xhs.ems.dao.CarStateChangeDAO;
import com.xhs.ems.excelTools.ExcelUtils;
import com.xhs.ems.excelTools.JsGridReportBase;
import com.xhs.ems.excelTools.TableData;

/**
 * @author 崔兴伟
 * @datetime 2015年4月13日 下午9:17:33
 */
@Controller
@RequestMapping(value = "/page/base")
public class CarStateChangeController {
	private static final Logger logger = Logger
			.getLogger(CarStateChangeController.class);

	@Autowired
	private CarStateChangeDAO carStateChangeDAO;

	@RequestMapping(value = "/getCarStateChangeDatas", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter) {
		logger.info("车辆状态变化查询");
		return carStateChangeDAO.getData(parameter);
	}

	@RequestMapping(value = "/exportCarStateChangeDatas", method = RequestMethod.GET)
	public @ResponseBody void export(Parameter parameter,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("导出车辆状态变化数据到excel");
		response.setContentType("application/msexcel;charset=UTF-8");
		logger.info("eventName:" + parameter.getEventName() + ";carCode:"
				+ parameter.getCarCode());
		String title = "车辆状态变化";
		String[] headers = new String[] { "事件名称", "车辆", "车辆状态", "记录时刻", "记录类型",
				"坐席号", "操作人" };
		String[] fields = new String[] { "eventName", "carCode", "carState",
				"recordTime", "recordClass", "seatCode", "dispatcher" };
		int spanCount = 1; // 需要合并的列数。从第1列开始到指定列。
		TableData td = ExcelUtils.createTableData(
				carStateChangeDAO.getData(parameter).getRows(),
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
