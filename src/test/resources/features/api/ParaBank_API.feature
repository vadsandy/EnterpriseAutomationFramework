@Regression @API
Feature: ParaBank Backend API Validations

  Scenario: Validate Customer Login via API
    # ParaBank uses URL parameters for their login API
    Given the API base URI is "https://parabank.parasoft.com/parabank/services/bank"
    When I send a GET request to "/login/john/demo"
    Then the API response status code should be 200
    # ParaBank returns XML by default for this endpoint, so we verify a node
    And the response JSON should contain "customer.firstName" as "John"

  Scenario: Create a new post via REST API
    Given the API base URI is "https://jsonplaceholder.typicode.com"
    And I set the request body to:
      """
      {
        "title": "foo",
        "body": "bar",
        "userId": 1
      }
      """
    When I send a POST request to "/posts"
    Then the API response status code should be 201
    And the response JSON should contain "title" as "foo"