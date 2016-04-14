package com.xhu.xuyi.service;

import com.xhu.xuyi.dao.UserDao;
import com.xhu.xuyi.entity.User;

public class UserService {
	
	UserDao userDao = new UserDao();
	
	public User findUserByUserName(String userName) {
		
		return userDao.findUserByUserName(userName);
	}


	public User findUserByUserNameAndPassword(String userName, String password) {
		
		return userDao.findUserByUserNameAndPassword(userName, password);
	}
	
	
}

