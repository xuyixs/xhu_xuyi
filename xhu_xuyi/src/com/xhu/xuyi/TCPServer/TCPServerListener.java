package com.xhu.xuyi.TCPServer;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * ʹ��Timer����ServerSocket����ֹserverSocket.accept()��������
 * �޷�����Web����
 * @author xuyi
 *
 */
@WebListener
public class TCPServerListener implements ServletContextListener {
	
	public ServletContext servletContext;
	
	/**
	 * ��TCPServer��ΪTaskͨ��Timer��ʱ5000��������TCPServer�����
	 */
	public void contextInitialized(ServletContextEvent event) {
		
		TCPStartTimer startTimer = new TCPStartTimer();
		
		long delay = 3000l;
		TCPServerTask task = new TCPServerTask();
		startTimer.schedule(task, delay);
		
		WebClientStartTimer clientTimer = new WebClientStartTimer();
		long delayWebClient = 5000l;
		WebClientTask webClientTask = new WebClientTask();
		clientTimer.schedule(webClientTask, delayWebClient);
		
	}
	
	/**
	 * �ر�������ʱ��ر�ServerSocket�����Ķ˿�
	 */
	public void contextDestroyed(ServletContextEvent event) {
		new TCPServer().TCPServerClose();
		new BlockingQueue().sendToServerSocketClose();
	}


}
