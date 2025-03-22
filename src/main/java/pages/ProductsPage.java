package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
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

    public String getPageTitle() {
        return ElementsActions.getAttributeFromElement(driver,productsLabel,"text");
    }

    public String getSauseLabsBackPackTitle() {
        return ElementsActions.getAttributeFromElement(driver,sauseLabsBackPackTitle,"text");
    }
    public String getSauseLabsBackPackPrice() {
        return ElementsActions.getAttributeFromElement(driver,sauseLabsBackPackPrice,"text");
    }
    public ProductDetailsPage clickOnSauseLabsBackPackTitle(){
        ElementsActions.clicking(driver,sauseLabsBackPackTitle);
        return new ProductDetailsPage(driver);
    }
    public MenuPage clickOnMenuButton(){
        ElementsActions.clicking(driver,menuButton);
        return new MenuPage(driver);
    }
}
