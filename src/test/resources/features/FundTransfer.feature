@Regression @E2E @Transactions
Feature: Fund Transfer User Journeys

  @UserJourney
  Scenario: End to End Fund Transfer utilizing saved state
    Given I navigate to the ParaBank login page
    When I fetch username from JSON file "users.json" and login
    And the login state should be saved for future dependent tests
    And the accounts overview page should greet the user
    And I note the initial account balance
    When I transfer 10 dollars to another account
    Then the account balance should reflect the deduction