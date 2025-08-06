package com.saucedemo.driver;

import com.saucedemo.config.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class DriverManager {
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static void initializeDriver() {
        String browser = ConfigReader.getBrowser().toLowerCase();
        if(browser.equals("chrome")){
            WebDriverManager.chromedriver().setup();
            ChromeOptions chromeOptions = new ChromeOptions();

            Map<String, Object> prefs = new HashMap<>();
            prefs.put("credentials_enable_service", false);
            prefs.put("profile.password_manager_enabled", false);
//            prefs.put("profile.default_content_setting_values.notifications", 2); // Disable notifications
//            prefs.put("profile.default_content_setting_values.automatic_downloads", 1);
            chromeOptions.setExperimentalOption("prefs", prefs);
            if (ConfigReader.isHeadless()) {
                chromeOptions.addArguments("--headless");
            }
            chromeOptions.addArguments("user-data-dir=/tmp/temp-profile");
            chromeOptions.addArguments("--no-sandbox");
            chromeOptions.addArguments("--disable-dev-shm-usage");
            chromeOptions.addArguments("--disable-gpu");
            chromeOptions.addArguments("--disable-popup-blocking");
            chromeOptions.addArguments("--disable-credential-saving");
            chromeOptions.addArguments("--disable-notifications"); // optional: disables web push notifications
            chromeOptions.addArguments("--disable-save-password-bubble");
            chromeOptions.addArguments("--window-size=1920,1080");
            chromeOptions.addArguments("--no-default-browser-check");
            chromeOptions.addArguments("--disable-autofill-keyboard-accessory-view");
//            chromeOptions.addArguments("--incognito");
            chromeOptions.addArguments("--no-first-run");
            chromeOptions.addArguments("--password-store=basic");
            chromeOptions.addArguments("--use-mock-keychain");
            driver.set(new ChromeDriver(chromeOptions));
        }
        else {
            throw new IllegalArgumentException("Browser not supported: " + browser);
        }
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(ConfigReader.getImplicitWait()));
        getDriver().manage().window().maximize();
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}