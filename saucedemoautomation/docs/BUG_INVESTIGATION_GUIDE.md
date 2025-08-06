# Bug Investigation and Debugging Guide

## 1. Overview
This guide provides a systematic approach to investigating bugs, reproducing issues, and debugging test failures in the SauceDemo BDD automation framework.

## 2. Bug Investigation Methodology

### 2.1 Initial Bug Analysis
When a bug is reported, follow these steps:

1. **Gather Information**
   - Bug description and expected vs actual behavior
   - Environment details (browser, OS, resolution)
   - Steps to reproduce
   - Screenshots/videos if available
   - User account used (if applicable)

2. **Categorize the Bug**
   - UI/UX issue
   - Functional defect
   - Performance problem
   - Browser-specific issue
   - Data-related problem

### 2.2 Test Case Design for Bug Reproduction

#### Example: Shopping Cart Bug Investigation

**Bug Report**: "Items disappear from cart when navigating between pages"

**Investigation Test Case**:
```gherkin
@BugInvestigation @Cart @Regression
Feature: Shopping Cart Persistence Investigation
  As a user
  I want my cart items to persist across page navigation
  So that I don't lose my selected products

  Scenario: Cart persistence during navigation - Bug Reproduction
    Given I navigate to the SauceDemo login page
    And I am logged in as "standard_user" with password "secret_sauce"
    When I add "Sauce Labs Backpack" to the cart
    And I add "Sauce Labs Bike Light" to the cart
    Then the cart badge should show "2"
    
    When I navigate to product detail page for "Sauce Labs Bolt T-Shirt"
    And I navigate back to products page
    Then the cart badge should still show "2"
    And I should see "Sauce Labs Backpack" in cart when I check
    And I should see "Sauce Labs Bike Light" in cart when I check
    
    When I click on user menu
    And I navigate to about page
    And I navigate back to products page
    Then the cart badge should still show "2"
    And all previously added items should still be in cart
```

### 2.3 Enhanced Step Definitions for Debugging

```java
@Then("the cart badge should still show {string}")
public void the_cart_badge_should_still_show(String expectedCount) {
    // Add detailed logging for debugging
    String actualCount = productsPage.getCartBadgeText();
    String currentUrl = productsPage.getCurrentUrl();
    
    ExtentReportManager.logInfo("Current URL: " + currentUrl);
    ExtentReportManager.logInfo("Expected cart count: " + expectedCount);
    ExtentReportManager.logInfo("Actual cart count: " + actualCount);
    
    // Capture screenshot for analysis
    String screenshot = productsPage.captureScreenshot();
    ExtentReportManager.attachScreenshot(screenshot);
    
    Assertions.assertThat(actualCount)
            .as("Cart badge should maintain count after navigation. Current URL: " + currentUrl)
            .isEqualTo(expectedCount);
}

@Then("all previously added items should still be in cart")
public void all_previously_added_items_should_still_be_in_cart() {
    productsPage.clickShoppingCart();
    
    List<String> cartItems = cartPage.getCartItemNames();
    ExtentReportManager.logInfo("Items currently in cart: " + cartItems.toString());
    
    // Verify expected items are present
    List<String> expectedItems = Arrays.asList("Sauce Labs Backpack", "Sauce Labs Bike Light");
    
    for (String expectedItem : expectedItems) {
        boolean itemFound = cartItems.contains(expectedItem);
        ExtentReportManager.logInfo("Checking for item: " + expectedItem + " - Found: " + itemFound);
        
        if (!itemFound) {
            String screenshot = cartPage.captureScreenshot();
            ExtentReportManager.attachScreenshot(screenshot);
        }
        
        Assertions.assertThat(itemFound)
                .as("Expected item should be in cart: " + expectedItem)
                .isTrue();
    }
}
```

## 3. Debugging Strategies

### 3.1 Systematic Debugging Approach

