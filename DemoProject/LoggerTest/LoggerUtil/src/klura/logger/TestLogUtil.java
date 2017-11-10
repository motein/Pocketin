package klura.logger;

import java.util.logging.Logger;

public class TestLogUtil {

	 private static Logger logger = LoggerUtil.getLogger(TestLogUtil.class.getName());
	 public static void main(String[] args) {
		 logger.info("test log");
		 System.out.println(logger.getName());
	 }
}
