package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import utils.ElementsActions;

public class ProductsPage {

    private final AndroidDriver driver;
    // Define locators
    private final By productsLabel = AppiumBy.xpath("//android.widget.TextView[@text=\"PRODUCTS\"]");
    private final By sauseLabsBackPackTitle=AppiumBy.androidUIAutomator("new UiSelector().text(\"Sauce Labs Backpack\")");
    private final By sauseLabsBackPackPrice=AppiumBy.androidUIAutomator("new UiSelector().text(\"$29.99\")");
    private final By menuButton=AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.ImageView\").instance(1)");

    // Constructor
    public ProductsPage(AndroidDriver driver) {
        this.driver = driver;
    }

    @Step("Getting page title")
    public String getPageTitle() {
        return ElementsActions.getAttributeFromElement(driver,productsLabel,"text");
    }

    @Step("Getting product title")
    public String getSauseLabsBackPackTitle() {
        return ElementsActions.getAttributeFromElement(driver,sauseLabsBackPackTitle,"text");
    }

    @Step("Getting product price")
    public String getSauseLabsBackPackPrice() {
        return ElementsActions.getAttributeFromElement(driver,sauseLabsBackPackPrice,"text");
    }

    @Step("Clicking on menu button")
    public MenuPage clickOnMenuButton(){
        ElementsActions.clicking(driver,menuButton);
        return new MenuPage(driver);
    }

    private ProductDetailsPage clickOnSauseLabsBackPackTitle(){
        ElementsActions.clicking(driver,sauseLabsBackPackTitle);
        return new ProductDetailsPage(driver);
    }

    @Step("Navigating to product details page")
    public ProductDetailsPage navigateToProductDetailsPage(String username, String password) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);
        return this.clickOnSauseLabsBackPackTitle();
    }
}
