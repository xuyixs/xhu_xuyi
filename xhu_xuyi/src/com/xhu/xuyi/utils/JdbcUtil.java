package com.xhu.xuyi.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JdbcUtil {
	private static String url = "jdbc:mysql://localhost:3306/xhu_xuyi";
	private static String user = "root";
	private static String password = "root";
	private static String driverClass = "com.mysql.jdbc.Driver";
	
	static{
//		Properties prop = new Properties();
//		InputStream in = JdbcUtil.class.getResourceAsStream("com/xhu/xuyi/db.properties");
		try {
//			prop.load(in);
//			
//			url = prop.getProperty("url");
//			user = prop.getProperty("user");
//			password = prop.getProperty("password");
//			driverClass = prop.getProperty("driverClass");
			
			Class.forName(driverClass);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	public static Connection getConnection(){
		Connection conn = null;
			try {
				conn = DriverManager.getConnection(url,user,password);
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return conn;
	}
	
	public static void close(Statement stmt,Connection conn){
		
		if(stmt != null){
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if(conn != null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void close(ResultSet rs,Statement stmt,Connection conn){
		if(rs != null){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if(stmt != null){
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if(conn != null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
}
