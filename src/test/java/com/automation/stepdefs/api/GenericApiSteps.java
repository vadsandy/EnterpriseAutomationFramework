package com.automation.stepdefs;

import com.automation.api.RestClient;
import com.automation.context.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.testng.Assert;

public class GenericApiSteps {

    private TestContext testContext;
    private RestClient restClient;

    public GenericApiSteps(TestContext context) {
        this.testContext = context;
    }

    @Given("the API base URI is {string}")
    public void the_api_base_uri_is(String baseUri) {
        // Initialize our new universal engine
        restClient = new RestClient(baseUri);
    }

    @Given("I set the request body to:")
    public void i_set_the_request_body_to(String payload) {
        restClient.setBody(payload);
    }

    @When("I send a POST request to {string}")
    public void i_send_a_post_request_to(String endpoint) {
        Response response = restClient.post(endpoint);
        testContext.setContext("apiResponse", response);
    }

    @When("I send a GET request to {string}")
    public void i_send_a_get_request_to(String endpoint) {
        Response response = restClient.get(endpoint);
        testContext.setContext("apiResponse", response);
    }

    @Then("the API response status code should be {int}")
    public void the_api_response_status_code_should_be(Integer expectedStatusCode) {
        Response response = (Response) testContext.getContext("apiResponse");
        Assert.assertEquals(response.getStatusCode(), (int) expectedStatusCode, "Status code mismatch!");
    }

    @Then("the response JSON should contain {string} as {string}")
    public void the_response_json_should_contain_as(String jsonPath, String expectedValue) {
        Response response = (Response) testContext.getContext("apiResponse");
        String actualValue = response.jsonPath().getString(jsonPath);
        Assert.assertEquals(actualValue, expectedValue, "JSON response data mismatch!");
    }

    // 1. Change the header for SOAP
    @Given("I set the Content-Type header to {string}")
    public void i_set_the_content_type_header_to(String contentType) {
        restClient.addHeaders(java.util.Map.of("Content-Type", contentType));
    }

    // 2. Pass an XML payload (works exactly the same as JSON)
    @Given("I set the SOAP request body to:")
    public void i_set_the_soap_request_body_to(String xmlPayload) {
        restClient.setBody(xmlPayload);
    }

    // 3. Assert using RestAssured's native XML Path
    @Then("the response XML should contain {string} as {string}")
    public void the_response_xml_should_contain_as(String xmlPath, String expectedValue) {
        io.restassured.response.Response response = (io.restassured.response.Response) testContext.getContext("apiResponse");
        String actualValue = response.xmlPath().getString(xmlPath);
        org.testng.Assert.assertEquals(actualValue, expectedValue, "SOAP XML response data mismatch!");
    }
}