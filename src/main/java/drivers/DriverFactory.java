package drivers;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.qameta.allure.Step;
import utils.LogsUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.Properties;

/*
 * The DriverManager class provides utility methods for managing
 * the Appium server and creating an AndroidDriver instance.
 * This design encapsulates the server and driver setup, making
 * test classes cleaner and easier to maintain.
 */
public class DriverFactory {

    // Instance of the Appium service managing the local server.
    private AppiumDriverLocalService service;

    // Instance of AndroidDriver for interacting with the Android device.
    private AndroidDriver driver;

    /*
     * Starts the Appium server using the default service configuration.
     */
    @Step("Starting Appium server")
    public AppiumDriverLocalService startServer() {
        service = AppiumDriverLocalService.buildDefaultService();
        service.start();
        return service;
    }
    /*
     * Configures and initializes the AndroidDriver using UiAutomator2Options.
     *
     * @return the initialized AndroidDriver instance.
     * @throws URISyntaxException if the Appium server URI is invalid.
     * @throws MalformedURLException if the Appium server URL is malformed.
     */
    @Step("Setting up Android driver")
    public AndroidDriver setupDriver() throws URISyntaxException, MalformedURLException {

        // Load properties from config.properties
        Properties props = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new IOException("Unable to find config.properties in src/test/resources");
            }
            props.load(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Create options and set capabilities from properties
        UiAutomator2Options options = new UiAutomator2Options();
        options.setPlatformName(props.getProperty("platformName"));
        options.setDeviceName(props.getProperty("deviceName"));
        options.setAvd(props.getProperty("avd"));
        options.setAvdLaunchTimeout(Duration.ofSeconds(Long.parseLong(props.getProperty("avdLaunchTimeout"))));
        options.setAvdReadyTimeout(Duration.ofSeconds(Long.parseLong(props.getProperty("avdReadyTimeout"))));
        options.setApp(props.getProperty("app"));
        options.setAppPackage(props.getProperty("appPackage"));
        options.setAppActivity(props.getProperty("appActivity"));
        options.setNewCommandTimeout(Duration.ofSeconds(Long.parseLong(props.getProperty("newCommandTimeout"))));

        // Get the Appium server URI from properties
        String appiumServerUri = props.getProperty("appiumServerUri");

        // Initialize the driver with the dynamic URI
        driver = new AndroidDriver(new URI(appiumServerUri).toURL(), options);
        LogsUtils.info("Driver initialized with capabilities: ", options.toString());
        LogsUtils.info("Driver session ID: ", driver.getSessionId().toString());
        return driver;
    }

    /*
     * Quits the AndroidDriver instance to close the application and test session.
     */
    @Step("Quitting Android driver")
    public void quitDriver() {
        if (driver != null) {
            driver.quit();
        }
    }

    /*
     * Stops the Appium server if it is currently running.
     */
    @Step("Stopping Appium server")
    public void stopServer() {
        if (service != null && service.isRunning()) {
            service.stop();
        }
    }
}