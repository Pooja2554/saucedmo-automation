package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class CartPage extends BasePage {

    @FindBy(className = "title")
    private WebElement pageTitle;

    @FindBy(id = "continue-shopping")
    private WebElement continueShoppingButton;

    @FindBy(id = "checkout")
    private WebElement checkoutButton;

    @FindBy(className = "cart_item")
    private List<WebElement> cartItems;

    @FindBy(className = "inventory_item_name")
    private List<WebElement> itemNames;


    public boolean isOnCartPage() {
        return getCurrentUrl().contains("cart.html") && isElementDisplayed(pageTitle);
    }

    public String getPageTitle() {
        return getText(pageTitle);
    }

    public void clickContinueShopping() {
        clickElement(continueShoppingButton);
    }

    public void clickCheckout() {

        clickElement(checkoutButton);
    }

    public int getCartItemCount() {
        return cartItems.size();
    }

    public boolean isProductInCart(String productName) {
        return itemNames.stream()
                .anyMatch(item -> getText(item).equals(productName));
    }

    public void removeProductFromCart(String productName) {
        WebElement removeButton = driver.findElement(
            By.xpath("//div[text()='" + productName + "']/ancestor::div[@class='cart_item']//button[contains(@id,'remove')]")
        );
        clickElement(removeButton);
    }

    public boolean isCartEmpty() {
        return cartItems.isEmpty();
    }


}