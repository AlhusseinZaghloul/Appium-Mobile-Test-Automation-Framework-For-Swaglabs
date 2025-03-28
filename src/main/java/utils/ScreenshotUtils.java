package utils;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenshotUtils {

    public static final String SCREENSHOTS_PATH = "test-outputs/screenshots/";
    private static final DateTimeFormatter TIMESTAMP_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");

    /**
     * Captures a screenshot using the provided driver and saves it with the test name and timestamp.
     * @param driver The AndroidDriver instance to capture the screenshot from.
     * @param testName The name of the test for naming the screenshot file.
     */
    public static void captureScreenshot(AndroidDriver driver, String testName) {
        if (driver == null) {
            LogsUtils.info("Driver is null, cannot capture screenshot for " + testName);
            return;
        }
        String sanitizedTestName = testName.replaceAll("[^a-zA-Z0-9_-]", "_");
        String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMAT);
        String fileName = String.format("%s_%s.png", sanitizedTestName, timestamp);
        String filePath = SCREENSHOTS_PATH + fileName;

        try {
            Files.createDirectories(Paths.get(SCREENSHOTS_PATH));
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.move(screenshot.toPath(), Paths.get(filePath));
            LogsUtils.info("Screenshot saved at: " + filePath);
        } catch (IOException e) {
            LogsUtils.info("Failed to save screenshot for " + testName + ": " + e.getMessage());
        }
    }
    }
