package tests;

import drivers.DriverFactory;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import listeners.TestNGListeners;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import pages.LoginPage;
import pages.ProductsPage;
import utils.JsonReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

@Listeners(TestNGListeners.class)
public class ProductsTest {

    // Server instance for Appium managing the local server.
    private AppiumDriverLocalService service;
    // Driver instance for Android interactions
    private AndroidDriver driver;
    private JsonReader jsonReader;
    private LoginPage loginPage;
    private ProductsPage productsPage;

    /**
     * Starts Appium server before all test classes
     */
    @BeforeClass
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
        driver = DriverFactory.setupDriver();
        loginPage = new LoginPage(driver);
    }

    @Test(description = "Verifies product details displayed in products page after successful login")
    public void testProductDisplayedAfterLogin() {
        //perform login
        loginPage
                .login(jsonReader.getValue("valid_username"), jsonReader.getValue("valid_password"));

        //verify product details
        productsPage = new ProductsPage(driver);
        String actualProductPrice=productsPage.getSauseLabsBackPackPrice();
        String actualProductTitle =productsPage.getSauseLabsBackPackTitle();
        String expectedProductPrice = jsonReader.getValue("product_price");
        String expectedProductTitle = jsonReader.getValue("product_title");

        // Assert for texts
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(actualProductPrice, expectedProductPrice, "Product price mismatch");
        softAssert.assertEquals(actualProductTitle, expectedProductTitle, "Product title mismatch");
        softAssert.assertAll();
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