#### Step 1: Reproduce the Issue
```java
@Test
public void reproduceBugScenario() {
    // Set up comprehensive logging
    logger.info("Starting bug reproduction for cart persistence issue");
    
    // Enable detailed WebDriver logging
    System.setProperty("webdriver.chrome.logfile", "target/chromedriver.log");
    System.setProperty("webdriver.chrome.verboseLogging", "true");
    
    // Execute reproduction steps with detailed verification
    loginPage.navigateToLoginPage();
    loginPage.login("standard_user", "secret_sauce");
    
    // Verify initial state
    Assert.assertTrue("Should be on products page", productsPage.isOnProductsPage());
    Assert.assertEquals("Cart should be empty initially", "0", productsPage.getCartBadgeText());
    
    // Add items and verify each step
    productsPage.addProductToCart("Sauce Labs Backpack");
    String cartCountAfterFirst = productsPage.getCartBadgeText();
    logger.info("Cart count after adding first item: " + cartCountAfterFirst);
    
    productsPage.addProductToCart("Sauce Labs Bike Light");
    String cartCountAfterSecond = productsPage.getCartBadgeText();
    logger.info("Cart count after adding second item: " + cartCountAfterSecond);
    
    // Navigate and check persistence
    productsPage.clickProductByName("Sauce Labs Bolt T-Shirt");
    logger.info("Navigated to product detail page");
    
    driver.navigate().back();
    logger.info("Navigated back to products page");
    
    String cartCountAfterNavigation = productsPage.getCartBadgeText();
    logger.info("Cart count after navigation: " + cartCountAfterNavigation);
    
    // Capture state for analysis
    captureDebugInformation();
    
    Assert.assertEquals("Cart count should persist after navigation", "2", cartCountAfterNavigation);
}

private void captureDebugInformation() {
    // Capture screenshot
    String screenshot = productsPage.captureScreenshot();
    logger.info("Screenshot captured: " + screenshot);
    
    // Capture page source
    String pageSource = driver.getPageSource();
    saveToFile("target/debug/page_source.html", pageSource);
    
    // Capture browser logs
    LogEntries logs = driver.manage().logs().get(LogType.BROWSER);
    for (LogEntry entry : logs) {
        logger.info("Browser Log: " + entry.getMessage());
    }
    
    // Capture network activity (if supported)
    if (driver instanceof ChromeDriver) {
        // ChromeDriver specific network logging
    }
}
```

#### Step 2: Isolate the Problem
```java
@ParameterizedTest
@ValueSource(strings = {"chrome", "firefox", "edge"})
public void testCartPersistenceAcrossBrowsers(String browser) {
    // Test the same scenario across different browsers
    DriverManager.initializeDriver(browser);
    
    try {
        reproduceBugScenario();
    } catch (Exception e) {
        logger.error("Bug reproduced in " + browser + ": " + e.getMessage());
        throw e;
    }
}

@Test
public void testCartPersistenceWithDifferentUsers() {
    String[] testUsers = {"standard_user", "performance_glitch_user", "problem_user"};
    
    for (String user : testUsers) {
        logger.info("Testing cart persistence with user: " + user);
        
        // Reset state
        driver.manage().deleteAllCookies();
        driver.navigate().refresh();
        
        // Test with current user
        loginPage.login(user, "secret_sauce");
        // ... rest of test logic
    }
}
```

### 3.2 Advanced Debugging Techniques

#### JavaScript Error Detection
```java
public void checkForJavaScriptErrors() {
    LogEntries logEntries = driver.manage().logs().get(LogType.BROWSER);
    List<String> jsErrors = new ArrayList<>();
    
    for (LogEntry entry : logEntries) {
        if (entry.getLevel() == Level.SEVERE && 
            entry.getMessage().contains("javascript")) {
            jsErrors.add(entry.getMessage());
        }
    }
    
    if (!jsErrors.isEmpty()) {
        logger.error("JavaScript errors detected: " + jsErrors);
        ExtentReportManager.logFail("JavaScript errors found: " + jsErrors.toString());
    }
}
```

#### Network Request Monitoring
```java
public void monitorNetworkRequests() {
    // Enable network logging
    LoggingPreferences logPrefs = new LoggingPreferences();
    logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
    
    ChromeOptions options = new ChromeOptions();
    options.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
    
    WebDriver driver = new ChromeDriver(options);
    
    // After actions, check network logs
    LogEntries logs = driver.manage().logs().get("performance");
    for (LogEntry entry : logs) {
        JSONObject message = new JSONObject(entry.getMessage());
        JSONObject messageBody = message.getJSONObject("message");
        
        if (messageBody.getString("method").equals("Network.responseReceived")) {
            // Analyze network responses
            logger.info("Network response: " + messageBody.toString());
        }
    }
}
```

