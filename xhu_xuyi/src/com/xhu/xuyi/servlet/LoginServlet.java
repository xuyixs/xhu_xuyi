package com.xhu.xuyi.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xhu.xuyi.entity.User;
import com.xhu.xuyi.entity.UserOxFe;
import com.xhu.xuyi.service.SettingService;
import com.xhu.xuyi.service.UserService;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * 采用Post方式提交用户的用户名和密码，根据这两个参数去数据库查询User表，返回的对象放到Session中
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		User user = new User();
		String userName;
		String password;
		userName = request.getParameter("userName");
		password = request.getParameter("password");
		
		UserService userService = new UserService();
		//null
		if(userName == null){
			System.out.println("登录页面没有提交数据，暂定的用户名与密码。");
			userName = "xuyi";
			password = "123";
		}
		user = userService.findUserByUserName(userName);
		if(user == null){
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}else{
			//存在以userName作为用户名的用户，再以用户名和密码同时查询数据库
			user = userService.findUserByUserNameAndPassword(userName, password);
			if(user == null){
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}else{
				//登录成功，把用户信息存入到Session中
				request.getSession().setAttribute("sysUser", user);
				
				//查找出用户保存的投食和充氧的时间并保存到Sesstion中
				SettingService settingService = new SettingService();
				UserOxFe userOxFe = settingService.findUserOxFeByUserId(user.getUserId());
				request.getSession().setAttribute("userOxFe", userOxFe);
				
				request.getRequestDispatcher("home.jsp").forward(request, response);
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
