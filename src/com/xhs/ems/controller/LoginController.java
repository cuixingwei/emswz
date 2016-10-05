package com.xhs.ems.controller;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xhs.ems.bean.User;
import com.xhs.ems.bean.easyui.Json;
import com.xhs.ems.bean.easyui.SessionInfo;
import com.xhs.ems.common.CommonUtil;
import com.xhs.ems.service.UserService;

@Controller
@RequestMapping(value = "/page")
public class LoginController {
	private static final Logger logger = Logger
			.getLogger(LoginController.class);

	@Autowired
	private UserService userService;

	/**
	 * 返回菜单
	 * 
	 * @author CUIXINGWEI
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getMenu", method = RequestMethod.POST)
	public @ResponseBody String getMenu() {
		logger.info("获取菜单");
		URL url = LoginController.class.getClassLoader().getResource(
				"menu.json");
		String menuString = "";
		try {
			menuString = CommonUtil.readContentFromFile(new File(url.toURI()));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return menuString;
	}

	/**
	 * 登录方法
	 * 
	 * @author CUIXINGWEI
	 * 
	 * @param userId
	 * @param password
	 * @param request
	 * @return 登录结果result:fail代表用户不存在,error表示用户名错误,success表示登录成功
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody Json login(
			@RequestParam(value = "userId") String userId,
			@RequestParam(value = "password") String password,
			HttpServletRequest request) {
		logger.info("用户名:" + userId + ":密码" + password);
		Json json = new Json();
		User user = new User(userId, "", password, 1);
		List<User> list = userService.validateMrUser(user);
		json.setMsg("fail");
		json.setSuccess(false);
		if (list.size() > 0) {
			json.setMsg("error");
			User usertemp = list.get(0);
			if (usertemp.getPassword().equalsIgnoreCase(password)) {
				if (3 != usertemp.getIsValid()) {
					json.setSuccess(true);
					json.setMsg("success");
					if (CommonUtil.isNullOrEmpty(usertemp.getStationName())) {
						usertemp.setStationName("未知分站");
					}
					HttpSession session = request.getSession();
					SessionInfo sessionInfo = new SessionInfo();
					sessionInfo.setUser(usertemp);
					session.setAttribute("sessionInfo", sessionInfo);
				} else {
					json.setMsg("noPermission");
				}
			}
		}
		return json;
	}

	/**
	 * 注销系统
	 * 
	 * @author CUIXINGWEI
	 * 
	 * @return
	 */
	@RequestMapping(value = "/logOut", method = RequestMethod.POST)
	public @ResponseBody Json logOut(HttpSession session) {
		logger.info("注销");
		if (session != null) {
			session.invalidate();
			logger.info("注销成功");
		}
		Json json = new Json();
		json.setSuccess(true);
		return json;
	}

	@RequestMapping(value = "/changePwd", method = RequestMethod.POST)
	public @ResponseBody Json changePwd(String dataPwd, HttpSession session) {
		logger.info("修改密码");
		SessionInfo sessionInfo = (SessionInfo) session
				.getAttribute("sessionInfo");
		User user = sessionInfo.getUser();
		user.setPassword(dataPwd);
		Json json = new Json();
		logger.info(userService.changePwd(user));
		if (1 == userService.changePwd(user)) {
			json.setSuccess(true);
		} else {
			json.setSuccess(false);
		}
		return json;
	}

}