## 4. Common Bug Patterns and Solutions

### 4.1 Timing Issues
```java
// Problem: Element not found due to timing
public void waitForElementWithRetry(WebElement element, int maxRetries) {
    for (int i = 0; i < maxRetries; i++) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            return;
        } catch (TimeoutException e) {
            logger.warn("Attempt " + (i + 1) + " failed, retrying...");
            if (i == maxRetries - 1) {
                throw e;
            }
        }
    }
}
```

### 4.2 State Consistency Issues
```java
// Problem: Application state not as expected
public void verifyApplicationState() {
    // Check user authentication status
    boolean isLoggedIn = productsPage.isUserLoggedIn();
    logger.info("User logged in status: " + isLoggedIn);
    
    // Check cart state
    String cartCount = productsPage.getCartBadgeText();
    logger.info("Current cart count: " + cartCount);
    
    // Check current page
    String currentUrl = driver.getCurrentUrl();
    logger.info("Current page URL: " + currentUrl);
    
    // Verify cookies
    Set<Cookie> cookies = driver.manage().getCookies();
    logger.info("Session cookies: " + cookies.size());
}
```

## 5. Debugging Tools and Utilities

### 5.1 Enhanced Assertion Helper
```java
public class DebugAssertions {
    public static void assertWithDebugInfo(String expected, String actual, String context) {
        if (!expected.equals(actual)) {
            String debugInfo = String.format(
                "Assertion failed in context: %s%n" +
                "Expected: %s%n" +
                "Actual: %s%n" +
                "Current URL: %s%n" +
                "Page Title: %s%n" +
                "Timestamp: %s",
                context, expected, actual,
                DriverManager.getDriver().getCurrentUrl(),
                DriverManager.getDriver().getTitle(),
                LocalDateTime.now()
            );
            
            logger.error(debugInfo);
            ExtentReportManager.logFail(debugInfo);
            
            // Capture additional debug information
            String screenshot = captureScreenshot();
            ExtentReportManager.attachScreenshot(screenshot);
            
            fail(debugInfo);
        }
    }
}
```

### 5.2 Test Data Validator
```java
public class TestDataValidator {
    public static void validateTestEnvironment() {
        // Check if test data is available
        String baseUrl = ConfigReader.getBaseUrl();
        
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(baseUrl).openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            
            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                throw new RuntimeException("Test environment not accessible: " + responseCode);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to validate test environment", e);
        }
    }
}
```

## 6. Bug Report Template

### 6.1 Automated Bug Report Generation
```java
public class BugReportGenerator {
    public static void generateBugReport(String bugTitle, Exception exception, String scenario) {
        BugReport report = new BugReport();
        report.setTitle(bugTitle);
        report.setScenario(scenario);
        report.setException(exception);
        report.setEnvironment(getEnvironmentInfo());
        report.setScreenshot(captureScreenshot());
        report.setBrowserLogs(getBrowserLogs());
        report.setTimestamp(LocalDateTime.now());
        
        // Save to file or send to bug tracking system
        saveBugReport(report);
    }
    
    private static EnvironmentInfo getEnvironmentInfo() {
        return EnvironmentInfo.builder()
                .browser(ConfigReader.getBrowser())
                .os(System.getProperty("os.name"))
                .javaVersion(System.getProperty("java.version"))
                .seleniumVersion(getSeleniumVersion())
                .build();
    }
}
```

## 7. Best Practices for Bug Investigation

### 7.1 Documentation
- Always document reproduction steps
- Include environment details
- Capture relevant logs and screenshots
- Note any workarounds discovered

### 7.2 Collaboration
- Share findings with development team promptly
- Provide clear, actionable bug reports
- Include automated test cases for regression prevention
- Follow up on bug fixes with verification tests

### 7.3 Prevention
- Implement comprehensive logging
- Use meaningful assertion messages
- Create negative test scenarios
- Regular test maintenance and review

---

This guide provides a comprehensive approach to investigating and debugging issues in the SauceDemo BDD automation framework, ensuring efficient problem resolution and preventing regression.