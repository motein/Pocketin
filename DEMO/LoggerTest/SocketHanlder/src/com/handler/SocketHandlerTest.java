package com.handler;

import java.io.IOException;
import java.util.logging.Logger;
import java.util.logging.SocketHandler;

public class SocketHandlerTest {

	private SocketHandler handler = null;
	
	private static Logger logger = Logger.getLogger(SocketHandlerTest.class.getName());
	
	public SocketHandlerTest(String host, int port) {
		try {
			handler = new SocketHandler(host, port);
			logger.addHandler(handler);
			logger.info("SocketHandler has been launched successfully...");
		} catch (IOException e) {
			logger.severe("Failed to launch SocketHandler...");
			StringBuilder sb = new StringBuilder();
			sb.append(e.toString()).append("\n");
			for (StackTraceElement elem : e.getStackTrace()) {
				sb.append("\tat").append(elem).append("\n");
			}
			
			logger.severe(sb.toString());
		}
	}
	
	public static void main(String args[]) {
		// System.out.print(SocketHandlerTest.class.getName());
		new SocketHandlerTest("localhost", 8080);
	}
}
