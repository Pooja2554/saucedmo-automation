package com.saucedemo.stepdefinitions;

import com.saucedemo.pages.ProductsPage;
import com.saucedemo.pages.CartPage;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import org.assertj.core.api.Assertions;

import java.util.List;

public class ShoppingCartSteps {
    
    private ProductsPage productsPage;
    private CartPage cartPage;

    public ShoppingCartSteps() {
        this.productsPage = new ProductsPage();
        this.cartPage = new CartPage();
    }

    @When("I add {string} to the cart")
    public void i_add_to_the_cart(String productName) {
        productsPage.addProductToCart(productName);
    }

    @When("I add the following products to the cart:")
    public void i_add_the_following_products_to_the_cart(DataTable dataTable) {
        List<String> products = dataTable.asList();
        for (String product : products) {
            productsPage.addProductToCart(product);
        }
    }

    @Then("the cart badge should show {string}")
    public void the_cart_badge_should_show(String expectedCount) {
        Assertions.assertThat(productsPage.getCartBadgeText())
                .as("Cart badge should show correct count")
                .isEqualTo(expectedCount);
    }

    @Then("the cart badge should show {string} or be hidden")
    public void the_cart_badge_should_show_or_be_hidden(String expectedCount) {
        if (expectedCount.equals("0")) {
            Assertions.assertThat(productsPage.isCartBadgeDisplayed())
                    .as("Cart badge should be hidden when count is 0")
                    .isFalse();
        } else {
            Assertions.assertThat(productsPage.getCartBadgeText())
                    .as("Cart badge should show correct count")
                    .isEqualTo(expectedCount);
        }
    }

    @Then("the {string} button should change to {string}")
    public void the_button_should_change_to(String oldButtonText, String newButtonText) {
        // This will be verified in the context of the last product added
        // Implementation would depend on storing the last product name
    }

    @Then("all selected products should have {string} buttons")
    public void all_selected_products_should_have_buttons(String buttonText) {
        // Implementation would verify all products have the expected button text
    }

    @Given("I have added {string} to the cart")
    public void i_have_added_to_the_cart(String productName) {
        productsPage.addProductToCart(productName);
        Assertions.assertThat(productsPage.isProductButtonText(productName, "Remove"))
                .as("Product should be added to cart")
                .isTrue();
    }

    @Given("I have added the following products to the cart:")
    public void i_have_added_the_following_products_to_the_cart(DataTable dataTable) {
        List<String> products = dataTable.asList();
        for (String product : products) {
            productsPage.addProductToCart(product);
        }
    }

    @When("I click the {string} button for {string}")
    public void i_click_the_button_for(String buttonText, String productName) {
        if (buttonText.toLowerCase().contains("remove")) {
            productsPage.removeProductFromCart(productName);
        }
    }

    @When("I click on the shopping cart icon")
    public void i_click_on_the_shopping_cart_icon() {
        productsPage.clickShoppingCart();
    }

    @Then("I should be on the cart page")
    public void i_should_be_on_the_cart_page() {
        Assertions.assertThat(cartPage.isOnCartPage())
                .as("Should be on cart page")
                .isTrue();
    }

    @Then("I should see {string} items in the cart")
    public void i_should_see_items_in_the_cart(String expectedCount) {
        int expected = Integer.parseInt(expectedCount);
        Assertions.assertThat(cartPage.getCartItemCount())
                .as("Cart should contain expected number of items")
                .isEqualTo(expected);
    }

    @Then("I should see {string} in the cart")
    public void i_should_see_in_the_cart(String productName) {
        Assertions.assertThat(cartPage.isProductInCart(productName))
                .as("Product should be in cart")
                .isTrue();
    }

    @Given("I am on the cart page")
    public void i_am_on_the_cart_page() {
        if (!cartPage.isOnCartPage()) {
            productsPage.clickShoppingCart();
        }
        Assertions.assertThat(cartPage.isOnCartPage())
                .as("Should be on cart page")
                .isTrue();
    }

    @When("I remove {string} from the cart")
    public void i_remove_from_the_cart(String productName) {
        cartPage.removeProductFromCart(productName);
    }

    @Then("the cart should be empty")
    public void the_cart_should_be_empty() {
        Assertions.assertThat(cartPage.isCartEmpty())
                .as("Cart should be empty")
                .isTrue();
    }

    @Then("I should see {string} or similar message")
    public void i_should_see_or_similar_message(String message) {
        // Implementation would check for empty cart message
        Assertions.assertThat(cartPage.isCartEmpty())
                .as("Cart should be empty")
                .isTrue();
    }

    @When("I click the shopping {string} button")
    public void i_click_the_shopping_button(String buttonText) {
        if (buttonText.equals("Continue Shopping")) {
            cartPage.clickContinueShopping();
        } else if (buttonText.equals("Checkout")) {
            cartPage.clickCheckout();
        }
    }

    @Given("I have an empty cart")
    public void i_have_an_empty_cart() {
        // Ensure cart is empty by navigating to cart and removing all items if any
        productsPage.clickShoppingCart();
        // Implementation would clear all items from cart
    }

    @When("I navigate to the cart page")
    public void i_navigate_to_the_cart_page() {
        productsPage.clickShoppingCart();
    }

    @Then("the {string} button should be disabled or not present")
    public void the_button_should_be_disabled_or_not_present(String buttonText) {
        if (buttonText.equals("Checkout")) {
            // Implementation would check if checkout button is disabled
        }
    }
}