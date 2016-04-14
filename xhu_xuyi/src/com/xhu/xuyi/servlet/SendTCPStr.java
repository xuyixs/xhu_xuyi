package com.xhu.xuyi.servlet;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xhu.xuyi.TCPServer.BlockingQueue;
import com.xhu.xuyi.entity.User;
import com.xhu.xuyi.entity.UserOxFe;
import com.xhu.xuyi.service.SettingService;

/**
 * 通过Servlet向已经和服务端保持TCP联接的客户端发送数据
 */
@WebServlet("/SendTCPStr")
public class SendTCPStr extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	List<Object> objList = null;
	
	//需要传输的信号
	String tcpStr = null;
	//在SendTCPStr这个Servlet初始化的时候启动Socekt
	public void init() throws ServletException {
		
	}
       
	/**
	 * 
	 * 
	 */
	protected void doGet(HttpServletRequest request
			, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
//		final List<Object> objList;
		
		User sysUser = (User)request.getSession().getAttribute("sysUser");
		String userId = sysUser.getUserId();
		String userIp = sysUser.getUserIp();
		int userPort = sysUser.getUserPort();
		
		String oxygenTime = request.getParameter("oxygenTime");
		String feedTime = request.getParameter("feedTime");
		String oxygenNow = request.getParameter("oxygenNow");
		String feedNow = request.getParameter("feedNow");
		
		//现在充氧或者现在投食
		if(oxygenNow != null && oxygenNow.equals("") != true){
			objList = new ArrayList<Object>();
			tcpStr = oxygenNow;
			objList.add(tcpStr);
			objList.add(userIp);
			objList.add(userPort);
			try {
				BlockingQueue.linkedBlockingQueue.put(objList);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			//响应Home.jsp的Ajax请求 ，用于页面的回调函数
			ServletOutputStream outputStream = response.getOutputStream();
			outputStream.write("success".getBytes());
			
//			this.writeToClient(objList);
			return;
		}else if(feedNow != null && feedNow.equals("") != true){
			objList = new ArrayList<Object>();
			tcpStr = feedNow;
			objList.add(tcpStr);
			objList.add(userIp);
			objList.add(userPort);
			
			try {
				BlockingQueue.linkedBlockingQueue.put(objList);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			//响应Home.jsp的Ajax请求 ，用于页面的回调函数
			ServletOutputStream outputStream = response.getOutputStream();
			outputStream.write("success".getBytes());
			
//			this.writeToClient(objList);
			return;
		}
		
		//要把这个以参数形式从其他Servlet传过来的String通过TCP传到客户端
		if(oxygenTime != null && oxygenTime.equals("") != true 
				&& feedTime != null && feedTime.equals("") != true){
			
			StringBuilder sb = new StringBuilder();
			sb.append(oxygenTime).append("&").append(feedTime);
			
			tcpStr = sb.toString();
			
			objList = new ArrayList<Object>();
			objList.add(tcpStr);
			objList.add(userIp);
			objList.add(userPort);
			
			try {
				BlockingQueue.linkedBlockingQueue.put(objList);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
//			this.writeToClient(objList);
			
			//同时保存或更新数据库中的设置 
			UserOxFe userOxFe = new UserOxFe();
			userOxFe.setUserId(userId);
			userOxFe.setOxygenTime(oxygenTime);
			userOxFe.setFeedTime(feedTime);
			SettingService settingService = new SettingService();
			settingService.saveOrUpdateUserOxFe(userOxFe);
			
			//同时保存到Session中
			request.getSession().setAttribute("userOxFe", userOxFe);
			
		}else{
			//测试使用的tcpStr
			tcpStr = "1&2";
			objList = new ArrayList<Object>();
			objList.add(tcpStr);
			objList.add("192.168.1.104");
			objList.add(8266);
			
			try {
				BlockingQueue.linkedBlockingQueue.put(objList);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
//			this.writeToClient(objList);
		}
		
		response.sendRedirect("home.jsp");
	}
	
	//抽取方法向客户端写出数据------对象流
//	private void writeToClient(final List<Object> objList) {
//		new Thread(){
//			public void run(){
//				try {
//					OutputStream out = socket.getOutputStream();
//					ObjectOutputStream oos = new ObjectOutputStream(out);
//					oos.writeObject(objList);
//					
//					oos.flush();
//					
//					System.out.println("显示Servlet数据送到ServerSocket了");
//					
//					objList.removeAll(objList);
//					
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			};
//		}.start();
//	}

	protected void doPost(HttpServletRequest request
			, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	
}
