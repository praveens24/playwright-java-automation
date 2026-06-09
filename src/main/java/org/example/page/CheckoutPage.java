package org.example.page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;


public class CheckoutPage extends BasePage {private final String completeHeader = "[data-test='complete-header']";
    private final String backToProductsButton = "[data-test='back-to-products']";

    public CheckoutPage(Page page) {
        super(page);
    }

    public void clickBackToProducts() {
        click(backToProductsButton);
    }

    public Locator getCompleteHeaderLocator() {
        return page.locator(completeHeader);
    }
}

