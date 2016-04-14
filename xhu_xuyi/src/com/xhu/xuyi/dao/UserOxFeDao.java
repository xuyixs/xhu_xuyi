package com.xhu.xuyi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.xhu.xuyi.entity.UserOxFe;
import com.xhu.xuyi.utils.JdbcUtil;

public class UserOxFeDao {
	
	Connection conn;
	PreparedStatement pstmt;
	ResultSet resultSet;
	
	public UserOxFe findUserOxFeByUserId(String userId) {
		
		conn = JdbcUtil.getConnection();
		
		UserOxFe userOxFe = null;
		String sql = "select * from user_ox_fe where userId=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			resultSet = pstmt.executeQuery();
			while(resultSet.next()){
				userOxFe = new UserOxFe();
				userOxFe.setUserId(userId);
				userOxFe.setOxygenTime(resultSet.getString(2));
				userOxFe.setFeedTime(resultSet.getString(3));
			}
			JdbcUtil.close(resultSet, pstmt, conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return userOxFe;
	}

	public void saveUserOxFe(UserOxFe userOxFe) {
		conn = JdbcUtil.getConnection();
		String sql = "insert into user_ox_fe values(?,?,?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userOxFe.getUserId());
			pstmt.setString(2, userOxFe.getOxygenTime());
			pstmt.setString(3, userOxFe.getFeedTime());
			pstmt.executeUpdate();
			
			JdbcUtil.close(pstmt, conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateUserOxFe(UserOxFe userOxFe) {
		//首先执行删除数据的操作
		conn = JdbcUtil.getConnection();
		String deleteSql = "delete from user_ox_fe where userId=?";
		
		try {
			pstmt = conn.prepareStatement(deleteSql);
			pstmt.setString(1, userOxFe.getUserId());
			pstmt.executeUpdate();
			
			this.saveUserOxFe(userOxFe);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
