package com.saucedemo.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.saucedemo.driver.DriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExtentReportManager {
    private static ExtentReports extent;
    private static final ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    private static final String REPORT_PATH = "test-output/extent-reports/";
    private static final String SCREENSHOT_PATH = "test-output/screenshots/";

    public static void initializeReport() {
        if (extent == null) {
            createReportDirectory();
            
            String reportFileName = "TestReport_" + 
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".html";
            
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(REPORT_PATH + reportFileName);
            sparkReporter.config().setTheme(Theme.DARK);
            sparkReporter.config().setDocumentTitle("SauceDemo BDD Test Report");
            sparkReporter.config().setReportName("Automated Test Results");
            
            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
            extent.setSystemInfo("Environment", "Test");
            extent.setSystemInfo("User", System.getProperty("user.name"));
            extent.setSystemInfo("OS", System.getProperty("os.name"));
            extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        }
    }

    public static void createTest(String testName) {
        if (extent == null) {
            initializeReport();
        }
        ExtentTest extentTest = extent.createTest(testName);
        test.set(extentTest);
    }

    public static void logPass(String message) {
        test.get().log(Status.PASS, message);
    }

    public static void logFail(String message) {
        test.get().log(Status.FAIL, message);
    }

    public static void logInfo(String message) {
        test.get().log(Status.INFO, message);
    }

    public static String captureScreenshot() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String screenshotName = "screenshot_" + timestamp + ".png";
        String screenshotPath = SCREENSHOT_PATH + screenshotName;
        
        try {
            createScreenshotDirectory();
            File sourceFile = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.FILE);
            File destFile = new File(screenshotPath);
            FileUtils.copyFile(sourceFile, destFile);
            return screenshotPath;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void attachScreenshot(String screenshotPath) {
        if (screenshotPath != null) {
            try {
                test.get().addScreenCaptureFromPath(screenshotPath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void endTest() {
        extent.flush();
    }

    private static void createReportDirectory() {
        File reportDir = new File(REPORT_PATH);
        if (!reportDir.exists()) {
            reportDir.mkdirs();
        }
    }

    private static void createScreenshotDirectory() {
        File screenshotDir = new File(SCREENSHOT_PATH);
        if (!screenshotDir.exists()) {
            screenshotDir.mkdirs();
        }
    }
}