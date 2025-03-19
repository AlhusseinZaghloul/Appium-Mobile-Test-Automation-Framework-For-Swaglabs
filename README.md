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
- **Scalability:** Designed to accommodate growing test suites and complex application workflows.
- **Clear Reporting:** TestNG's reporting features provide detailed test execution summaries and failure analysis.
- **Easy Setup and Usage:** Designed for ease of use, enabling quick onboarding for new team members.
- **CI/CD Friendly:** Can be easily integrated into continuous integration and continuous delivery pipelines for automated testing.
- **Focus on Reusability:** The framework promotes code reusability through modular design and shared components.

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
Swaglabs-Appium-Mobile-Test-Automation-Framework
├── .idea/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── drivers/
│   │   │   │   └── DriverFactory.java
│   │   │   ├── pages/
│   │   │   │   ├── LoginPage.java
│   │   │   │   └── ProductPage.java
│   │   │   └── utils/
│   │   │       ├── ElementsActions.java
│   │   │       ├── JsonReader.java
│   │   │       └── Waits.java
│   │   └── resources/
│   └── test/
│       ├── java/
│       │   └── tests/
│       │       └── LoginTest.java
│       └── resources/
│           ├── config.properties
│           ├── SauceLabs-app.apk
│           └── testData.json
└── target/
```

Key directories and their purposes are outlined below:

- **`src/main/java`**  
  Contains the core Java classes for the framework.
  - **`drivers/`**: Houses the `DriverFactory` class, which manages the Appium server and AndroidDriver lifecycle. It handles starting, stopping, and configuring the driver based on settings from `config.properties`.
  - **`pages/`**: Implements the Page Object Model with classes like `LoginPage` and `ProductPage`. These classes encapsulate UI interactions and validations for specific screens of the Android application.
  - **`utils/`**: Contains utility classes that provide common functionalities:
    - `ElementsActions.java`: Methods for interacting with UI elements (e.g., click, send keys).
    - `JsonReader.java`: Utility for reading and parsing JSON test data from `testData.json`.
    - `Waits.java`: Helper methods for handling explicit and implicit waits.

- **`src/main/resources/`**  
  Currently empty. This directory can be used for any resources needed by the main application code, such as configuration files or static assets.

- **`src/test/java`**  
  Contains the test classes.
  - **`tests/`**: Includes test classes like `LoginTest`, which define test scenarios using TestNG. Each test method includes setup and teardown processes to interact with the application seamlessly.

- **`src/test/resources/`**  
  Stores resources used by the test classes.
  - **`config.properties`**: A self-documenting configuration file that externalizes settings for the Appium driver and server, such as device capabilities, application details, and server URIs. Comments within the file explain each property’s purpose.
  - **`SauceLabs-app.apk`**: The APK file for the Swag Labs application, used for installation on the test device.
  - **`testData.json`**: Stores external test data (e.g., expected headers, error messages) used for assertions in test cases, enabling data-driven testing.

- **`target/`**  
  Generated by Maven during the build process. Contains compiled classes, test reports, and other build artifacts. This directory is not part of the source code but is included in the project structure for completeness.

- **`.idea/`**  
  Contains IntelliJ IDEA project settings. This directory is specific to users of the IntelliJ IDE and is not essential to the framework’s functionality.

This structure ensures a clear separation of concerns, making the framework easy to navigate and maintain.

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
