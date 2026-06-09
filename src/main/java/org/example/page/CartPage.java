package org.example.page;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class CartPage extends BasePage {

    private final String pageHeader = "[data-test='title']";
    private final String cartItems = "[data-test='inventory-item']";
    private final String cartItemName = "[data-test='inventory-item-name']";
    private final String continueShoppingBtn = "#continue-shopping";
    private final String checkoutBtn = "#checkout";

    public CartPage(Page page) {
        super(page);
    }

    public void clickContinueShopping() {
        click(continueShoppingBtn);
    }

    public void clickCheckout() {
        click(checkoutBtn);
    }

    public void removeProductFromCart(String productName) {
        // Dynamically locate the remove button matching this specific item card
        page.locator(cartItems)
                .filter(new Locator.FilterOptions().setHasText(productName))
                .locator("button")
                .click();
    }


    public Locator getPageHeaderLocator() { return page.locator(pageHeader); }
    public Locator getCartItemsLocator() { return page.locator(cartItems); }

    public void assertItemIsPresentInCart(String productName) {
        assertThat(page.locator(cartItemName).filter(new Locator.FilterOptions().setHasText(productName))).isVisible();
    }

    public void assertItemIsHiddenInCart(String productName) {
        assertThat(page.locator(cartItemName).filter(new Locator.FilterOptions().setHasText(productName))).isHidden();
    }
}
