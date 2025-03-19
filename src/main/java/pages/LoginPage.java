package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import utils.ElementsActions;

public class LoginPage {
    // Define locators
    private final AndroidDriver driver;
    private final By username = AppiumBy.accessibilityId("test-Username");
    private final By password = AppiumBy.accessibilityId("test-Password");
    private final By loginButton = AppiumBy.accessibilityId("test-LOGIN");
    private final By errorMessage = AppiumBy.xpath("//android.widget.TextView[@text=\"Username and password do not match any user in this service.\"]");



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

    public ProductPage clickLogin() {
        ElementsActions.clicking(driver,loginButton);
        return new ProductPage(driver);
    }

    public String getErrorMessage(){
        return ElementsActions.getAttributeFromElement(driver,errorMessage,"text");
    }


}