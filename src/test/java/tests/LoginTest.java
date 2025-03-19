package tests;

import drivers.DriverFactory;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pages.LoginPage;
import pages.ProductPage;
import utils.JsonReader;
import utils.ScreenshotUtils;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

public class LoginTest {

    DriverFactory driverFactory = new DriverFactory();
    // Server instance for Appium managing the local server.
    private  AppiumDriverLocalService service;
    // Driver instance for Android interactions
    private AndroidDriver driver;
    private JsonReader jsonReader;
    LoginPage loginPage;

    /**
     * Starts Appium server before all test classes
     */
    @BeforeClass
    public void setupServer() {
       service= driverFactory.startServer();
       jsonReader = new JsonReader("testData.json");
    }

    /**
     * Configures and initializes Android driver before each test method
     * Sets up device capabilities and application under test
     */
    @BeforeMethod
    public void setupDriver() throws URISyntaxException, MalformedURLException {
        driver= driverFactory.setupDriver();
        loginPage = new LoginPage(driver);
    }

    @Test
    public void testValidLogin() {

       loginPage
                 .enterUsername(jsonReader.getValue("valid_username"))
                 .enterPassword(jsonReader.getValue("valid_password"))
                 .clickLogin();

        ProductPage productPage = new ProductPage(driver);
        String expectedHeader=jsonReader.getValue("product_page_header");;

        Assert.assertEquals( productPage.getPageTitle(), expectedHeader);
    }
    @Test
    public void testInvalidLogin() {

        loginPage
                .enterUsername(jsonReader.getValue("valid_username"))
                .enterPassword(jsonReader.getValue("invalid_password"))
                .clickLogin();

        String expectedText= jsonReader.getValue("invalid_login_error_message");
        Assert.assertEquals( loginPage.getErrorMessage(), expectedText);
    }
    /**
     * Cleans up driver after each test method
     */
    @AfterMethod
    public void quitDriver(ITestResult result) {
        // Capture screenshot on failure
        if (result.getStatus() == ITestResult.FAILURE) {
            String testName = result.getMethod().getMethodName();
            ScreenshotUtils.captureScreenshot(driver, testName);
        }

        // Quit driver
        if (driver != null) {
            driverFactory.quitDriver();
        }
    }
    /**
     * Stops Appium server after all test classes
     */
    @AfterClass
    public void quitServer() {
        if (service != null && service.isRunning()) {
            driverFactory.stopServer();
        }
    }

}
