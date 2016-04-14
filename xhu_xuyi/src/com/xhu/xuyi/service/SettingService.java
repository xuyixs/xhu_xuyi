package com.xhu.xuyi.service;

import com.xhu.xuyi.dao.UserOxFeDao;
import com.xhu.xuyi.entity.UserOxFe;

public class SettingService {
	
	UserOxFeDao userOxFeDao;
	
	//根据用户Id找出用户已保存的设置
	public UserOxFe findUserOxFeByUserId(String userId) {
		
		return new UserOxFeDao().findUserOxFeByUserId(userId);
	}
	
	//保存或者更新用户的设置
	public void saveOrUpdateUserOxFe(UserOxFe userOxFe) {
		String userId = userOxFe.getUserId();
		
		//判断是否已经保存过了这个用户的设置
		UserOxFe oxFe = this.findUserOxFeByUserId(userId);
		if(oxFe != null){
			//如果不为空，执行更新
			this.update(userOxFe);
		}else{
			//如果为空，执行保存
			this.save(userOxFe);
		}
	}
	
	//抽取方法保存
	private void save(UserOxFe userOxFe) {
		userOxFeDao = new UserOxFeDao();
		userOxFeDao.saveUserOxFe(userOxFe);
	}
	//抽取方法更新
	private void update(UserOxFe userOxFe) {
		userOxFeDao = new UserOxFeDao();
		userOxFeDao.updateUserOxFe(userOxFe);
	}
	
	
}
