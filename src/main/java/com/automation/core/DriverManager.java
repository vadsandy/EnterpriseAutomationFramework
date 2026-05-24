package com.automation.core;

import com.automation.config.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.time.Duration;

public class DriverManager {
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static void initDriver() {
        if (driver.get() == null) {
            String executionTarget = ConfigReader.getExecutionTarget();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*");

            WebDriver webDriver;

            try {
                // NEW CHANGE: Route to Grid if target is 'remote'
                if (executionTarget.equalsIgnoreCase("remote")) {
                    options.addArguments("--headless=new"); // Best practice for Grid
                    options.addArguments("--window-size=1920,1080");
                    String gridUrl = ConfigReader.getGridUrl();
                    webDriver = new RemoteWebDriver(new URL(gridUrl), options);
                } else {
                    webDriver = new ChromeDriver(options);
                    webDriver.manage().window().maximize();
                }
            } catch (Exception e) {
                throw new RuntimeException("Failed to initialize WebDriver: " + e.getMessage());
            }

            webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.set(webDriver);
        }
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