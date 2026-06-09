package org.example.test;

import org.example.config.TestConfig;
import org.example.page.InventoryPage;
import org.example.page.LoginPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
public class InventoryTest extends BaseTest{
    private InventoryPage inventoryPage;
    @BeforeEach
    void loginAndNavigateToInventory() {
        this.inventoryPage = new LoginPage(page)
                .login(TestConfig.getUser(),TestConfig.getPassword());
        assertThat(inventoryPage.getPageHeaderLocator()).hasText("Products");
    }


    @Test
    void shouldHaveBuggerMenuInLeftSideOfThePage(){
        assertEquals("Products", inventoryPage.pageTitle());
        assertEquals("Open Menu", inventoryPage.getNameOfMenuButton());
        inventoryPage.clickMenuButton();
        inventoryPage.assertSideBarMenuIsVisible();
        assertEquals("Close Menu",inventoryPage.getNameOfMenuCloseButton());
        inventoryPage.clickMenuCloseButton();
        inventoryPage.assertSideBarMenuIsClosed();
        inventoryPage.assertProductSortIsVisible();
        inventoryPage.assertKartIsVisible();
    }
    @Test
    @DisplayName("PROD-01: Page grid catalog should cleanly load 6 default products")
    void shouldDisplayAllSixProducts() {
        assertThat(inventoryPage.getInventoryItemsLocator()).hasCount(6);
    }

    @Test
    @DisplayName("PROD-03/04: Product grid sorting should organize names cleanly (A to Z vs Z to A)")
    void shouldSortProductsAlphabetically() {
        // 1. Verify A to Z sorting (Default)
        inventoryPage.selectSortOption("az");
        List<String> namesAz = inventoryPage.getAllItemNames();
        List<String> sortedNamesAz = namesAz.stream().sorted().toList();
        assertEquals(sortedNamesAz, namesAz, "Products are not sorted from A to Z.");

        // 2. Verify Z to A sorting
        inventoryPage.selectSortOption("za");
        List<String> namesZa = inventoryPage.getAllItemNames();
        List<String> sortedNamesZa = namesAz.stream().sorted(Collections.reverseOrder()).toList();
        assertEquals(sortedNamesZa, namesZa, "Products are not sorted from Z to A.");
    }

    @Test
    @DisplayName("PROD-05/06: Product sorting should correctly align price elements (Low-to-High vs High-to-Low)")
    void shouldSortProductsByPrice() {
        // 1. Sort Low to High
        inventoryPage.selectSortOption("lohi");
        List<Double> pricesLohi = inventoryPage.getAllItemPrices();
        List<Double> sortedPricesLohi = pricesLohi.stream().sorted().toList();
        assertEquals(sortedPricesLohi, pricesLohi, "Prices are not sorted from low to high.");

        // 2. Sort High to Low
        inventoryPage.selectSortOption("hilo");
        List<Double> pricesHilo = inventoryPage.getAllItemPrices();
        List<Double> sortedPricesHilo = pricesHilo.stream().sorted(Collections.reverseOrder()).toList();
        assertEquals(sortedPricesHilo, pricesHilo, "Prices are not sorted from high to low.");
    }

    @Test
    @DisplayName("CART-01/02: Cart badge integer count should cleanly increment on adding single/multiple items")
    void shouldIncrementCartBadgeOnAdd() {
        // Confirm badge is missing/empty initially
        assertThat(inventoryPage.getCartBadgeLocator()).isHidden();

        // Add 1st product and check badge
        inventoryPage.addProductToCart("Sauce Labs Backpack");
        assertThat(inventoryPage.getCartBadgeLocator()).hasText("1");

        // Add 2nd product and verify state updates cleanly
        inventoryPage.addProductToCart("Sauce Labs Bike Light");
        assertThat(inventoryPage.getCartBadgeLocator()).hasText("2");
    }

    @Test
    @DisplayName("CART-03: Removing an added product should cleanly decrement the active global badge tally")
    void shouldDecrementCartBadgeOnRemove() {
        inventoryPage.addProductToCart("Sauce Labs Backpack");
        inventoryPage.addProductToCart("Sauce Labs Bike Light");
        assertThat(inventoryPage.getCartBadgeLocator()).hasText("2");

        // Remove an item and verify the count goes down
        inventoryPage.removeProductFromCart("Sauce Labs Backpack");
        assertThat(inventoryPage.getCartBadgeLocator()).hasText("1");
    }
}
