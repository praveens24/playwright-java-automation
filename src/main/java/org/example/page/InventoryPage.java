package org.example.page;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;
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
