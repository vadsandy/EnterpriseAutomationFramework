@Regression @API
Feature: Blog Post API Management

  Scenario: Successfully create a new blog post
    Given I have a dynamically generated post payload
    When I send the payload to the create post endpoint
    Then the post response status code should be 201
    And the response should accurately reflect the generated data