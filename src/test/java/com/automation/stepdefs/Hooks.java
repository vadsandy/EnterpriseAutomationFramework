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
    public void setup() {
        DriverManager.initDriver();
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            try {
                TakesScreenshot screenshotDriver = (TakesScreenshot) DriverManager.getDriver();
                byte[] screenshot = screenshotDriver.getScreenshotAs(OutputType.BYTES);
                // NEW CHANGE: Attach screenshot natively to Allure
                Allure.addAttachment("Failure Screenshot - " + scenario.getName(), new ByteArrayInputStream(screenshot));
            } catch (Exception e) {
                System.err.println("Failed to capture screenshot: " + e.getMessage());
            }
        }

        DriverManager.quitDriver();
    }
}