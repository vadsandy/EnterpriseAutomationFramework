@Regression @Auth
Feature: Authentication and Data Fetching

  @Smoke
  Scenario: Successful login using hardcoded credentials
    Given I navigate to the ParaBank login page
    When I login with valid credentials "john" and "demo"
    Then I should be logged in successfully

  @API
  Scenario: Login using dynamic API test data
    Given I navigate to the ParaBank login page
    When I fetch username from API endpoint "https://jsonplaceholder.typicode.com/users/1" and login

  @Database
  Scenario: Login using SQL database test data
    Given I navigate to the ParaBank login page
    When I fetch credentials from the database for role "Admin" and login