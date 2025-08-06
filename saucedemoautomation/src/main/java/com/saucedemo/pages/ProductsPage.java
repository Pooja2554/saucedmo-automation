package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

public class ProductsPage extends BasePage {

    @FindBy(className = "title")
    private WebElement pageTitle;

    @FindBy(id = "react-burger-menu-btn")
    private WebElement hamburgerMenu;

    @FindBy(id = "logout_sidebar_link")
    private WebElement logoutLink;

    @FindBy(className = "shopping_cart_link")
    private WebElement shoppingCartIcon;

    @FindBy(className = "shopping_cart_badge")
    private WebElement cartBadge;

    @FindBy(className = "product_sort_container")
    private WebElement sortDropdown;

    @FindBy(className = "inventory_item")
    private List<WebElement> productItems;

    @FindBy(className = "inventory_item_name")
    private List<WebElement> productNames;

    @FindBy(className = "inventory_item_price")
    private List<WebElement> productPrices;

    public boolean isOnProductsPage() {

        return getCurrentUrl().contains("https://www.saucedemo.com/inventory.html") && isElementDisplayed(pageTitle);
    }

    public String getPageTitle() {
        return getText(pageTitle);
    }

    public boolean isHamburgerMenuDisplayed() {
        return isElementDisplayed(hamburgerMenu);
    }

    public void clickHamburgerMenu() {
        clickElement(hamburgerMenu);
    }

    public void clickLogout() {
        waitForElementToBeVisible(logoutLink);
        clickElement(logoutLink);
    }

    public void addProductToCart(String productName) {
        WebElement addToCartButton = driver.findElement(
            By.xpath("//div[text()='" + productName + "']/ancestor::div[@class='inventory_item']//button[contains(@id,'add-to-cart')]")
        );
        clickElement(addToCartButton);
    }

    public void removeProductFromCart(String productName) {
        WebElement removeButton = driver.findElement(
            By.xpath("//div[text()='" + productName + "']/ancestor::div[@class='inventory_item']//button[contains(@id,'remove')]")
        );
        clickElement(removeButton);
    }

    public String getCartBadgeText() {
        if (isElementDisplayed(cartBadge)) {
            return getText(cartBadge);
        }
        return "0";
    }

    public boolean isCartBadgeDisplayed() {
        return isElementDisplayed(cartBadge);
    }

    public void clickShoppingCart() {
        clickElement(shoppingCartIcon);
    }

    public void selectSortOption(String option) {
        Select select = new Select(sortDropdown);
        select.selectByVisibleText(option);
    }

    public List<String> getProductNames() {
        List<String> productTexts = new ArrayList<>();
        for (WebElement product : productNames) {
            productTexts.add(getText(product));
        }
        return productTexts;
    }

    public List<String> getProductPrices() {
        List<String> prices = new ArrayList<>();
        for (WebElement element : productPrices) {
            String text = getText(element).replace("$", "");
            prices.add(text);
        }
        return prices;
    }

    public int getProductCount() {
        return productItems.size();
    }

    public void clickProductByName(String productName) {
        WebElement productLink = driver.findElement(
            By.xpath("//div[text()='" + productName + "']")
        );
        clickElement(productLink);
    }

    public boolean isProductButtonText(String productName, String buttonText) {
        try {
            WebElement button = driver.findElement(
                By.xpath("//div[text()='" + productName + "']/ancestor::div[@class='inventory_item']//button")
            );
            return getText(button).toLowerCase().contains(buttonText.toLowerCase());
        } catch (Exception e) {
            return false;
        }
    }
}