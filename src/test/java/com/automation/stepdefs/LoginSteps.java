package com.automation.stepdefs;

import com.automation.config.ConfigReader;
import com.automation.context.TestContext;
import com.automation.core.DriverManager;
import com.automation.pages.LoginPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class LoginSteps {

    private TestContext testContext;
    private LoginPage loginPage;

    public LoginSteps(TestContext context){
        this.testContext = context;
        this.loginPage = new LoginPage();
    }

    @Given("I navigate to the ParaBank login page")
    public void i_navigate_to_the_para_bank_login_page(){
        String url = ConfigReader.getBaseUrl();
        DriverManager.getDriver().get(url);
    }

    @When("I login with valid credentials {string} and {string}")
    public void i_login_with_valid_credentials_and(String username, String password){
        loginPage.login(username, password);
        // Storing the username in the PicoContainer context for downstream use
        testContext.setContext("loggedInUser", username);
    }

    @Then("I should be logged in successfully")
    public void i_should_be_logged_in_successfully(){
        // Usually we assert the page title or a welcome message here
        String title = DriverManager.getDriver().getTitle();
        Assert.assertTrue(title.contains("ParaBank"), "Login did not reach the expected dashboard.");
    }

    @Then("the login state should be saved for future dependent tests")
    public void the_login_state_should_be_saved_for_future_dependent_tests(){
        // Verifying PicoContainer holds our state
        boolean hasUser = testContext.isContains("loggedInUser");
        Assert.assertTrue(hasUser, "User state was not saved in TestContext!");

        String savedUser = (String) testContext.getContext("loggedInUser");
        System.out.println("Shared context verified. Logged in as: " + savedUser);
    }
}
