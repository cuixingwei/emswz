package com.xhs.ems.dao;

import javax.servlet.http.HttpServletRequest;

import com.xhs.ems.bean.AcceptSendCarDetail;
import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;

/**
 * @author 崔兴伟
 * @datetime 2015年5月7日 下午3:06:10
 */
public interface AcceptSendCarDAO {
	/**
	 * @author 崔兴伟
	 * @datetime 2015年5月7日 下午3:06:29
	 * @param parameter
	 * @return 开始受理到派车大于X秒
	 */
	public Grid getData(Parameter parameter);

	/**
	 * @author 崔兴伟
	 * @datetime 2015年5月9日 上午11:33:22
	 * @param id
	 * @return 事件详情
	 */
	public AcceptSendCarDetail getDetail(String id, HttpServletRequest request);

}
