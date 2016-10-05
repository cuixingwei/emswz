package com.xhs.ems.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xhs.ems.bean.OutOfThree;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.bean.easyui.SessionInfo;
import com.xhs.ems.excelTools.ExcelUtils;
import com.xhs.ems.excelTools.JsGridReportBase;
import com.xhs.ems.excelTools.TableData;
import com.xhs.ems.service.OutOfThreeService;

/**
 * @datetime 2015年11月14日 下午3:34:41
 * @author 崔兴伟
 */
@Controller
@RequestMapping(value = "page/base")
public class OutOfThreeController {
	private static final Logger logger = Logger
			.getLogger(OutOfThreeController.class);

	@Autowired
	private OutOfThreeService outOfThreeService;

	@RequestMapping(value = "/getOutOfThreeDatas", method = RequestMethod.POST)
	public @ResponseBody List<OutOfThree> getData(Parameter parameter) {
		logger.info("出诊时间大于3分钟统计表");
		return outOfThreeService.getData(parameter);
	}

	@RequestMapping(value = "/exportOutOfThreeDatas", method = RequestMethod.GET)
	public void export(Parameter parameter, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("导出出诊时间大于3分钟统计表果数据到excel");
		response.setContentType("application/msexcel;charset=UTF-8");

		String title = "出诊时间大于3分钟统计表";
		String[] headers = new String[] { "出诊类型", "标准出诊计数", "占比", "蜗牛出诊计数",
				"占比" };
		String[] fields = new String[] { "outType", "normalNumbers", "rate1",
				"lateNumbers", "rate2" };
		TableData td = ExcelUtils.createTableData(
				outOfThreeService.getData(parameter),
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
