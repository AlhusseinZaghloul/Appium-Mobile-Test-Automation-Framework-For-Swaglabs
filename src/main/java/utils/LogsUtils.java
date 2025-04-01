package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Utility class for providing logging functionalities using Log4j 2.
 * This class provides static methods to easily log messages at different severity levels.
 */
public class LogsUtils {
    //Private constructor to prevent instantiation of this utility class.
    private LogsUtils() {}

    public static final String LOGS_PATH = "test-outputs/Logs";

    /**
     * Retrieves the Logger instance for the class that calls this method.
     * This approach ensures that log messages are associated with the originating class,
     * making it easier to trace the source of the logs.
     *
     * @return The Logger instance for the calling class.
     */
    private static Logger logger() {
        // Get the logger for the class that calls this method
        return LogManager.getLogger(Thread.currentThread().getStackTrace()[3].getClassName());
    }

    public static void trace(String... message) {
        logger().trace(String.join(" ", message));
    }

    public static void debug(String... message) {
        logger().debug(String.join(" ", message));
    }

    public static void info(String... message) {
        logger().info(String.join(" ", message));
    }

    public static void warn(String... message) {
        logger().warn(String.join(" ", message));
    }

    public static void error(String... message) {
        logger().error(String.join(" ", message));
    }

    public static void fatal(String... message) {
        logger().fatal(String.join(" ", message));
    }

}
