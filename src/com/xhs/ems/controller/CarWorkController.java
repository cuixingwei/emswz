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
import com.xhs.ems.service.CarWorkService;

/**
 * @author 崔兴伟
 * @datetime 2015年4月17日 下午2:03:48
 */
@Controller
@RequestMapping(value = "/page/base")
public class CarWorkController {
	private static final Logger logger = Logger
			.getLogger(CarWorkController.class);
	@Autowired
	private CarWorkService carWorkService;

	@RequestMapping(value = "/getCarWorkDatas", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter) {
		logger.info("车辆工作情况统计");
		return carWorkService.getData(parameter);
	}

	@RequestMapping(value = "/exportCarWorkDatas", method = RequestMethod.GET)
	public void export(Parameter parameter, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("导出车辆工作统计到excel");
		response.setContentType("application/msexcel;charset=UTF-8");
		String title = "车辆工作情况统计";
		String[] headers = new String[] { "车辆", "出车次数", "平均出车时长", "到达现场次数",
				"平均到达现场时长", "暂停次数" };
		String[] fields = new String[] { "carCode", "outCarNumbers",
				"averageOutCarTimes", "arriveSpotNumbers",
				"averageArriveSpotTimes", "pauseNumbers" };
		TableData td = ExcelUtils.createTableData(
				carWorkService.getData(parameter).getRows(),
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
