package tests;

import drivers.DriverFactory;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

public class LoginTest {
    // Server instance for Appium managing the local server.
    private  AppiumDriverLocalService service;
    // Driver instance for Android interactions
    private AndroidDriver driver;

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
    }
    /**
     * Test scenario demonstrating basic UI interactions:
     * - Navigating through menus
     * - Scrolling to elements
     * - Text input operations
     */
    @Test
    public void testValidLogin() throws InterruptedException {
        // Define locators
        final By username = AppiumBy.accessibilityId("test-Username");
        final By password = AppiumBy.accessibilityId("test-Password");
        final By loginButton = AppiumBy.accessibilityId("test-LOGIN");
        final By productsLabel = AppiumBy.xpath("//android.widget.TextView[@text=\"PRODUCTS\"]");

        // Perform actions
        Thread.sleep(Long.parseLong("5000"));
        driver.findElement(username).sendKeys("standard_user");
        driver.findElement(password).sendKeys("secret_sauce");
        driver.findElement(loginButton).click();

        String productsHeader= driver.findElement(productsLabel).getDomAttribute("text");
        String expectedHeader="PRODUCTS";

        Assert.assertEquals( productsHeader, expectedHeader);
    }
    @Test
    public void testInvalidLogin() throws InterruptedException {
        // Define locators
        final By username = AppiumBy.accessibilityId("test-Username");
        final By password = AppiumBy.accessibilityId("test-Password");
        final By loginButton = AppiumBy.accessibilityId("test-LOGIN");
        final By invalidMessage= AppiumBy.xpath("//android.widget.TextView[@text=\"Username and password do not match any user in this service.\"]");

        // Perform actions
        Thread.sleep(Long.parseLong("5000"));
        driver.findElement(username).sendKeys("standard_user");
        driver.findElement(password).sendKeys("secret_sauce123232");
        driver.findElement(loginButton).click();

        String errorText= driver.findElement(invalidMessage).getDomAttribute("text");
        String expectedText="Username and password do not match any user in this service.";

        Assert.assertEquals( errorText, expectedText);
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
