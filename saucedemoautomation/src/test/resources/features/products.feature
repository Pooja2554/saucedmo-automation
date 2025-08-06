@Products
Feature: Product Management
  Background:
    Given I navigate to the SauceDemo login page
    And I am logged in as "standard_user" with password "secret_sauce"

  @ProductDisplay @Smoke
  Scenario: View all products on products page
    Then I should see all available products
    And each product should have a name, description, price, and image
    And each product should have an "Add to cart" button


  @ProductDetails
  Scenario: View individual product details
    When I click on "Sauce Labs Backpack" product
    Then I should see the product detail page
    And I should see the product name "Sauce Labs Backpack"
    And I should see the product description
    And I should see the product price
    And I should see the product image
    And I should see "Add to cart" and "Back to products" buttons

  @ProductNavigation
  Scenario: Navigate back to products from product detail
    Given I am on the product detail page for "Sauce Labs Backpack"
    When I click the "Back to products" button
    Then I should be redirected to the products page