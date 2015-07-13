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
import com.xhs.ems.service.ThreeNOService;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午5:22:38
 */
@Controller
@RequestMapping(value = "page/base")
public class ThreeNOController {
	private static final Logger logger = Logger
			.getLogger(ThreeNOController.class);

	@Autowired
	private ThreeNOService threeNOService;

	@RequestMapping(value = "/getThreeNODatas", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter) {
		logger.info("三无统计");
		return threeNOService.getData(parameter);
	}

	@RequestMapping(value = "/exportThreeNO", method = RequestMethod.GET)
	public void export(Parameter parameter, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("导出三无统计到excel");
		response.setContentType("application/msexcel;charset=UTF-8");

		String title = "三无统计";
		String[] headers = new String[] { "姓名", "性别", "年龄", "诊断", "地址", "费用",
				"送往地点", "距离" };
		String[] fields = new String[] { "name", "gender", "age", "diagnose",
				"address", "cost", "toAddress", "distance" };
		int spanCount = 1; // 需要合并的列数。从第1列开始到指定列。
		TableData td = ExcelUtils.createTableData(
				threeNOService.getData(parameter).getRows(),
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
