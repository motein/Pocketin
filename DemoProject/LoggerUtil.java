package logger;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerUtil {
	private static final SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd");

	private static final int LOG_LIMIT = 50000; // 50KB
	private static final int LOG_COUNT = 10; // 10 files to use
	private static final String LOG_FOLDER_NAME = "Logging";
	private static final String LOG_FILE_SUFFIX = ".log";

	public synchronized static Logger getLogger(String name) {
		return LoggerUtil.setLoggerHandler(Logger.getLogger(name));
	}

	public synchronized static Logger getLogger(String name,
			boolean outputToConsole) {
		return LoggerUtil.setLoggerHandler(Logger.getLogger(name), Level.ALL,
				outputToConsole);
	}

	public synchronized static Logger getLogger(String name, Level minLevel,
			boolean outputToConsole) {
		return LoggerUtil.setLoggerHandler(Logger.getLogger(name), minLevel,
				outputToConsole);
	}

	private synchronized static Logger setLoggerHandler(Logger logger) {
		return setLoggerHandler(logger, Level.ALL, false);
	}

	private synchronized static Logger setLoggerHandler(Logger logger,
			Level minLevel, boolean outputToConsole) {
		FileHandler fileHandler = null;
		try {
			fileHandler = new FileHandler(getLogFilePath(), LOG_LIMIT,
					LOG_COUNT, true);
			fileHandler.setFormatter(new SimpleFormatter());

			logger.addHandler(fileHandler);
			logger.setLevel(minLevel);
			logger.setUseParentHandlers(outputToConsole);
		} catch (SecurityException e) {
			logger.severe(populateExceptionStackTrace(e));
		} catch (IOException e) {
			logger.severe(populateExceptionStackTrace(e));
		}

		return logger;
	}

	/**
	 * Get the log filename
	 */
	private synchronized static String getLogFilePath() {
		StringBuffer logFilePath = new StringBuffer();
		logFilePath.append(System.getenv("ProgramData")); // C:\ProgramData
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

	/**
	 * Get the exception stack info
	 */
	private synchronized static String populateExceptionStackTrace(
			Exception e) {
		StringBuilder sb = new StringBuilder();
		sb.append(e.toString()).append("\n");
		for (StackTraceElement element : e.getStackTrace()) {
			sb.append("\tat").append(element).append("\n");
		}

		return sb.toString();
	}
}
