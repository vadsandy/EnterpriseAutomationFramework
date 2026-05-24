package com.automation.utils;

import com.automation.core.DriverManager;
import io.qameta.allure.Allure;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class ElementActions {
    private WebDriver driver;
    private WebDriverWait wait;

    public ElementActions() {
        this.driver = DriverManager.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public WebElement waitForVisibility(By locator, String elementName) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (Exception e) {
            Assert.fail("Element not visible after timeout: [" + elementName + "]");
            return null;
        }
    }

    public void click(By locator, String elementName) {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
            element.click();
            Allure.step("Successfully clicked on: " + elementName);
        } catch (Exception e) {
            Allure.step("WARNING: Standard UI click failed on '" + elementName + "'. Attempting JS fallback...");
            try {
                WebElement element = driver.findElement(locator);
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].click();", element);
                Assert.fail("Standard UI click failed for: [" + elementName + "]. JS fallback used, but test is marked failed.");
            } catch (Exception jsEx) {
                Assert.fail("Both standard and JS click failed for: [" + elementName + "]");
            }
        }
    }

    public void type(By locator, String text, String elementName) {
        try {
            WebElement element = waitForVisibility(locator, elementName);
            element.clear();
            element.sendKeys(text);
            // We mask the password in the logs for security
            String logText = elementName.toLowerCase().contains("password") ? "********" : text;
            Allure.step("Successfully typed '" + logText + "' into: " + elementName);
        } catch (Exception e) {
            Allure.step("WARNING: Standard UI type failed on '" + elementName + "'. Attempting JS fallback...");
            try {
                WebElement element = driver.findElement(locator);
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].value='" + text + "';", element);
                Assert.fail("Standard UI typing failed for: [" + elementName + "]. JS fallback used, but test is marked failed.");
            } catch (Exception jsEx) {
                Assert.fail("Both standard and JS typing failed for: [" + elementName + "]");
            }
        }
    }

    public int getDropdownOptionsCount(By locator, String elementName) {
        try {
            WebElement element = waitForVisibility(locator, elementName);
            Select dropdown = new Select(element);
            return dropdown.getOptions().size();
        } catch (Exception e) {
            Assert.fail("Failed to get options from dropdown: [" + elementName + "]");
            return 0;
        }
    }

    public void selectByIndex(By locator, int index, String elementName) {
        try {
            WebElement element = waitForVisibility(locator, elementName);
            Select dropdown = new Select(element);
            dropdown.selectByIndex(index);
            Allure.step("Successfully selected index '" + index + "' from dropdown: " + elementName);
        } catch (Exception e) {
            Assert.fail("Failed to select index " + index + " from dropdown: [" + elementName + "]");
        }
    }
}