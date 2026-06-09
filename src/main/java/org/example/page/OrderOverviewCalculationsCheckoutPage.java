package org.example.page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class OrderOverviewCalculationsCheckoutPage extends BasePage{
    private final String pageHeader = "[data-test='title']";
    private final String subtotalLabel = "[data-test='subtotal-label']";
    private final String taxLabel = "[data-test='tax-label']";
    private final String totalLabel = "[data-test='total-label']";
    private final String finishButton = "[data-test='finish']";

    public OrderOverviewCalculationsCheckoutPage(Page page) {
        super(page);
    }

    public CheckoutPage clickFinish() {
        click(finishButton);
        return new CheckoutPage(page);
    }

    public Locator getPageHeaderLocator() { return page.locator(pageHeader); }
    public Locator getSubtotalLocator() { return page.locator(subtotalLabel); }
    public Locator getTaxLocator() { return page.locator(taxLabel); }
    public Locator getTotalLocator() { return page.locator(totalLabel); }

}
