package com.saucedemo.hooks;

import com.saucedemo.driver.DriverManager;
import com.saucedemo.utils.ExtentReportManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class TestHooks {

    @Before
    public void setUp(Scenario scenario) {
        DriverManager.initializeDriver();
        ExtentReportManager.createTest(scenario.getName());
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            // Capture screenshot for failed scenarios
            String screenshotPath = ExtentReportManager.captureScreenshot();
            ExtentReportManager.attachScreenshot(screenshotPath);
            
            // Log failure details
            ExtentReportManager.logFail("Scenario failed: " + scenario.getName());
        } else {
            ExtentReportManager.logPass("Scenario passed: " + scenario.getName());
        }
        
        ExtentReportManager.endTest();
        DriverManager.quitDriver();
    }
}