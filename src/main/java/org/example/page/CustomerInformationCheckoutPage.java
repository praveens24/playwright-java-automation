package org.example.page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class CustomerInformationCheckoutPage extends BasePage {
    private final String firstNameField = "[data-test='firstName']";
    private final String lastNameField = "[data-test='lastName']";
    private final String postalCodeField = "[data-test='postalCode']";
    private final String continueButton = "[data-test='continue']";
    private final String errorContainer = "[data-test='error']";

    public CustomerInformationCheckoutPage(Page page) {
        super(page);
    }

    public void enterCustomerInformation(String firstName, String lastName, String postalCode) {
        fill(firstNameField, firstName);
        fill(lastNameField, lastName);
        fill(postalCodeField, postalCode);
    }

    public OrderOverviewCalculationsCheckoutPage clickContinue() {
        click(continueButton);
        return new OrderOverviewCalculationsCheckoutPage(page);
    }

    public Locator getErrorBannerLocator() {
        return page.locator(errorContainer);
    }
}
