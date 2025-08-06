package com.saucedemo.stepdefinitions;

import com.saucedemo.config.ConfigReader;
import com.saucedemo.driver.DriverManager;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.pages.ProductsPage;
import io.cucumber.java.en.*;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.Alert;

public class LoginSteps {
    
    private LoginPage loginPage;
    private ProductsPage productsPage;

    public LoginSteps() {
        this.loginPage = new LoginPage();
        this.productsPage = new ProductsPage();
    }

    @Given("I navigate to the SauceDemo login page")
    public void i_navigate_to_the_sauce_demo_login_page() {
        DriverManager.getDriver().get(ConfigReader.getBaseUrl());
    }

    @When("I enter username {string} and password {string}")
    public void i_enter_username_and_password(String username, String password) {
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
    }

    @When("I click the login button")
    public void i_click_the_login_button() {
        // Alert alert= DriverManager.getDriver().switchTo().alert();
        loginPage.clickLoginButton();
        // alert.accept();



    }

    @Then("I should be redirected to the products page")
    public void i_should_be_redirected_to_the_products_page() {
        Assertions.assertThat(productsPage.isOnProductsPage())
                .as("Should be redirected to products page")
                .isTrue();
    }

    @Then("I should see the products page header")
    public void i_should_see_the_products_page_header() {
        Assertions.assertThat(productsPage.getPageTitle())
                .as("Products page should have correct title")
                .isEqualTo("Products");
    }

    @Then("I should see the hamburger menu")
    public void i_should_see_the_hamburger_menu() {
        Assertions.assertThat(productsPage.isHamburgerMenuDisplayed())
                .as("Hamburger menu should be displayed")
                .isTrue();
    }

    @Then("I should see an error message {string}")
    public void i_should_see_an_error_message(String expectedErrorMessage) {
        Assertions.assertThat(loginPage.isErrorMessageDisplayed())
                .as("Error message should be displayed")
                .isTrue();
        
        Assertions.assertThat(loginPage.getErrorMessage())
                .as("Error message should match expected text")
                .isEqualTo(expectedErrorMessage);
    }

    @Then("I should remain on the login page")
    public void i_should_remain_on_the_login_page() {
        Assertions.assertThat(loginPage.isOnLoginPage())
                .as("Should remain on login page")
                .isTrue();
    }

    @Given("I am logged in as {string} with password {string}")
    public void i_am_logged_in_as_with_password(String username, String password) {
        if (!productsPage.isOnProductsPage()) {
            if (!loginPage.isOnLoginPage()) {
                DriverManager.getDriver().get(ConfigReader.getBaseUrl());
            }
            loginPage.login(username, password);
            Assertions.assertThat(productsPage.isOnProductsPage())
                    .as("Should be logged in and on products page")
                    .isTrue();
        }
    }

    @When("I click on the hamburger menu")
    public void i_click_on_the_hamburger_menu() {
        productsPage.clickHamburgerMenu();
    }

    @When("I click on the logout link")
    public void i_click_on_the_logout_link() {
        productsPage.clickLogout();
    }

    @Then("I should be redirected to the login page")
    public void i_should_be_redirected_to_the_login_page() {
        Assertions.assertThat(loginPage.isOnLoginPage())
                .as("Should be redirected to login page")
                .isTrue();
    }

    @Then("I should see the login form")
    public void i_should_see_the_login_form() {
        Assertions.assertThat(loginPage.isLoginFormDisplayed())
                .as("Login form should be displayed")
                .isTrue();
    }
}