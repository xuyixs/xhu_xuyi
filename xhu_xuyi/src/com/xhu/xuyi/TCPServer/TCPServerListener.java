package com.xhu.xuyi.TCPServer;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * 使用Timer启动ServerSocket，防止serverSocket.accept()阻塞导致
 * 无法启动Web容器
 * @author xuyi
 *
 */
@WebListener
public class TCPServerListener implements ServletContextListener {
	
	public ServletContext servletContext;
	
	/**
	 * 将TCPServer作为Task通过Timer延时5000毫秒启动TCPServer服务端
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
	 * 关闭容器的时候关闭ServerSocket监听的端口
	 */
	public void contextDestroyed(ServletContextEvent event) {
		new TCPServer().TCPServerClose();
		new BlockingQueue().sendToServerSocketClose();
	}


}
