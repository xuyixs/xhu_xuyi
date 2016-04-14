package com.xhu.xuyi.servlet;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xhu.xuyi.dao.InitDao;

/**
 * Servlet implementation class InitServlet
 */
public class InitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void init(ServletConfig config) throws ServletException {
		
		ServletContext context = config.getServletContext();
		
		InitDao initDao = new InitDao();
		Map<Integer, String> oxygenMap = new TreeMap<Integer, String>();
		oxygenMap = initDao.initOxygenTime();
		Map<String, String> feedMap = new TreeMap<String, String>();
		feedMap = initDao.initFeedTime();
		
		//将从数据库获取到的常量时间放到ServletContext域中
		context.setAttribute("oxygenMap", oxygenMap);
		context.setAttribute("feedMap", feedMap);
		
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
