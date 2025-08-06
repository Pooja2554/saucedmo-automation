package com.saucedemo.runners;

import org.junit.runner.RunWith;
import io.cucumber.junit.*;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.saucedemo.stepdefinitions", "com.saucedemo.utils","com.saucedemo.hooks"},
        plugin = {"pretty", "html:target/cucumber-report.html"},
        monochrome = true,
        tags = "@E2E"
)
public class TestRunner {
}
