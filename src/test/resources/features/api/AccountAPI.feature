@Regression @API
Feature: Account Management REST API Validation

  @Smoke @GetAccountDetails
  Scenario: Verify customer account details via backend REST API
    Given the API base URI is configured
    When I send a GET request to fetch accounts for customer "1"
    Then the API response status code should be 200
    And the response should contain valid account details