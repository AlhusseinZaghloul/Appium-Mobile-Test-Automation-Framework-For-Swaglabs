package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.ElementsActions;
import utils.Waits;

public class LoginPage {

    private final AndroidDriver driver;
    // Define locators
    private final By username = AppiumBy.accessibilityId("test-Username");
    private final By password = AppiumBy.accessibilityId("test-Password");
    private final By loginButton = AppiumBy.accessibilityId("test-LOGIN");
    private final By errorMessage = AppiumBy.xpath("//android.widget.TextView[@text=\"Username and password do not match any user in this service.\"]");

    // Constructor
    public LoginPage(AndroidDriver driver) {
        this.driver = driver;
    }

    public LoginPage enterUsername(String text) {
        ElementsActions.sendData(driver,username,text);
        return this;
    }

    public LoginPage enterPassword(String text) {
        ElementsActions.sendData(driver,password,text);
        return this;
    }

    public ProductsPage clickOnLogin() {
        ElementsActions.clicking(driver,loginButton);
        return new ProductsPage(driver);
    }

    public String getErrorMessage(){
        return ElementsActions.getAttributeFromElement(driver,errorMessage,"text");
    }
    public WebElement loginButton() {
        return  Waits.waitForElementVisible(driver,loginButton);
    }



}