package com.xhs.ems.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.User;
import com.xhs.ems.dao.UserDAO;
import com.xhs.ems.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDAO userDAO;

	@Override
	public List<User> validateMrUser(User user) {
		return userDAO.validateMrUser(user);
	}

	@Override
	public List<User> getAvailableDispatcher() {
		return userDAO.getAvailableDispatcher();
	}

	@Override
	public int changePwd(User user) {
		return userDAO.changePwd(user);
	}

}
