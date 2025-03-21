package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import utils.ElementsActions;

public class MenuPage {

    private final AndroidDriver driver;
    // Define locators
    By LogoutButton = AppiumBy.accessibilityId("test-LOGOUT");

    // Constructor
    public MenuPage(AndroidDriver driver) {
        this.driver = driver;
    }
    public LoginPage clickLogoutButton(){
        ElementsActions.clicking(driver,LogoutButton);
        return new LoginPage(driver);
    }


}
