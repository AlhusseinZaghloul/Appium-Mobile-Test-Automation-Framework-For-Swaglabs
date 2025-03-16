package tests;

import drivers.DriverFactory;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.LoginPage;
import pages.ProductPage;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

public class LoginTest {
    // Server instance for Appium managing the local server.
    private  AppiumDriverLocalService service;
    // Driver instance for Android interactions
    private AndroidDriver driver;
    LoginPage loginPage;

    /**
     * Starts Appium server before all test classes
     */
    @BeforeClass
    public void setupServer() {
       this.service= DriverFactory.startServer();
    }

    /**
     * Configures and initializes Android driver before each test method
     * Sets up device capabilities and application under test
     */
    @BeforeMethod
    public void setupDriver() throws URISyntaxException, MalformedURLException {
        this.driver= DriverFactory.setupDriver();
        loginPage = new LoginPage(driver);
    }
    /**
     * Test scenario demonstrating basic UI interactions:
     * - Navigating through menus
     * - Scrolling to elements
     * - Text input operations
     */
    @Test
    public void testValidLogin() throws InterruptedException {

       loginPage
                 .enterUsername("standard_user")
                 .enterPassword("secret_sauce")
                 .clickLogin();

        ProductPage productPage = new ProductPage(driver);
        String expectedHeader="PRODUCTS";

        Assert.assertEquals( productPage.getPageTitle(), expectedHeader);
    }
    @Test
    public void testInvalidLogin() throws InterruptedException {

        loginPage
                .enterUsername("standard_user")
                .enterPassword("43243243242324324")
                .clickLogin();

        String expectedText="Username and password do not match any user in this service.";
        Assert.assertEquals( loginPage.getErrorMessage(), expectedText);
    }
    /**
     * Cleans up driver after each test method
     */
    @AfterMethod
    public void quitDriver() {
        if (driver != null) {
            driver.quit();
        }
    }
    /**
     * Stops Appium server after all test classes
     */
    @AfterClass
    public void quitServer() {
        if (service != null && service.isRunning()) {
            service.stop();
        }
    }

}
