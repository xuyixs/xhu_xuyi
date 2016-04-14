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
 * ͨ��Servlet���Ѿ��ͷ���˱���TCP���ӵĿͻ��˷�������
 */
@WebServlet("/SendTCPStr")
public class SendTCPStr extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	List<Object> objList = null;
	
	//��Ҫ������ź�
	String tcpStr = null;
	//��SendTCPStr���Servlet��ʼ����ʱ������Socekt
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
		
		//���ڳ�����������Ͷʳ
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
			
			//��ӦHome.jsp��Ajax���� ������ҳ��Ļص�����
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
			
			//��ӦHome.jsp��Ajax���� ������ҳ��Ļص�����
			ServletOutputStream outputStream = response.getOutputStream();
			outputStream.write("success".getBytes());
			
//			this.writeToClient(objList);
			return;
		}
		
		//Ҫ������Բ�����ʽ������Servlet��������Stringͨ��TCP�����ͻ���
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
			
			//ͬʱ�����������ݿ��е����� 
			UserOxFe userOxFe = new UserOxFe();
			userOxFe.setUserId(userId);
			userOxFe.setOxygenTime(oxygenTime);
			userOxFe.setFeedTime(feedTime);
			SettingService settingService = new SettingService();
			settingService.saveOrUpdateUserOxFe(userOxFe);
			
			//ͬʱ���浽Session��
			request.getSession().setAttribute("userOxFe", userOxFe);
			
		}else{
			//����ʹ�õ�tcpStr
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
	
	//��ȡ������ͻ���д������------������
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
//					System.out.println("��ʾServlet�����͵�ServerSocket��");
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
