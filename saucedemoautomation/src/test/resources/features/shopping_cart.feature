@ShoppingCart
Feature: Shopping Cart Management
  As a logged-in user
  I want to add and remove products from my shopping cart
  So that I can manage my purchases

  Background:
    Given I navigate to the SauceDemo login page
    And I am logged in as "standard_user" with password "secret_sauce"

  @AddToCart @Smoke
  Scenario: Add single product to cart
    When I add "Sauce Labs Backpack" to the cart
    Then the cart badge should show "1"
    And the "Add to cart" button should change to "Remove"

  @AddToCart
  Scenario: Add multiple products to cart
    When I add the following products to the cart:
      | Sauce Labs Backpack     |
      | Sauce Labs Bike Light   |
      | Sauce Labs Bolt T-Shirt |
    Then the cart badge should show "3"
    And all selected products should have "Remove" buttons

  @RemoveFromCart
  Scenario: Remove product from cart on products page
    Given I have added "Sauce Labs Backpack" to the cart
    When I click the "Remove" button for "Sauce Labs Backpack"
    Then the cart badge should show "0" or be hidden
    And the "Remove" button should change to "Add to cart"

  @ViewCart @Smoke
  Scenario: View cart contents
    Given I have added the following products to the cart:
      | Sauce Labs Backpack   |
      | Sauce Labs Bike Light |
    When I click on the shopping cart icon
    Then I should be on the cart page
    And I should see "2" items in the cart
    And I should see "Sauce Labs Backpack" in the cart


  @RemoveFromCart
  Scenario: Remove product from cart page
    Given I have added "Sauce Labs Backpack" to the cart
    And I am on the cart page
    When I remove "Sauce Labs Backpack" from the cart
    Then the cart should be empty
    And I should see "Your cart is empty" or similar message

  @ContinueShopping
  Scenario: Continue shopping from cart
    Given I am on the cart page
    When I click the shopping "Continue Shopping" button
    Then I should be redirected to the products page