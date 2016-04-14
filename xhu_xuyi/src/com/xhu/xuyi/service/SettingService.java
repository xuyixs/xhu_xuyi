package com.xhu.xuyi.service;

import com.xhu.xuyi.dao.UserOxFeDao;
import com.xhu.xuyi.entity.UserOxFe;

public class SettingService {
	
	UserOxFeDao userOxFeDao;
	
	//�����û�Id�ҳ��û��ѱ��������
	public UserOxFe findUserOxFeByUserId(String userId) {
		
		return new UserOxFeDao().findUserOxFeByUserId(userId);
	}
	
	//������߸����û�������
	public void saveOrUpdateUserOxFe(UserOxFe userOxFe) {
		String userId = userOxFe.getUserId();
		
		//�ж��Ƿ��Ѿ������������û�������
		UserOxFe oxFe = this.findUserOxFeByUserId(userId);
		if(oxFe != null){
			//�����Ϊ�գ�ִ�и���
			this.update(userOxFe);
		}else{
			//���Ϊ�գ�ִ�б���
			this.save(userOxFe);
		}
	}
	
	//��ȡ��������
	private void save(UserOxFe userOxFe) {
		userOxFeDao = new UserOxFeDao();
		userOxFeDao.saveUserOxFe(userOxFe);
	}
	//��ȡ��������
	private void update(UserOxFe userOxFe) {
		userOxFeDao = new UserOxFeDao();
		userOxFeDao.updateUserOxFe(userOxFe);
	}
	
	
}
