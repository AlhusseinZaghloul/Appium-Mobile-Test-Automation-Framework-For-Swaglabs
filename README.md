

# Test Automation Framework

## Overview
This project is a robust test automation framework built with **Java** and **Maven**, tailored for testing Android applications using **Appium** and **TestNG**. The framework is engineered to ensure maintainability, scalability, and ease of use by adopting industry best practices, including the **Page Object Model (POM)** and externalized configuration and test data.

---

## Project Structure
The framework is organized into a modular structure to separate concerns and streamline development and testing workflows. Key directories and their purposes are outlined below:

- **`src/main/java`**
    - **`drivers`**: Houses the `DriverFactory` class, which manages the Appium server and AndroidDriver lifecycle. It handles starting, stopping, and configuring the driver based on settings from an external configuration file.
    - **`pages`**: Implements the Page Object Model with classes like `LoginPage` and `ProductPage`. These classes encapsulate UI interactions and validations for specific screens of the Android application.

- **`src/test/java`**
    - **`tests`**: Contains test classes such as `LoginTest`, which define test scenarios using TestNG. Each test method includes setup and teardown processes to interact with the application seamlessly.

- **`src/test/resources`**
    - **`testData.json`**: Stores external test data (e.g., expected headers, error messages) used for assertions in test cases, enabling data-driven testing.
    - **`config.properties`**: A self-documenting configuration file that externalizes settings for the Appium driver and server, such as device capabilities, application details, and server URIs. Comments within the file explain each property’s purpose.

---

## Key Components

- **Driver Management**  
  The `DriverFactory` class initializes and manages the `AndroidDriver` instance by reading settings from `config.properties`. This dynamic configuration eliminates hard-coded values, allowing the framework to adapt to various testing environments (e.g., local or remote Appium servers) with ease.

- **Page Object Model (POM)**  
  The `pages` package follows the POM design pattern, where each class (e.g., `LoginPage`) represents a specific screen in the application. This approach encapsulates UI interactions and assertions, making tests more maintainable and resilient to UI changes.

- **Test Cases**  
  Test classes in the `tests` package, such as `LoginTest`, define specific test scenarios (e.g., valid and invalid login flows). These tests leverage data from `testData.json` for assertions, ensuring consistent verification of expected outcomes.

---

## Advantages of the Framework

- **Maintainability**  
  By externalizing configuration settings in `config.properties` and test data in `testData.json`, changes can be made without modifying the codebase. This reduces the risk of errors and simplifies updates to test configurations or data.

- **Flexibility**  
  The framework adapts effortlessly to different testing environments. For instance, switching between local and remote Appium servers or testing on various devices requires only updates to `config.properties`, not code changes.

- **Scalability**  
  The modular design, driven by the Page Object Model, allows new pages and interactions to be added with minimal impact on existing tests. This makes the framework suitable for growing applications with evolving test requirements.

- **Best Practices**  
  The framework adheres to industry standards by avoiding hard-coded values, separating concerns (e.g., driver management, page interactions, and test logic), and promoting reusability. This enhances its robustness and usability for both new and experienced testers.

---

## Running Tests

To execute tests using this framework, follow these steps:

1. **Prerequisites**: Ensure **Java** and **Maven** are installed on your system.
2. **Configuration**: Update the `config.properties` file in `src/test/resources` with the necessary device capabilities, application details, and Appium server settings. Refer to the file’s comments for guidance.
3. **Build and Run**: Execute the following Maven command in the project root directory:
   ```bash
   mvn clean install
   ```

