package utils;

import io.qameta.allure.Allure;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Files;

public class AllureUtils {
    private AllureUtils(){}

    public static final String ALLURE_RESULTS_PATH = "test-outputs/allure-results";

    public static void attachLogsToAllureReport(){

        // Get the latest log file from the logs directory
        File logFile = FilesUtils.getLatestFile(LogsUtils.LOGS_PATH);
        // Check if the log file exists
        if (logFile != null && logFile.exists()){
            try (InputStream is = new FileInputStream(logFile)) {
                Allure.addAttachment("Execution Logs", Files.readString(logFile.toPath()));
            } catch (FileNotFoundException e) {
                LogsUtils.error("Log file not found at: ", logFile.getAbsolutePath());
            } catch (Exception e) {
                LogsUtils.error("Failed to attach logs to Allure report: ", e.getMessage());
            }
        } else {
            LogsUtils.warn("No log file found in directory: ", LogsUtils.LOGS_PATH);
        }
    }
}
