package tests;

import drivers.DriverFactory;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import listeners.TestNGListeners;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import pages.LoginPage;
import pages.MenuPage;
import pages.ProductsPage;
import utils.JsonReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

@Listeners(TestNGListeners.class)
public class LoginTest {

    // Server instance for Appium managing the local server.
    private  AppiumDriverLocalService service;
    // Driver instance for Android interactions
    private AndroidDriver driver;
    private JsonReader jsonReader;
    private LoginPage loginPage;

    /**
     * Starts Appium server before all test classes
     */
    @BeforeClass(alwaysRun = true)
    public void setupServer() {
       service= DriverFactory.startServer();
       jsonReader = new JsonReader("testData.json");
    }

    /**
     * Configures and initializes Android driver before each test method
     * Sets up device capabilities and application under test
     */
    @BeforeMethod
    public void setupDriver() throws URISyntaxException, MalformedURLException {
        driver= DriverFactory.setupDriver();
        loginPage = new LoginPage(driver);
    }

    @Test(description = "Verifies successful login with valid credentials and redirection to the product page")
    public void testValidLogin() {

       loginPage
                 .enterUsername(jsonReader.getValue("valid_username"))
                 .enterPassword(jsonReader.getValue("valid_password"))
                 .clickOnLogin();

        ProductsPage productsPage = new ProductsPage(driver);
        String expectedHeader=jsonReader.getValue("product_page_title");
        String actualHeader= productsPage.getPageTitle();
        Assert.assertEquals( actualHeader, expectedHeader, "Login failed: Product page header mismatch");
    }
    @Test(description = "Verifies that the correct error message is displayed for invalid login attempts")
    public void testInvalidLogin() {

        loginPage
                .enterUsername(jsonReader.getValue("valid_username"))
                .enterPassword(jsonReader.getValue("invalid_password"))
                .clickOnLogin();

        String actualErrorMessage = loginPage.getErrorMessage();
        String expectedErrorMessage= jsonReader.getValue("invalid_login_error_message");
        Assert.assertEquals( actualErrorMessage, expectedErrorMessage, "Error message not displayed correctly");
    }
    @Test(description ="Verifies that a user can log in successfully after a previous invalid login attempt")
    public void testValidLoginAfterInvalidCredentials() {
        SoftAssert softAssert = new SoftAssert();

        // Attempt login with invalid credentials
        loginPage
                .enterUsername(jsonReader.getValue("valid_username"))
                .enterPassword(jsonReader.getValue("invalid_password"))
                .clickOnLogin();

        // Soft assert for error message
        String expectedErrorMessage = jsonReader.getValue("invalid_login_error_message");
        String actualErrorMessage = loginPage.getErrorMessage();
        softAssert.assertEquals(actualErrorMessage, expectedErrorMessage, "Error message not displayed correctly");

        // Attempt login with valid credentials
        loginPage
                .enterUsername(jsonReader.getValue("valid_username"))
                .enterPassword(jsonReader.getValue("valid_password"))
                .clickOnLogin();

        // Verify successful login
        ProductsPage productsPage = new ProductsPage(driver);
        String expectedHeader = jsonReader.getValue("product_page_title");
        Assert.assertEquals(productsPage.getPageTitle(), expectedHeader, "Valid login failed after invalid attempt");

        // Collect all soft assertion results
        softAssert.assertAll();
    }
    @Test(description ="Verifies that a user can log out after logging in and is returned to the login screen")
    public void testLogout() {

        loginPage
                .enterUsername(jsonReader.getValue("valid_username"))
                .enterPassword(jsonReader.getValue("valid_password"))
                .clickOnLogin();

        ProductsPage productsPage = new ProductsPage(driver);
        String expectedHeader=jsonReader.getValue("product_page_title");
        String actualHeader= productsPage.getPageTitle();
        Assert.assertEquals(actualHeader, expectedHeader, "Login failed: Product page header mismatch");
        // Logout
        productsPage.clickOnMenuButton();
        MenuPage menuPage=new MenuPage(driver);
        menuPage.clickLogoutButton();

        // Verify logout
        boolean loginButtonDisplayed = loginPage.loginButton().isDisplayed();
        Assert.assertTrue(loginButtonDisplayed,"Logout failed: Login button is not displayed after logout");
    }
    /**
     * Cleans up driver after each test method
     */
    @AfterMethod(alwaysRun = true)
    public void quitDriver(ITestResult result) {
        DriverFactory.quitDriver();
    }
    /**
     * Stops Appium server after all test classes
     */
    @AfterClass(alwaysRun = true)
    public void quitServer() {
        DriverFactory.stopServer();
        }

}
