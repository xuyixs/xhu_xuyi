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
	 * ����Post��ʽ�ύ�û����û��������룬��������������ȥ���ݿ��ѯUser�����صĶ���ŵ�Session��
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
			System.out.println("��¼ҳ��û���ύ���ݣ��ݶ����û��������롣");
			userName = "xuyi";
			password = "123";
		}
		user = userService.findUserByUserName(userName);
		if(user == null){
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}else{
			//������userName��Ϊ�û������û��������û���������ͬʱ��ѯ���ݿ�
			user = userService.findUserByUserNameAndPassword(userName, password);
			if(user == null){
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}else{
				//��¼�ɹ������û���Ϣ���뵽Session��
				request.getSession().setAttribute("sysUser", user);
				
				//���ҳ��û������Ͷʳ�ͳ�����ʱ�䲢���浽Sesstion��
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
