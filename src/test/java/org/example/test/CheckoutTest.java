package org.example.test;

import org.example.config.TestConfig;
import org.example.page.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class CheckoutTest extends BaseTest {
    private InventoryPage inventoryPage;

    @BeforeEach
    void authenticatedSetup() {
        LoginPage loginPage = new LoginPage(page);
        loginPage.open();
        this.inventoryPage = loginPage.login(TestConfig.getUser(), TestConfig.getPassword());
    }

    @Test
    @DisplayName("CHKT-01/06: End-to-End purchase flow validating calculations and complete confirmation screen")
    void shouldCompletePurchaseFlowSuccessfully() {

        inventoryPage.addProductToCart("Sauce Labs Backpack");
        page.click("[data-test='shopping-cart-link']");

        CartPage cartPage = new CartPage(page);
        cartPage.clickCheckout();


        CustomerInformationCheckoutPage stepOne = new CustomerInformationCheckoutPage(page);
        stepOne.enterCustomerInformation("", "Doe", "12345"); // Missing first name
        stepOne.clickContinue();
        assertThat(stepOne.getErrorBannerLocator()).hasText("Error: First Name is required");


        stepOne.enterCustomerInformation("John", "Doe", "12345");
        OrderOverviewCalculationsCheckoutPage stepTwo = stepOne.clickContinue();


        assertThat(stepTwo.getPageHeaderLocator()).hasText("Checkout: Overview");
        assertThat(stepTwo.getSubtotalLocator()).containsText("$29.99");
        assertThat(stepTwo.getTaxLocator()).containsText("$2.40");
        assertThat(stepTwo.getTotalLocator()).containsText("$32.39");


        CheckoutPage completePage = stepTwo.clickFinish();
        assertThat(completePage.getCompleteHeaderLocator()).hasText("Thank you for your order!");
    }
}
