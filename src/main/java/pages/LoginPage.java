package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

public class LoginPage {
    private final AndroidDriver driver;
    private final By username = AppiumBy.accessibilityId("test-Username");
    private final By password = AppiumBy.accessibilityId("test-Password");
    private final By loginButton = AppiumBy.accessibilityId("test-LOGIN");

    public LoginPage(AndroidDriver driver) {
        this.driver = driver;
    }

    public void enterUsername(String value) {
        driver.findElement(username).sendKeys(value);
    }

    public void enterPassword(String value) {
        driver.findElement(password).sendKeys(value);
    }

    public void clickLogin() {
        driver.findElement(loginButton).click();
    }

    public void login(String user, String pass) throws InterruptedException {
        Thread.sleep(5000);
        enterUsername(user);
        enterPassword(pass);
        clickLogin();
    }
}