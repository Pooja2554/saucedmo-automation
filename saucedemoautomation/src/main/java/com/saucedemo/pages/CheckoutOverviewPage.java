package com.saucedemo.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class CheckoutOverviewPage extends BasePage {

    @FindBy(className = "title")
    private WebElement pageTitle;

    @FindBy(id = "finish")
    private WebElement finishButton;

    @FindBy(id = "cancel")
    private WebElement cancelButton;

    @FindBy(className = "cart_item")
    private List<WebElement> cartItems;

    @FindBy(className = "summary_total_label")
    private WebElement totalPrice;

    @FindBy(className = "summary_subtotal_label")
    private WebElement subtotalPrice;

    @FindBy(className = "summary_tax_label")
    private WebElement taxAmount;

    public boolean isOnCheckoutOverviewPage() {
        return getCurrentUrl().contains("checkout-step-two.html") && isElementDisplayed(pageTitle);
    }

    public String getPageTitle() {
        return getText(pageTitle);
    }

    public void clickFinish() {
        clickElement(finishButton);
    }

    public void clickCancel() {
        clickElement(cancelButton);
    }


    public String getTotalPrice() {
        return getText(totalPrice);
    }


    public boolean isOrderSummaryDisplayed() {
        return isElementDisplayed(totalPrice) && isElementDisplayed(subtotalPrice);
    }
}