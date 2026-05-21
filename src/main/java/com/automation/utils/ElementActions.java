package com.automation.utils;

import com.automation.core.DriverManager;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ElementActions {

    private WebDriver driver;
    private WebDriverWait wait;

    public ElementActions(){
        this.driver = DriverManager.getDriver();
        // Standard explicit wait for elements
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void waitForVisibilityOfElement(WebElement element, String elementName){
        try{
            wait.until(ExpectedConditions.visibilityOf(element));
        }catch(Exception e) {
            Assert.fail("Element not visible after timeout: [" + elementName + "]");
        }
    }

    public void click(WebElement element, String elementName) {
        try{
            wait.until(ExpectedConditions.elementToBeClickable(element));
            element.click();
        } catch (Exception e){
            System.err.println("Standard UI click failed on '" + elementName + "'. Attempting JavaScript fallback...");
            try{
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].click();", element);
                // We executed JS to keep the browser state moving for downstream debugging,
                // but the test MUST fail because a real user couldn't click it.
                Assert.fail("Standard UI click failed for element: [" + elementName + "]. JS click was executed, but test is marked failed. Exception: " + e.getMessage());
            } catch (Exception jsException){
                Assert.fail("Both standard click and JS fallback failed for element: [" + elementName + "]");
            }
        }
    }

    public void type(WebElement element, String text, String elementName){
        try{
            wait.until(ExpectedConditions.visibilityOf(element));
            element.clear();
            element.sendKeys(text);
        } catch (Exception e) {
            System.err.println("UI type failed on '" + elementName + "'. Attempting JS fallback...");
            try{
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].value='" + text + "'; ", element);
                Assert.fail("Standard UI typing failed for: [" + elementName + "]. JS fallback used, but test is marked failed.");
            } catch (Exception jsException) {
                Assert.fail("Both standard and JS typing failed for: [" + elementName + "]");
            }
        }
    }

    public void scrollToElement(WebElement element){
        try{
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView(true);", element);
        }catch(Exception e){
            System.err.println("Could not scroll to element. Exception: " + e.getMessage());
        }
    }

    public void selectByVisibleText(WebElement element, String text, String elementName){
        try{
            wait.until(ExpectedConditions.visibilityOf(element));
            Select dropdown = new Select(element);
            dropdown.selectByVisibleText(text);
        } catch(Exception e) {
            Assert.fail("Failed to select '" + text + "' from dropdown: [" + elementName + "]");
        }
    }

}
