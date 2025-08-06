package com.saucedemo.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CheckoutPage extends BasePage {

    @FindBy(id = "first-name")
    private WebElement firstNameField;

    @FindBy(id = "last-name")
    private WebElement lastNameField;

    @FindBy(id = "postal-code")
    private WebElement postalCodeField;

    @FindBy(id = "continue")
    private WebElement continueButton;

    @FindBy(id = "cancel")
    private WebElement cancelButton;

    @FindBy(css = "[data-test='error']")
    private WebElement errorMessage;

    @FindBy(className = "title")
    private WebElement pageTitle;

    public boolean isOnCheckoutPage() {
        return getCurrentUrl().contains("checkout-step-one.html") && isElementDisplayed(pageTitle);
    }

    public void enterFirstName(String firstName) {
        enterText(firstNameField, firstName);
    }

    public void enterLastName(String lastName) {
        enterText(lastNameField, lastName);
    }

    public void enterPostalCode(String postalCode) {
        enterText(postalCodeField, postalCode);
    }

    public void fillCheckoutInformation(String firstName, String lastName, String postalCode) {
        if (firstName != null && !firstName.isEmpty()) {
            enterFirstName(firstName);
        }
        if (lastName != null && !lastName.isEmpty()) {
            enterLastName(lastName);
        }
        if (postalCode != null && !postalCode.isEmpty()) {
            enterPostalCode(postalCode);
        }
    }

    public void clickContinue() {
        clickElement(continueButton);
    }

    public void clickCancel() {
        clickElement(cancelButton);
    }

    public String getErrorMessage() {
        waitForElementToBeVisible(errorMessage);
        return getText(errorMessage);
    }

    public boolean isErrorMessageDisplayed() {
        return isElementDisplayed(errorMessage);
    }
}