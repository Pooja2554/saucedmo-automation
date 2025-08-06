package com.saucedemo.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    @FindBy(id = "user-name")
    private WebElement usernameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    @FindBy(css = "[data-test='error']")
    private WebElement errorMessage;

    @FindBy(className = "login_logo")
    private WebElement loginLogo;

    @FindBy(className = "login_wrapper")
    private WebElement loginForm;

    public void enterUsername(String username) {
        enterText(usernameField, username);
    }

    public void enterPassword(String password) {
        enterText(passwordField, password);
    }

    public void clickLoginButton() {
        clickElement(loginButton);
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }

    public String getErrorMessage() {
        waitForElementToBeVisible(errorMessage);
        return getText(errorMessage);
    }

    public boolean isErrorMessageDisplayed() {
        return isElementDisplayed(errorMessage);
    }

    public boolean isLoginFormDisplayed() {
        return isElementDisplayed(loginForm);
    }

    public boolean isOnLoginPage() {
        return getCurrentUrl().contains("saucedemo.com") && isElementDisplayed(loginLogo);
    }
}