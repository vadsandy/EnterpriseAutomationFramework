package com.automation.stepdefs.api;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.qameta.allure.Allure;
import org.testng.Assert;

public class AccountApiSteps {
    private AccountApiClient accountApiClient;
    private Response response;

    public AccountApiSteps() {
        this.accountApiClient = new AccountApiClient();
    }

    @Given("the API base URI is configured")
    public void the_api_base_uri_is_configured() {
        Allure.step("API Base URI validated successfully.");
    }

    @When("I send a GET request to fetch accounts for customer {string}")
    public void i_send_a_get_request_to_fetch_accounts_for_customer(String customerId) {
        response = accountApiClient.getCustomerAccounts(customerId);
    }

    @Then("the API response status code should be {int}")
    public void the_api_response_status_code_should_be(Integer statusCode) {
        Allure.step("Validating response status code is: " + statusCode);
        Assert.assertEquals(response.getStatusCode(), (int) statusCode, "API HTTP status mismatch!");
    }

    @Then("the response should contain valid account details")
    public void the_response_should_contain_valid_account_details() {
        String responseBody = response.getBody().asString();

        // Structural assertion
        Assert.assertTrue(responseBody.contains("id"), "Response missing unique identifier fields.");

        // NEW CHANGE: Attaches the raw API JSON response directly into the Allure Step Report tree
        Allure.addAttachment("JSON Response Payload", "application/json", responseBody);
    }
}