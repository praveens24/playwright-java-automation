package org.example.page;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;

import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class InventoryPage extends BasePage {
    private final String pageHeader = "[data-test='title']";
    private final String menuButton = "#react-burger-menu-btn";
    private final String allItemsMenu = "#inventory_sidebar_link";
    private final String aboutMenu = "#about_sidebar_link";
    private final String logoutMenu = "#logout_sidebar_link";
    private final String resetAppStateMenu = "#reset_sidebar_link";
    private final String menuCloseButton = "#react-burger-cross-btn";
    private final String sortDropdown = "[data-test='product-sort-container']";
    private final String shoppingCart = "[data-test='shopping-cart-link']";
    private final String shoppingCartBadge = "[data-test='shopping-cart-badge']";
    private final String inventoryItems = "[data-test='inventory-item']";
    private final String inventoryItemName = "[data-test='inventory-item-name']";
    private final String inventoryItemPrice = "[data-test='inventory-item-price']";

    public InventoryPage(Page page) {
        super(page);
    }

    public String pageTitle() {
        return getText(pageHeader);
    }

    public String getNameOfMenuButton() {
        return getText(menuButton);
    }

    public void clickMenuButton() {
        click(menuButton);
    }

    public void clickMenuCloseButton() {
        click(menuCloseButton);
    }

    public String getNameOfMenuCloseButton() {
        return getText(menuCloseButton);
    }

    // Actions
    public void selectSortOption(String optionValue) {
        page.selectOption(sortDropdown, optionValue);
    }

    public void addProductToCart(String productName) {
        // Dynamically locate the button within the specific product card
        page.locator("[data-test='inventory-item']")
                .filter(new Locator.FilterOptions().setHasText(productName))
                .locator("button")
                .click();
    }

    public void removeProductFromCart(String productName) {
        page.locator("[data-test='inventory-item']")
                .filter(new Locator.FilterOptions().setHasText(productName))
                .locator("button")
                .click();
    }

    // Locator Expositions for Playwright Assertions
    public Locator getPageHeaderLocator() { return page.locator(pageHeader); }
    public Locator getSortDropdownLocator() { return page.locator(sortDropdown); }
    public Locator getCartBadgeLocator() { return page.locator(shoppingCartBadge); }
    public Locator getInventoryItemsLocator() { return page.locator(inventoryItems); }

    public List<String> getAllItemNames() {
        return page.locator(inventoryItemName).allTextContents();
    }

    public List<Double> getAllItemPrices() {
        return page.locator(inventoryItemPrice).allTextContents().stream()
                .map(price -> Double.parseDouble(price.replace("$", "")))
                .toList();
    }
   public void assertSideBarMenuIsVisible() {
        assertThat(page.locator(allItemsMenu)).isVisible();
        assertThat(page.locator(aboutMenu)).isVisible();
        assertThat(page.locator(logoutMenu)).isVisible();
        assertThat(page.locator(resetAppStateMenu)).isVisible();
        assertThat(page.locator(menuCloseButton)).isVisible();
    }
 public void assertSideBarMenuIsClosed() {
        assertThat(page.locator(allItemsMenu)).isHidden();
        assertThat(page.locator(aboutMenu)).isHidden();
        assertThat(page.locator(logoutMenu)).isHidden();
        assertThat(page.locator(resetAppStateMenu)).isHidden();
        assertThat(page.locator(menuCloseButton)).isHidden();
    }

    public void assertProductSortIsVisible() {
        assertThat(page.locator(sortDropdown)).isVisible();
    }

    public void assertKartIsVisible() {
        assertThat(page.locator(shoppingCart)).isVisible();
    }
}
