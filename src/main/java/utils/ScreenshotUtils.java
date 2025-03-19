package utils;

import io.appium.java_client.AppiumDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenshotUtils {

    public static void captureScreenshot(AppiumDriver driver, String testName) {
        if (driver == null) return;

        // Create screenshots directory if it doesn't exist
        File directory = new File("screenshots");
        if (!directory.exists()) directory.mkdirs();

        // Generate timestamp
        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String fileName = testName + "_" + timestamp + ".png";

        try {
            File srcFile = driver.getScreenshotAs(OutputType.FILE);
            File destFile = new File(directory, fileName);
            FileUtils.copyFile(srcFile, destFile);
            System.out.println("Screenshot saved: " + destFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}