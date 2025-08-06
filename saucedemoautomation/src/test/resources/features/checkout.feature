@Checkout
Feature: Checkout Process
  As a user with items in my cart
  I want to complete the checkout process
  So that I can purchase my selected items

  Background:
    Given I navigate to the SauceDemo login page
    And I am logged in as "standard_user" with password "secret_sauce"
    And I have added "Sauce Labs Backpack" to the cart

  @Checkout @Smoke @E2E
  Scenario: Complete checkout with valid information
    Given I am on the cart page
    When I click the checkout "Checkout" button
    And I enter the following checkout information:
      | First Name | John    |
      | Last Name  | Doe     |
      | Postal Code| 12345   |
    And I click the checkout "Continue" button
    Then I should be on the checkout overview page
    And I should see the order summary
    And I should see the total price
    When I click the checkout "Finish" button
    Then I should see the checkout complete page
    And I should see "Thank you for your order!" message


  @CheckoutValidation
  Scenario Outline: Checkout form validation
    Given I am on the cart page
    When I click the checkout "Checkout" button
    And I enter the following checkout information:
      | First Name  | <first_name>  |
      | Last Name   | <last_name>   |
      | Postal Code | <postal_code> |
    And I click the checkout "Continue" button
    Then I should see an checkout error message "<error_message>"

    Examples:
      | first_name | last_name | postal_code | error_message                    |
      |            | Doe       | 12345       | Error: First Name is required    |
      | John       |           | 12345       | Error: Last Name is required     |
      | John       | Doe       |             | Error: Postal Code is required   |

  @CheckoutCancel
  Scenario: Cancel checkout process
    Given I am on the cart page
    When I click the checkout "Checkout" button
    And I click the checkout "Cancel" button
    Then I should be redirected to the cart page

  @CheckoutOverviewCancel
  Scenario: Cancel from checkout overview
    Given I am on the checkout overview page with valid information
    When I click the checkout "Cancel" button
    Then I should be redirected to the products page

  @EmptyCartCheckout
  Scenario: Attempt checkout with empty cart
    Given I have an empty cart
    When I navigate to the cart page
    Then the "Checkout" button should be disabled or not present