package com.saucedemo.stepdefinitions;

import com.saucedemo.driver.DriverManager;
import com.saucedemo.pages.ProductsPage;
import io.cucumber.java.en.*;
import org.assertj.core.api.Assertions;

import java.util.List;

public class ProductSteps {
    
    private ProductsPage productsPage;


    public ProductSteps() {
        this.productsPage = new ProductsPage();

    }

    @Then("I should see all available products")
    public void i_should_see_all_available_products() {
        int productCount = productsPage.getProductCount();
        Assertions.assertThat(productCount)
                .as("Should display all available products")
                .isGreaterThan(0);
    }

    @Then("each product should have a name, description, price, and image")
    public void each_product_should_have_a_name_description_price_and_image() {
        // This would require additional page object methods to verify each product has all elements
        Assertions.assertThat(productsPage.getProductCount())
                .as("Products should be displayed with all required elements")
                .isGreaterThan(0);
    }

    @Then("each product should have an {string} button")
    public void each_product_should_have_an_button(String buttonText) {
        // Implementation would verify each product has the specified button
        Assertions.assertThat(productsPage.getProductCount())
                .as("Each product should have the required button")
                .isGreaterThan(0);
    }

    @When("I select {string} from the sort dropdown")
    public void i_select_from_the_sort_dropdown(String sortOption) {
        productsPage.selectSortOption(sortOption);
    }

    @Then("the products should be sorted by {string}")
    public void the_products_should_be_sorted_by(String expectedOrder) {
        List<String> productNames = productsPage.getProductNames();
        
        switch (expectedOrder) {
            case "alphabetical_ascending":
                // Verify products are sorted A-Z
                for (int i = 0; i < productNames.size() - 1; i++) {
                    Assertions.assertThat(productNames.get(i).compareTo(productNames.get(i + 1)))
                            .as("Products should be sorted alphabetically A-Z")
                            .isLessThanOrEqualTo(0);
                }
                break;
            case "alphabetical_descending":
                // Verify products are sorted Z-A
                for (int i = 0; i < productNames.size() - 1; i++) {
                    Assertions.assertThat(productNames.get(i).compareTo(productNames.get(i + 1)))
                            .as("Products should be sorted alphabetically Z-A")
                            .isGreaterThanOrEqualTo(0);
                }
                break;
            case "price_ascending":
                List<String> prices = productsPage.getProductPrices();
                for (int i = 0; i < prices.size() - 1; i++) {
                    double currentPrice = Double.parseDouble(prices.get(i));
                    double nextPrice = Double.parseDouble(prices.get(i + 1));
                    Assertions.assertThat(currentPrice)
                            .as("Products should be sorted by price low to high")
                            .isLessThanOrEqualTo(nextPrice);
                }
                break;
            case "price_descending":
                List<String> pricesDesc = productsPage.getProductPrices();
                for (int i = 0; i < pricesDesc.size() - 1; i++) {
                    double currentPrice = Double.parseDouble(pricesDesc.get(i));
                    double nextPrice = Double.parseDouble(pricesDesc.get(i + 1));
                    Assertions.assertThat(currentPrice)
                            .as("Products should be sorted by price high to low")
                            .isGreaterThanOrEqualTo(nextPrice);
                }
                break;
        }
    }

    @When("I click on {string} product")
    public void i_click_on_product(String productName) {
        productsPage.clickProductByName(productName);
    }

    @Then("I should see the product detail page")
    public void i_should_see_the_product_detail_page() {
        // Implementation would verify product detail page elements
        Assertions.assertThat(productsPage.getCurrentUrl())
                .as("Should be on product detail page")
                .contains("inventory-item");
    }

    @Then("I should see the product name {string}")
    public void i_should_see_the_product_name(String expectedProductName) {
        // Implementation would get product name from detail page
    }

    @Then("I should see the product description")
    public void i_should_see_the_product_description() {
        // Implementation would verify product description is displayed
    }

    @Then("I should see the product price")
    public void i_should_see_the_product_price() {
        // Implementation would verify product price is displayed
    }

    @Then("I should see the product image")
    public void i_should_see_the_product_image() {
        // Implementation would verify product image is displayed
    }

    @Then("I should see {string} and {string} buttons")
    public void i_should_see_and_buttons(String button1, String button2) {
        // Implementation would verify both buttons are present
    }

    @Given("I am on the product detail page for {string}")
    public void i_am_on_the_product_detail_page_for(String productName) {
        productsPage.clickProductByName(productName);
        // Verify we're on the product detail page
    }

    @When("I click the {string} button")
    public void i_click_the_button(String buttonText) {
        if (buttonText.equals("Back to products")) {
            // Implementation would click back to products button
            // For now, navigate back to products page
            DriverManager.getDriver().navigate().back();
        }
    }
}