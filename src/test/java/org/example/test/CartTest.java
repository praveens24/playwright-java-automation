package org.example.test;

import org.example.config.TestConfig;
import org.example.page.InventoryPage;
import org.example.page.LoginPage;
import org.example.page.CartPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class CartTest extends BaseTest {
    private InventoryPage inventoryPage;

    @BeforeEach
    void authenticatedInventorySetup() {
        this.inventoryPage = new LoginPage(page)
                .login(TestConfig.getUser(),TestConfig.getPassword());
        assertThat(inventoryPage.getPageHeaderLocator()).hasText("Products");
    }

    @Test
    @DisplayName("CART-04/05: Adding items and routing to Cart should maintain product integrity and support items removal")
    void shouldVerifyCartItemPersistencesAndRemovalFlows() {
        String itemOne = "Sauce Labs Backpack";
        String itemTwo = "Sauce Labs Bike Light";


        inventoryPage.addProductToCart(itemOne);
        inventoryPage.addProductToCart(itemTwo);
        assertThat(inventoryPage.getCartBadgeLocator()).hasText("2");


        page.click("[data-test='shopping-cart-link']");
        CartPage cartPage = new CartPage(page);


        assertThat(cartPage.getPageHeaderLocator()).hasText("Your Cart");
        assertThat(cartPage.getCartItemsLocator()).hasCount(2);
        cartPage.assertItemIsPresentInCart(itemOne);
        cartPage.assertItemIsPresentInCart(itemTwo);


        cartPage.removeProductFromCart(itemOne);
        assertThat(cartPage.getCartItemsLocator()).hasCount(1);
        cartPage.assertItemIsHiddenInCart(itemOne);


        cartPage.assertItemIsPresentInCart(itemTwo);
    }
}
