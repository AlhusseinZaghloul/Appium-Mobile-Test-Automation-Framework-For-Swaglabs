package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Step;
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

    @Step("Entering username: {text}")
    public LoginPage enterUsername(String text) {
        ElementsActions.sendData(driver,username,text);
        return this;
    }

    @Step("Entering password: {text}")
    public LoginPage enterPassword(String text) {
        ElementsActions.sendData(driver,password,text);
        return this;
    }

    @Step("Clicking on login button")
    public ProductsPage clickOnLogin() {
        ElementsActions.clicking(driver,loginButton);
        return new ProductsPage(driver);
    }

    @Step("Getting error message")
    public String getErrorMessage(){
        return ElementsActions.getAttributeFromElement(driver,errorMessage,"text");
    }

    @Step("Getting username element")
    public WebElement loginButton() {
        return  Waits.waitForElementVisible(driver,loginButton);
    }

    @Step("Performing login")
    public ProductsPage login (String username, String password){
        this.enterUsername(username);
        this.enterPassword(password);
        return this.clickOnLogin();
    }



}