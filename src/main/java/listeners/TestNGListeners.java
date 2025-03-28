package listeners;

import drivers.DriverManagerUtils;
import io.appium.java_client.android.AndroidDriver;
import org.testng.*;
import utils.AllureUtils;
import utils.FilesUtils;
import utils.LogsUtils;
import utils.ScreenshotUtils;

import java.io.File;

public class TestNGListeners implements IExecutionListener, ITestListener, IInvokedMethodListener {

    @Override
    public void onExecutionStart() {
        LogsUtils.info("Test Execution started");
        FilesUtils.deleteFiles(new File(AllureUtils.ALLURE_RESULTS_PATH));
        FilesUtils.cleanDirectory(new File(LogsUtils.LOGS_PATH));
        FilesUtils.cleanDirectory(new File(ScreenshotUtils.SCREENSHOTS_PATH));

    }

    @Override
    public void onExecutionFinish() {
        LogsUtils.info("Test Execution finished");
        //AllureUtils.attachLogsToAllureReport();

    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isTestMethod()) {
            AllureUtils.attachLogsToAllureReport();
        }
    }


    @Override
    public void onTestSuccess(ITestResult result) {
        LogsUtils.info("Test case" , result.getName() , "passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        LogsUtils.info("Test case" , result.getName() , "failed");
        AndroidDriver driver = DriverManagerUtils.getDriver();
        if (driver != null) {
            ScreenshotUtils.captureScreenshot(driver, result.getName());
        } else {
            LogsUtils.info("Driver is null, cannot take screenshot for test " + result.getName());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        LogsUtils.info("Test case" , result.getName() , "skipped");

    }


}
