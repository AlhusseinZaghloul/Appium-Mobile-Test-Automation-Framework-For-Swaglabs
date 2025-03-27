package utils;

import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class ElementsActions {


    //code to perform sendKeys operation
    @Step("Sending data: {data} to element: {locator}")
    public static void sendData(AndroidDriver driver , By locator, String data) {
        Waits.waitForElementVisible(driver, locator);
        driver.findElement(locator).sendKeys(data);
        LogsUtils.info("Data entered: ", data , " in element: ", locator.toString());
    }

    @Step("Clicking on element: {locator}")
    //code to perform click operation
    public static void clicking(AndroidDriver driver , By locator) {
        Waits.waitForElementClickable(driver, locator);
        driver.findElement(locator).click();
        LogsUtils.info("Clicked on element: ", locator.toString());
    }

    @Step("Getting attribute from element: {locator}")
    //get attribute from element
    public static String getAttributeFromElement(AndroidDriver driver , By locator , String attribute) {
        Waits.waitForElementVisible(driver, locator);
        LogsUtils.info("Getting attribute: ", attribute , " from element: ", locator.toString());
        return driver.findElement(locator).getDomAttribute(attribute);
    }
}
