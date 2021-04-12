import java.util.logging.Level;
import java.util.logging.Logger;

public class 日志 {

    //private static final Logger myLogger = Logger.getLogger("Hello.com");
    public static void main(String[] args) {
        Logger.getGlobal().setLevel(Level.OFF);
        Logger.getGlobal().info("I am testing log...");
    }
}
