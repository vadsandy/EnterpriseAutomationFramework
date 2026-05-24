package com.automation.pages;

import com.automation.core.DriverManager;
import com.automation.utils.ElementActions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class BasePage {
    protected WebDriver driver;
    protected ElementActions action;

    public BasePage(){
        this.driver = DriverManager.getDriver();
        this.action = new ElementActions();

        // Initializes all @FindBy annotations in child classes
        // PageFactory.initElements(driver, this); : Removing this as pagefactory is not required to avoid staleElementException
    }
}
