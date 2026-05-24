package com.automation.pages;

import org.openqa.selenium.By;

public class LoginPage extends BasePage{

    // Modern 'By' locators instead of PageFactory
    private By textUsername = By.name("username");
    private By textPassword = By.name("password");
    private By btnLogin = By.xpath("//input[@value = 'Log In']");

    // Locator to verify successful login
    private By lblWelcomeMessage = By.className("smallText");


    public void login(String username, String password) {
        action.type(textUsername, username, "Username input field");
        action.type(textPassword, password, "Password input field");
        action.click(btnLogin, "Log In button");

    }

    public String getWelcomeMessage(){
        return action.waitForVisibility(lblWelcomeMessage, "Welcome Message").getText();
    }

}
