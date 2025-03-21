package tests;

import drivers.DriverFactory;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
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
        String actualHeader=productPage.getPageTitle();
        Assert.assertEquals( actualHeader, expectedHeader, "Login failed");
    }
    @Test
    public void testInvalidLogin() {

        loginPage
                .enterUsername(jsonReader.getValue("valid_username"))
                .enterPassword(jsonReader.getValue("invalid_password"))
                .clickLogin();

        String actualErrorMessage = loginPage.getErrorMessage();
        String expectedErrorMessage= jsonReader.getValue("invalid_login_error_message");
        Assert.assertEquals( actualErrorMessage, expectedErrorMessage, "Error message not displayed correctly");
    }
    @Test
    public void testValidLoginAfterInvalidCredentials() {
        SoftAssert softAssert = new SoftAssert();

        // Attempt login with invalid credentials
        loginPage
                .enterUsername(jsonReader.getValue("valid_username"))
                .enterPassword(jsonReader.getValue("invalid_password"))
                .clickLogin();

        // Soft assert for error message
        String expectedErrorMessage = jsonReader.getValue("invalid_login_error_message");
        String actualErrorMessage = loginPage.getErrorMessage();
        softAssert.assertEquals(actualErrorMessage, expectedErrorMessage, "Error message not displayed correctly");

        // Attempt login with valid credentials
        loginPage
                .enterUsername(jsonReader.getValue("valid_username"))
                .enterPassword(jsonReader.getValue("valid_password"))
                .clickLogin();

        // Verify successful login
        ProductPage productPage = new ProductPage(driver);
        String expectedHeader = jsonReader.getValue("product_page_header");
        Assert.assertEquals(productPage.getPageTitle(), expectedHeader, "Valid login failed after invalid attempt");

        // Collect all soft assertion results
        softAssert.assertAll();
    }
    /**
     * Cleans up driver after each test method
     */
    @AfterMethod
    public void quitDriver(ITestResult result) {
        // Quit driver
        if (driver != null) {
            driverFactory.quitDriver();
        }
        // Capture screenshot on failure
        if (result.getStatus() == ITestResult.FAILURE) {
            String testName = result.getMethod().getMethodName();
            ScreenshotUtils.captureScreenshot(driver, testName);
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
