package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import utils.ElementsActions;

public class ProductDetailsPage {

    private final AndroidDriver driver;
    // Define locators
    By productTitle = AppiumBy.androidUIAutomator("new UiSelector().text(\"Sauce Labs Backpack\")");
    By productDescription = AppiumBy.androidUIAutomator("new UiSelector().text(\"carry.allTheThings() with the sleek, streamlined Sly Pack that melds uncompromising style with unequaled laptop and tablet protection.\")");
    By backToProductsButton = AppiumBy.androidUIAutomator("new UiSelector().text(\"BACK TO PRODUCTS\")");

    // Constructor
    public ProductDetailsPage(AndroidDriver driver) {
        this.driver=driver;
    }
    public String getProductTitle(){
        return ElementsActions.getAttributeFromElement(driver,productTitle,"text");
    }
    public String getProductDescription(){
        return ElementsActions.getAttributeFromElement(driver,productDescription,"text");
    }
    public ProductsPage clickBackToProducts(){
        ElementsActions.clicking(driver,backToProductsButton);
        return new ProductsPage(driver);
    }
}
