package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import utils.ElementsActions;

public class ProductPage {

    private final AndroidDriver driver;
    private final By productsLabel = AppiumBy.xpath("//android.widget.TextView[@text=\"PRODUCTS\"]");

    public ProductPage(AndroidDriver driver) {
        this.driver = driver;
    }

    public String getPageTitle() {
        return ElementsActions.getAttributeFromElement(driver,productsLabel,"text");
    }

}
