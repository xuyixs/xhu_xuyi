package com.xhu.xuyi.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.TreeMap;

import com.xhu.xuyi.utils.JdbcUtil;

public class InitDao {

	Map<Integer, String> oxygenMap = null;
	Map<String, String> feedMap = null;
	
	Connection conn;
	Statement stmt;
	ResultSet resultSet;
	
	public Map<Integer, String> initOxygenTime() {
		conn = JdbcUtil.getConnection();
		oxygenMap = new TreeMap<Integer, String>();
		
		try {
			stmt = conn.createStatement();
			resultSet = stmt.executeQuery("select * from oxygentime order by oxygenTime asc");
			while(resultSet.next()){
				oxygenMap.put(resultSet.getInt(1), resultSet.getString(2));
			}
			JdbcUtil.close(resultSet, stmt, conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return oxygenMap;
	}

	public Map<String, String> initFeedTime() {
		conn = JdbcUtil.getConnection();
		feedMap = new TreeMap<String, String>();
		
		try {
			stmt = conn.createStatement();
			resultSet = stmt.executeQuery("select * from feedtime order by feedTime asc");
			while(resultSet.next()){
				feedMap.put(resultSet.getString(1), resultSet.getString(2));
			}
			JdbcUtil.close(resultSet, stmt, conn);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return feedMap;
	}
	
	
}

