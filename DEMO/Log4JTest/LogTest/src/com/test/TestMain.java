package com.test;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;

public class TestMain {

	private static Logger log4j = Logger.getLogger(TestMain.class.getName());
	
	public static void main(String[] args) {
		System.out.println("Hello, this is a test");
		System.out.println(System.getProperty("user.dir"));
		printLog();
	}
	
	private static void printLog() {
		//BasicConfigurator.configure();
		//PropertyConfigurator.configure("Libs/log4j/log4j.properties");
		DOMConfigurator.configure("Libs/log4j/log4j.xml");
        log4j.debug("log4j debug");  
        log4j.info("log4j info");  
        log4j.warn("log4j warn");  
        log4j.error("log4j error");  
        log4j.fatal("log4j fatal");
	}

}
