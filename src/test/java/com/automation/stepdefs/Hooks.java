package com.automation.stepdefs;

import com.automation.core.DriverManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.ByteArrayInputStream;

public class Hooks {

    @Before
    public void setup(Scenario scenario) {
        // NEW CHANGE: Skip browser setup if it's a standalone API test case
        if (!scenario.getSourceTagNames().contains("@API")) {
            DriverManager.initDriver();
        }
    }

    @After
    public void tearDown(Scenario scenario) {
        // NEW CHANGE: Skip browser teardown and screenshotting if it's an API test case
        if (!scenario.getSourceTagNames().contains("@API")) {
            if (scenario.isFailed()) {
                try {
                    TakesScreenshot screenshotDriver = (TakesScreenshot) DriverManager.getDriver();
                    byte[] screenshot = screenshotDriver.getScreenshotAs(OutputType.BYTES);
                    Allure.addAttachment("Failure Screenshot - " + scenario.getName(), new ByteArrayInputStream(screenshot));
                } catch (Exception e) {
                    System.err.println("Failed to capture screenshot: " + e.getMessage());
                }
            }
            DriverManager.quitDriver();
        }
    }
}