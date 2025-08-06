package com.saucedemo.stepdefinitions;

import com.saucedemo.pages.CartPage;
import com.saucedemo.pages.CheckoutPage;
import com.saucedemo.pages.CheckoutOverviewPage;
import com.saucedemo.pages.CheckoutCompletePage;
import com.saucedemo.pages.ProductsPage;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import org.assertj.core.api.Assertions;

import java.util.Map;

public class CheckoutSteps {
    
    private CartPage cartPage;
    private CheckoutPage checkoutPage;
    private CheckoutOverviewPage checkoutOverviewPage;
    private CheckoutCompletePage checkoutCompletePage;
    private ProductsPage productsPage;

    public CheckoutSteps() {
        this.cartPage = new CartPage();
        this.checkoutPage = new CheckoutPage();
        this.checkoutOverviewPage = new CheckoutOverviewPage();
        this.checkoutCompletePage = new CheckoutCompletePage();
        this.productsPage = new ProductsPage();
    }

    @When("I enter the following checkout information:")
    public void i_enter_the_following_checkout_information(DataTable dataTable) {
        Map<String, String> checkoutInfo = dataTable.asMap(String.class, String.class);
        
        String firstName = checkoutInfo.get("First Name");
        String lastName = checkoutInfo.get("Last Name");
        String postalCode = checkoutInfo.get("Postal Code");
        
        checkoutPage.fillCheckoutInformation(firstName, lastName, postalCode);
    }

    @When("I click the checkout {string} button")
    public void i_click_the_checkout_button(String buttonText) {
//        System.out.println("button"+buttonText);
//        if(buttonText.equals("Continue")){
//            checkoutPage.clickContinue();
//        }else if(buttonText.equals("Finish")){
//            checkoutOverviewPage.clickFinish();
//        }
        switch (buttonText) {
            case "Continue":
                checkoutPage.clickContinue();
                break;
            case "Cancel":
                if (checkoutPage.isOnCheckoutPage()) {
                    checkoutPage.clickCancel();
                } else if (checkoutOverviewPage.isOnCheckoutOverviewPage()) {
                    checkoutOverviewPage.clickCancel();
                }
                break;
            case "Finish":
                checkoutOverviewPage.clickFinish();
                break;
            case "Checkout":
                cartPage.clickCheckout();
                break;
        }
    }

    @Then("I should be on the checkout overview page")
    public void i_should_be_on_the_checkout_overview_page() {
        Assertions.assertThat(checkoutOverviewPage.isOnCheckoutOverviewPage())
                .as("Should be on checkout overview page")
                .isTrue();
    }

    @Then("I should see the order summary")
    public void i_should_see_the_order_summary() {
        Assertions.assertThat(checkoutOverviewPage.isOrderSummaryDisplayed())
                .as("Order summary should be displayed")
                .isTrue();
    }

    @Then("I should see the total price")
    public void i_should_see_the_total_price() {
        String totalPrice = checkoutOverviewPage.getTotalPrice();
        Assertions.assertThat(totalPrice)
                .as("Total price should be displayed")
                .isNotEmpty()
                .contains("Total:");
    }

    @Then("I should see the checkout complete page")
    public void i_should_see_the_checkout_complete_page() {
        Assertions.assertThat(checkoutCompletePage.isOnCheckoutCompletePage())
                .as("Should be on checkout complete page")
                .isTrue();
    }

    @Then("I should see {string} message")
    public void i_should_see_message(String expectedMessage) {
        if (expectedMessage.contains("Thank you for your order")) {
            Assertions.assertThat(checkoutCompletePage.isOrderCompleteMessageDisplayed())
                    .as("Order complete message should be displayed")
                    .isTrue();
        }
    }

    @Then("I should see an checkout error message {string}")
    public void i_should_see_an_checkout_error_message1(String expectedErrorMessage) {
        Assertions.assertThat(checkoutPage.isErrorMessageDisplayed())
                .as("Error message should be displayed")
                .isTrue();

        Assertions.assertThat(checkoutPage.getErrorMessage())
                .as("Error message should match expected text")
                .contains(expectedErrorMessage.replace("Error: ", ""));
    }

    @Then("I should be redirected to the cart page")
    public void i_should_be_redirected_to_the_cart_page() {
        Assertions.assertThat(cartPage.isOnCartPage())
                .as("Should be redirected to cart page")
                .isTrue();
    }

    @Given("I am on the checkout overview page with valid information")
    public void i_am_on_the_checkout_overview_page_with_valid_information() {
        // Navigate through the checkout process to reach overview page
        if (!cartPage.isOnCartPage()) {
            productsPage.clickShoppingCart();
        }
        
        if (!checkoutOverviewPage.isOnCheckoutOverviewPage()) {
            cartPage.clickCheckout();
            checkoutPage.fillCheckoutInformation("John", "Doe", "12345");
            checkoutPage.clickContinue();
        }
        
        Assertions.assertThat(checkoutOverviewPage.isOnCheckoutOverviewPage())
                .as("Should be on checkout overview page")
                .isTrue();
    }
}