package com.automation.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class LoginPage extends BasePage{
    @FindBy(name = "username")
    private WebElement txtUsername;

    @FindBy(name = "password")
    private WebElement txtPassword;

    @FindBy(xpath = "//input[@value='Log In']")
    private WebElement btnLogin;

    // A list of web elements to demonstrate Java Streams
    @FindBy(css = "#headerPanel ul.leftmenu li a")
    private List<WebElement> leftMenuLinks;

    public void login(String username, String password) {
        action.type(txtUsername, username,  "Username input field");
        action.type(txtPassword, password, "Password input field");
        action.click(btnLogin, "Login Button");
    }

    public void clickMenuLink(String linkText){
        // Using Java Streams to find the matching element instead of a for-loop
        WebElement targetLink = leftMenuLinks.stream()
                .filter(link -> link.getText().trim().equalsIgnoreCase(linkText))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Menu link '" + linkText + "' was not found on the page."));

        action.click(targetLink, linkText+" menu link");
    }

}
