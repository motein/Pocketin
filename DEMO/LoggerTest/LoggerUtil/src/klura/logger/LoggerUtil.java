package klura.logger;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerUtil {
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	private static final String LOG_FOLDER_NAME = "Logging";
	private static final String LOG_FILE_SUFFIX = ".log";
	
	public synchronized static Logger getLogger(String name) {
		return LoggerUtil.setLoggerHandler(Logger.getLogger(name));
	}
	
	public synchronized static Logger getLogger(String name, Level level) {
		return LoggerUtil.setLoggerHandler(Logger.getLogger(name), level);
	}
	
	private synchronized static Logger setLoggerHandler(Logger logger) {
		return setLoggerHandler(logger, Level.ALL);
	}
	
	private synchronized static Logger setLoggerHandler(Logger logger, Level level) {
		FileHandler fileHandler = null;
		try {
			fileHandler = new FileHandler(getLogFilePath(), true);
			fileHandler.setFormatter(new SimpleFormatter());
			
			logger.addHandler(fileHandler);
			logger.setLevel(level);
		} catch (SecurityException e) {
			logger.severe(populateExceptionStackTrace(e)); 
		} catch (IOException e) {
			logger.severe(populateExceptionStackTrace(e)); 
		}
		
		return logger;
	}
	
	private synchronized static String getLogFilePath() {
		StringBuffer logFilePath = new StringBuffer();
		logFilePath.append(System.getenv("ProgramData"));
		logFilePath.append(File.separatorChar);
		logFilePath.append(LOG_FOLDER_NAME);
		
		File file = new File(logFilePath.toString());
		if (!file.exists())
			file.mkdir();
		
		logFilePath.append(File.separatorChar);
		logFilePath.append(sdf.format(new Date()));
		logFilePath.append(LOG_FILE_SUFFIX);
		
		return logFilePath.toString();
	}
	
	private synchronized static String populateExceptionStackTrace(Exception e) {
		StringBuilder sb = new StringBuilder();
		sb.append(e.toString()).append("\n");
		for (StackTraceElement element : e.getStackTrace()) {
			sb.append("\tat").append(element).append("\n");
		}
		
		return sb.toString();
	}
}
