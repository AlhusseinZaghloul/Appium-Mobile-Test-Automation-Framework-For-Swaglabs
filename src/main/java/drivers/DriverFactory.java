package drivers;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.openqa.selenium.Platform;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;

/*
 * The DriverManager class provides utility methods for managing
 * the Appium server and creating an AndroidDriver instance.
 * This design encapsulates the server and driver setup, making
 * test classes cleaner and easier to maintain.
 */
public class DriverFactory {
    private DriverFactory(){}

    // Instance of the Appium service managing the local server.
    private static AppiumDriverLocalService service;

    // Instance of AndroidDriver for interacting with the Android device.
    private static AndroidDriver driver;

    /*
     * Starts the Appium server using the default service configuration.
     */
    public static AppiumDriverLocalService startServer() {
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
    public static AndroidDriver setupDriver() throws URISyntaxException, MalformedURLException {
        // Create options for the Android driver.
        UiAutomator2Options options = new UiAutomator2Options();

        // Configure device capabilities.
        options.setPlatformName(Platform.ANDROID.name());
        options.setDeviceName("Medium_Phone_API_35");
        options.setAvd("Medium_Phone_API_35");

        // Set emulator launch timeouts.
        options.setAvdLaunchTimeout(Duration.ofSeconds(180));
        options.setAvdReadyTimeout(Duration.ofSeconds(180));

        // Specify the application under test.
        options.setApp("./src/test/resources/SauceLabs-app.apk");
        options.setAppPackage("com.swaglabsmobileapp");
        options.setAppActivity("com.swaglabsmobileapp.MainActivity");
        options.setNewCommandTimeout(Duration.ofSeconds(100));

        // Initialize the AndroidDriver with the provided options.
        driver = new AndroidDriver(new URI("http://127.0.0.1:4723").toURL(), options);
        return driver;
    }

    /*
     * Quits the AndroidDriver instance to close the application and test session.
     */
    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
        }
    }

    /*
     * Stops the Appium server if it is currently running.
     */
    public static void stopServer() {
        if (service != null && service.isRunning()) {
            service.stop();
        }
    }
}