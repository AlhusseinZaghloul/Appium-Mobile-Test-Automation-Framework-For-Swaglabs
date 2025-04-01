package tests;

import drivers.DriverFactory;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import listeners.TestNGListeners;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import pages.LoginPage;
import pages.ProductDetailsPage;
import pages.ProductsPage;
import utils.JsonReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

@Listeners(TestNGListeners.class)
public class ProductDetailsTest {
    // Server instance for Appium managing the local server.
    private AppiumDriverLocalService service;
    // Driver instance for Android interactions
    private AndroidDriver driver;
    private JsonReader jsonReader;
    private LoginPage loginPage;
    private ProductsPage productsPage;
    private ProductDetailsPage productDetailsPage;

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
        driver= DriverFactory.setupDriver();
        loginPage = new LoginPage(driver);
    }

    @Test( description = "Verifies product details displayed in product details page successfully")
    public void testProductDetailsDisplayedSuccessfully() {
        //Navigate to product details page after login
        productsPage = new ProductsPage(driver);
        productsPage.navigateToProductDetailsPage
                (jsonReader.getValue("valid_username"), jsonReader.getValue("valid_password"));

        //verify product details
        productDetailsPage = new ProductDetailsPage(driver);
        String actualProductDescription = productDetailsPage.getProductDescription();
        String actualProductTitle =productDetailsPage.getProductTitle();
        String expectedProductDescription = jsonReader.getValue("product_description");
        String expectedProductTitle = jsonReader.getValue("product_title");

        // Assert for texts
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(actualProductDescription, expectedProductDescription, "Product description mismatch");
        softAssert.assertEquals(actualProductTitle, expectedProductTitle, "Product title mismatch");
        softAssert.assertAll();
    }
    @Test(description = "Verifies back to products button functionality in product details page")
    public void testBackToProductsButtonFunctionality() {
        //Navigate to product details page after login
        productsPage = new ProductsPage(driver);
        productsPage.navigateToProductDetailsPage
                (jsonReader.getValue("valid_username"), jsonReader.getValue("valid_password"));

        //verify product details
        productDetailsPage = new ProductDetailsPage(driver);
        String actualProductDescription = productDetailsPage.getProductDescription();
        String actualProductTitle =productDetailsPage.getProductTitle();
        String expectedProductDescription = jsonReader.getValue("product_description");
        String expectedProductTitle = jsonReader.getValue("product_title");

        // Assert for texts
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(actualProductDescription, expectedProductDescription, "Product description mismatch");
        softAssert.assertEquals(actualProductTitle, expectedProductTitle, "Product title mismatch");

        // Click on back to products page button
        productDetailsPage.clickBackToProducts();

        // Verify products page title
        String actualPageTitle = productsPage.getPageTitle();
        String expectedPageTitle = jsonReader.getValue("product_page_title");
        softAssert.assertEquals(actualPageTitle, expectedPageTitle, "Page title mismatch");
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
