package com.xhu.xuyi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.xhu.xuyi.entity.User;
import com.xhu.xuyi.utils.JdbcUtil;

public class UserDao {
	
	Connection conn;
	PreparedStatement pstmt;
	ResultSet resultSet;
	public User findUserByUserName(String userName) {
		conn = JdbcUtil.getConnection();
		String sql = "select * from user where userName=?";
		User user = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userName);
			resultSet = pstmt.executeQuery();
			while(resultSet.next()){
				user = new User();
				user.setUserName(resultSet.getString(2));
			}
			JdbcUtil.close(resultSet, pstmt, conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return user;
	}

	public User findUserByUserNameAndPassword(String userName, String password) {
		conn = JdbcUtil.getConnection();
		String sql = "select * from user where userName=? and password=?";
		User user = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userName);
			pstmt.setString(2, password);
			resultSet = pstmt.executeQuery();
			while(resultSet.next()){
				user = new User();
				user.setUserId(resultSet.getString(1));
				user.setUserName(resultSet.getString(2));
				user.setPassword(resultSet.getString(3));
				user.setUserIp(resultSet.getString(4));
				user.setUserPort(resultSet.getInt(5));
			}
			JdbcUtil.close(resultSet, pstmt, conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return user;
	}

}
