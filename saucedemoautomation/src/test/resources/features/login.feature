@Login
Feature: User Authentication

  Background:
    Given I navigate to the SauceDemo login page

  @LoginSuccess
  Scenario Outline: Successful login with valid credentials
    When I enter username "<username>" and password "<password>"
    And I click the login button
    Then I should be redirected to the products page

    Examples:
      | username                | password     |
      | standard_user           | secret_sauce |


  @NegativeLogin
  Scenario Outline: Login failure with invalid credentials
    When I enter username "<username>" and password "<password>"
    And I click the login button
    Then I should see an error message "<error_message>"
    And I should remain on the login page

    Examples:
      | username      | password      | error_message                                                                 |
      | invalid_user  | secret_sauce  | Epic sadface: Username and password do not match any user in this service    |
      | standard_user | wrong_pass    | Epic sadface: Username and password do not match any user in this service    |




  @Logout
  Scenario: Successful logout
    Given I am logged in as "standard_user" with password "secret_sauce"
    When I click on the hamburger menu
    And I click on the logout link
    Then I should be redirected to the login page
    And I should see the login form