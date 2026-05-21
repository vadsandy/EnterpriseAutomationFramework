Feature: Authentication Functionality

  Scenario: Successful login and state sharing
    Given I navigate to the ParaBank login page
    When I login with valid credentials "john" and "demo"
    Then I should be logged in successfully
    And the login state should be saved for future dependent tests
