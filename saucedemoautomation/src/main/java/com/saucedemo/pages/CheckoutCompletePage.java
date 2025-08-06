package com.saucedemo.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CheckoutCompletePage extends BasePage {

    @FindBy(className = "complete-header")
    private WebElement completeHeader;

    public boolean isOnCheckoutCompletePage() {
        return getCurrentUrl().contains("checkout-complete.html");
    }


    public boolean isOrderCompleteMessageDisplayed() {
        return isElementDisplayed(completeHeader) && 
               getText(completeHeader).contains("Thank you for your order");
    }
}