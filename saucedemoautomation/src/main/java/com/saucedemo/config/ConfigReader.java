package com.saucedemo.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties;
    private static final String CONFIG_FILE_PATH = "src/test/resources/config.properties";

    static {
        try {
            FileInputStream file = new FileInputStream(CONFIG_FILE_PATH);
            properties = new Properties();
            properties.load(file);
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Configuration file not found at " + CONFIG_FILE_PATH);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static String getBrowser() {
        return getProperty("browser");
    }

    public static String getBaseUrl() {
        return getProperty("base.url");
    }

    public static int getImplicitWait() {
        return Integer.parseInt(getProperty("implicit.wait"));
    }

    public static int getExplicitWait() {
        return Integer.parseInt(getProperty("explicit.wait"));
    }

    public static boolean isHeadless() {
        return Boolean.parseBoolean(getProperty("headless"));
    }
}