# Appium Mobile Test Automation Framework For Swaglabs

## Table of Contents

- [Overview](#overview)
- [Prerequisites](#prerequisites)
- [Project Structure](#project-structure)
- [Key Components](#key-components)
- [Advantages of the Framework](#advantages-of-the-framework)
- [Running Tests](#running-tests)

## Overview

This project provides a comprehensive and scalable mobile test automation framework designed specifically for the Swag Labs Android application. Built with **Java** and **Maven**, it leverages the power of **Appium** and **TestNG** to deliver robust and reliable end-to-end testing. The framework adheres to industry best practices, including the **Page Object Model (POM)** and externalized configuration and test data, to ensure maintainability and reduce code duplication.

**Key Features and Benefits:**

- **Robust and Reliable Testing:** Utilizes Appium for cross-platform mobile testing, ensuring consistent results across Android devices.
- **Structured and Maintainable Code:** Implements the Page Object Model (POM) to separate test logic from UI elements, enhancing code readability and maintainability.
- **TestNG Integration:** Leverages TestNG for powerful test execution, reporting, and parallel testing capabilities.
- **Externalized Configuration:** Centralizes configuration settings (e.g., device capabilities, application paths) in external files, allowing for easy environment management.
- **Externalized Test Data:** Separates test data from test scripts, enabling data-driven testing and simplifying data management.
- **Maven Dependency Management:** Uses Maven for efficient dependency management, ensuring consistent build processes and easy integration with CI/CD pipelines.
- **Clear Reporting:** Allure reporting features provide detailed test execution summaries and failure analysis, with utilities like `AllureUtils` further enhancing the reports by attaching execution logs from tests, making it easier to diagnose any issues.
- **Comprehensive Logging:**
Centralized with Log4j 2 via `LogsUtils` for consistent output, it supports multiple levels for adjustable detail.
Stored in `test-outputs/Logs` and also attached in Allure report, auto-cleaned before each test run.
- **Screenshot on Failure:** Automatically captures screenshots when a test fails, implemented in the `TestNGListeners` class. The `onTestFailure` method uses `ScreenshotUtils` to save screenshots with the test name, aiding in debugging by providing visual context for failures.
- **Scalability:** Designed to accommodate growing test suites and complex application workflows.
- **CI/CD Friendly:** Can be easily integrated into continuous integration and continuous delivery pipelines for automated testing.
- **Focus on Reusability:** The framework promotes code reusability through modular design and shared components with respect Single Resonsibility Principle (SRP).

## Prerequisites

Before setting up and running the tests, ensure you have the following tools installed and configured:

- **[Java](https://www.oracle.com/java/technologies/javase-downloads.html)**: The framework is built with Java. Install the latest JDK.
- **[Maven](https://maven.apache.org/download.cgi)**: Used for building and managing project dependencies. Follow the installation instructions on the official site.
- **[Appium](http://appium.io/docs/en/about-appium/getting-started/)**: Required for mobile automation. Follow the getting started guide to install Appium and its dependencies.
- **[Android SDK](https://developer.android.com/studio)**: Necessary for Android testing. Install Android Studio, which includes the SDK, or install the SDK separately.
- **Android Device or Emulator**: You need an Android device or emulator to run the tests.
  - For an emulator, use Android Studio to create and manage virtual devices. See [this guide](https://developer.android.com/studio/run/managing-avds).
  - For a physical device, enable developer options and USB debugging. See the [official guide](https://developer.android.com/studio/run/device).

Additionally, ensure that the Swag Labs application APK is available, as you will need to specify its path in the `config.properties` file.

## Project Structure

The framework is organized into a modular structure to separate concerns and streamline development and testing workflows. Below is the detailed directory structure:

```
Appium-Mobile-Test-Automation-Framework-For-Swaglabs/
├── .idea/
├── src/
│   └── main/
│       └── java/
│           ├── drivers/
│           │   └── DriverFactory
│           ├── listeners/
│           │   └── TestNGListeners
│           ├── pages/
│           │   ├── LoginPage
│           │   ├── MenuPage
│           │   ├── ProductDetailsPage
│           │   └── ProductsPage
│           └── utils/
│               ├── AllureUtils
│               ├── ElementsActions
│               ├── FilesUtils
│               ├── JsonReader
│               ├── LogsUtils
│               ├── ScreenshotUtils
│               └── Waits
│       └── resources/
│           ├── allure.properties
│           └── log4j2.properties
├── test/
│   └── java/
│       └── tests/
│           ├── LoginTest
│           ├── ProductDetailsTest
│           └── ProductsTest
│   └── resources/
│       ├── config.properties
│       ├── SauceLabs-app.apk
│       └── testData.json
├── test-outputs/
│   ├── allure-results/
│   ├── Logs/
│   ├── screenshots/
│   └── target/
├── .gitignore
├── pom.xml
└── README.md
```

Key directories and their purposes are outlined below:
- **`src/main/java`**: Core Java classes for the framework.
  - **`drivers/`**: Contains `DriverFactory` for managing the Appium server and `AndroidDriver` lifecycle.
  - **`listeners/`**: Houses `TestNGListeners` for handling test events and reporting.
  - **`pages/`**: Implements POM with classes like `LoginPage` and `ProductsPage` for UI interactions.
  - **`utils/`**: Utility classes with specific responsibilities:
    - `AllureUtils`: Enhances Allure reports by attaching logs.
    - `ElementsActions`: Provides methods for UI element interactions.
    - `FilesUtils`: Handles file system operations (e.g., file retrieval, directory cleaning).
    - `JsonReader`: Reads JSON test data.
    - `LogsUtils`: Manages logging with Log4j 2.
    - `ScreenshotUtils`: Captures screenshots during test execution.
    - `Waits`: Manages wait conditions.

- **`src/main/resources/`**: Configuration files like `allure.properties` and `log4j2.properties`.

- **`src/test/java`**: Test classes.
  - **`tests/`**: Includes test classes like: `LoginTest`, `ProductDetailsTest`, and `ProductsTest`.

- **`src/test/resources/`**: Test resources.
  - `config.properties`: Externalizes Appium and device settings.
  - `SauceLabs-app.apk`: Swag Labs APK for testing.
  - `testData.json`: External test data for assertions.

- **`test-outputs/`**: Stores test outputs (Allure results, logs, screenshots).

This structure ensures a clear separation of concerns, aligning with SRP to enhance maintainability.
## Key Components

- **Driver Management**  
  The `DriverFactory` class initializes and manages the `AndroidDriver` instance by reading settings from `config.properties`. This dynamic configuration eliminates hard-coded values, allowing the framework to adapt to various testing environments (e.g., local or remote Appium servers) with ease.

- **Page Object Model (POM)**  
  The `pages` package follows the POM design pattern, where each class (e.g., `LoginPage`) represents a specific screen in the application. This approach encapsulates UI interactions and assertions, making tests more maintainable and resilient to UI changes.

- **Test Cases**  
  Test classes in the `tests` package, such as `LoginTest`, define specific test scenarios (e.g., valid and invalid login flows). These tests leverage data from `testData.json` for assertions, ensuring consistent verification of expected outcomes.

- **Data-Driven Testing**  
  The framework incorporates data-driven testing by externalizing test data into a JSON file (`testData.json`). This separation allows test methods to retrieve assertion values dynamically, enhancing maintainability. For instance, in `LoginTest`, expected headers and error messages are sourced from the JSON file, ensuring that updates to these values do not require changes to the test code.

## Advantages of the Framework

- **Maintainability**  
  By externalizing configuration settings in `config.properties` and test data in `testData.json`, changes can be made without modifying the codebase. This reduces the risk of errors and simplifies updates to test configurations or data.

- **Flexibility**  
  The framework adapts effortlessly to different testing environments. For instance, switching between local and remote Appium servers or testing on various devices requires only updates to `config.properties`, not code changes.

- **Scalability**  
  The modular design, driven by the Page Object Model, allows new pages and interactions to be added with minimal impact on existing tests. This makes the framework suitable for growing applications with evolving test requirements.

- **Best Practices**  
  The framework adheres to industry standards by avoiding hard-coded values, separating concerns (e.g., driver management, page interactions, and test logic), and promoting reusability. This enhances its robustness and usability for both new and experienced testers.

## Running Tests

To execute tests using this framework, follow these steps:

1. **Configuration**: Update the `config.properties` file in `src/test/resources` with the necessary device capabilities, application details (such as the APK path), and Appium server settings. Refer to the file’s comments for guidance.
2. **Build and Run**: Execute the following Maven command in the project root directory:
   ```bash
   mvn clean install
   ```

With the current implementation, where test data is externalized for individual tests, this provides a foundation for maintainability and potential future expansion to fully parameterized data-driven testing.
