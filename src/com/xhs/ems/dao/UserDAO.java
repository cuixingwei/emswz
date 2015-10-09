package com.xhs.ems.dao;

import java.util.List;

import com.xhs.ems.bean.User;

public interface UserDAO {
	/**
	 * 验证登录
	 * 
	 * @param employeeId
	 * @param password
	 * @param checkLogin
	 * @return
	 */
	public List<User> validateMrUser(User user);

	/**
	 * 获取所有有效调度员信息
	 * 
	 * @return
	 */
	public List<User> getAvailableDispatcher();

	/**
	 * @author 崔兴伟
	 * @datetime 2015年5月21日 上午9:29:40
	 * @param user
	 * @return 1代表修改成功 0代表修改失败
	 */
	public int changePwd(User user);
}
