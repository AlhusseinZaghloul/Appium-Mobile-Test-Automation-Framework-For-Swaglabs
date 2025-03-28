package drivers;

import io.appium.java_client.android.AndroidDriver;

public class DriverManagerUtils {
    private static final ThreadLocal<AndroidDriver> driver = new ThreadLocal<>();

    public static AndroidDriver getDriver() {
        return driver.get();
    }

    public static void setDriver(AndroidDriver androidDriver) {
        driver.set(androidDriver);
    }

    public static void removeDriver() {
        driver.remove();
    }

}
