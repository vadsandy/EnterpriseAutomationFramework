package com.automation.stepdefs.api;

import com.automation.api.data.PostDataFactory;
import com.automation.api.models.PostPayload;
import com.automation.api.services.PostApiService;
import com.automation.context.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.qameta.allure.Allure;
import org.testng.Assert;

public class PostApiSteps {

    private TestContext testContext;
    private PostApiService postService;

    public PostApiSteps(TestContext context) {
        this.testContext = context;
        this.postService = new PostApiService();
    }

    @Given("I have a dynamically generated post payload")
    public void i_have_a_dynamically_generated_post_payload() {
        // 1. Ask the factory for fresh data
        PostPayload newPost = PostDataFactory.createDynamicPost();

        // 2. Save it to PicoContainer so we can assert against it later
        testContext.setContext("requestPayload", newPost);
        Allure.step("Generated Payload Title: " + newPost.getTitle());
    }

    @When("I send the payload to the create post endpoint")
    public void i_send_the_payload_to_the_create_post_endpoint() {
        // 1. Retrieve the payload from PicoContainer
        PostPayload payload = (PostPayload) testContext.getContext("requestPayload");

        // 2. Ask the Service to execute the network call
        Response response = postService.createNewPost(payload);

        // 3. Save the response to PicoContainer
        testContext.setContext("apiResponse", response);
    }

    @Then("the post response status code should be {int}")
    public void the_post_response_status_code_should_be(Integer expectedStatus) {
        Response response = (Response) testContext.getContext("apiResponse");
        Assert.assertEquals(response.getStatusCode(), (int) expectedStatus, "HTTP Status mismatch!");
    }

    @Then("the response should accurately reflect the generated data")
    public void the_response_should_accurately_reflect_the_generated_data() {
        // 1. Get the original data we sent
        PostPayload originalRequest = (PostPayload) testContext.getContext("requestPayload");

        // 2. Get the raw API response
        Response response = (Response) testContext.getContext("apiResponse");

        // 3. Deserialization: Convert the JSON response back into a POJO
        PostPayload actualResponse = response.as(PostPayload.class);

        // 4. Assert the business logic
        Assert.assertEquals(actualResponse.getTitle(), originalRequest.getTitle(), "Title got corrupted!");
        Assert.assertEquals(actualResponse.getUserId(), originalRequest.getUserId(), "User ID mismatch!");
        Allure.step("Successfully validated the API response exactly matches our dynamic POJO.");
    }
}