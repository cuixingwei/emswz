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
import com.xhs.ems.service.TeleBookService;

@Controller
@RequestMapping(value = "/page/base")
public class TeleBookController {
	private static final Logger logger = Logger
			.getLogger(TeleBookController.class);

	@Autowired
	private TeleBookService teleBookService;

	@RequestMapping(value = "/getTeleBookDatas", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter) {
		logger.info("通讯录");
		return teleBookService.getData(parameter);
	}

	@RequestMapping(value = "/exportTeleBookDatas", method = RequestMethod.GET)
	public @ResponseBody void exportTeleBookDatas(Parameter parameter,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("导出通讯录为excel");
		response.setContentType("application/msexcel;charset=UTF-8");

		String title = "通讯录";
		String[] headers = new String[] { "单位名称", "机主姓名", "联系电话", "固定电话", "分机",
				"移动电话", "小灵通", "备注" };
		String[] fields = new String[] { "department", "ownerName",
				"contactPhone", "fixedPhone", "extension", "mobilePhone",
				"littleSmart", "remark" };
		int spanCount = 1; // 需要合并的列数。从第1列开始到指定列。
		TableData td = ExcelUtils.createTableData(
				teleBookService.getData(parameter).getRows(),
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
