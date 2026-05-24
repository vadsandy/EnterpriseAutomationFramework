package com.automation.stepdefs;

import com.automation.config.ConfigReader;
import com.automation.context.TestContext;
import com.automation.core.DriverManager;
import com.automation.pages.LoginPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Allure;
import org.testng.Assert;

public class LoginSteps {

    private TestContext testContext;
    private LoginPage loginPage;

    public LoginSteps(TestContext context) {
        this.testContext = context;
        this.loginPage = new LoginPage();
    }

    @Given("I navigate to the ParaBank login page")
    public void i_navigate_to_the_para_bank_login_page() {
        String url = ConfigReader.getBaseUrl();
        DriverManager.getDriver().get(url);
        Allure.step("Navigated to URL: " + url);
    }

    @When("I login with valid credentials {string} and {string}")
    public void i_login_with_valid_credentials_and(String username, String password) {
        loginPage.login(username, password);
        testContext.setContext("loggedInUser", username);
        Allure.step("Logged in manually with user: " + username);
    }

    @When("I fetch username from API endpoint {string} and login")
    public void i_fetch_username_from_api_endpoint_and_login(String apiEndpoint) {
        String apiUsername = com.automation.datamanager.DataManager.getApiData(apiEndpoint, "username");
        Allure.step("Successfully extracted user from API: " + apiUsername);
        loginPage.login(apiUsername, "demo_password");
    }

    @When("I fetch username from JSON file {string} and login")
    public void i_fetch_username_from_json_file_and_login(String fileName) {
        String jsonUser = com.automation.datamanager.DataManager.getJsonData(fileName, "/adminUser/username");
        String jsonPass = com.automation.datamanager.DataManager.getJsonData(fileName, "/adminUser/password");
        Allure.step("Successfully extracted user from JSON: " + jsonUser);
        loginPage.login(jsonUser, jsonPass);
        testContext.setContext("loggedInUser", jsonUser);
    }

    @When("I fetch credentials from the database for role {string} and login")
    public void i_fetch_credentials_from_the_database_for_role_and_login(String role) {
        String query = "SELECT Username, UserPassword FROM AutomationUsers WHERE UserRole = '" + role + "'";
        String dbUser = com.automation.datamanager.DataManager.getSqlData(query, "Username");
        String dbPass = com.automation.datamanager.DataManager.getSqlData(query, "UserPassword");

        Allure.step("Successfully queried DB. Found User: " + dbUser);
        loginPage.login(dbUser, dbPass);
        testContext.setContext("loggedInUser", dbUser);
    }

    @Then("I should be logged in successfully")
    public void i_should_be_logged_in_successfully() {
        String title = DriverManager.getDriver().getTitle();
        Assert.assertTrue(title.contains("ParaBank"), "Login did not reach the expected dashboard.");
        Allure.step("Verified Dashboard Title.");
    }

    @Then("the login state should be saved for future dependent tests")
    public void the_login_state_should_be_saved_for_future_dependent_tests() {
        boolean hasUser = testContext.isContains("loggedInUser");
        Assert.assertTrue(hasUser, "User state was not saved in TestContext!");

        String savedUser = (String) testContext.getContext("loggedInUser");
        Allure.step("Verified shared PicoContainer state holds user: " + savedUser);
    }

    @Then("the accounts overview page should greet the user")
    public void the_accounts_overview_page_should_greet_the_user() {
        String actualGreeting = loginPage.getWelcomeMessage();
        Allure.step("UI Greeting reads: " + actualGreeting);
        Assert.assertTrue(actualGreeting.contains("Welcome"),
                "The welcome message container failed to load on the dashboard.");
    }
}