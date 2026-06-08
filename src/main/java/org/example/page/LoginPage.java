package org.example.page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.example.config.TestConfig;

public class LoginPage extends BasePage {

    private final String loginLogo = ".login_logo";
    private final String userName = "#user-name";
    private final String password = "#password";
    private final String loginButton = "#login-button";
    private final String dataError = "[data-test='error']";
    private final String errorCloseButton = "[data-test='error-button']";
    private final String userNameErrorIcon = "#user-name + svg";
    private final String passwordErrorIcon = "#password + svg";

    public LoginPage(Page page) {
        super(page);
    }

    public void open() {
        page.navigate(TestConfig.baseUrl());

    }

    public boolean isPageLoaded() {
        waitForPageLoad();
        return isVisible(loginLogo)
                && isVisible(loginButton)
                && isVisible(userName)
                && isVisible(password);
    }

    public String getLabelText() {
        return getText(loginLogo);
    }

    public String pageTitle() {
        return getTitle();
    }

    public void enterUserName(String name) {
        fill(userName, name);
    }

    public void enterPassword(String passphrase) {
        fill(password, passphrase);
    }

    public void clickLogin() {
        click(loginButton);
    }

    public boolean errorMessageDisplayed() {
        return isVisible(dataError);
    }

    public Locator getLabelLocator() {
        return page.locator(loginLogo);
    }

    public Locator getErrorBannerLocator() {
        return page.locator(dataError);
    }

    public Locator getUserNameErrorIconLocator() {
        return page.locator(userNameErrorIcon);
    }

    public Locator getPasswordErrorIconLocator() {
        return page.locator(passwordErrorIcon);
    }

    public void closeError() {
        click(errorCloseButton);
    }


    public boolean userNameErrorIconVisible() {
        return isVisible(userNameErrorIcon);
    }

    public String getUserNameErrorDataIcon() {
        return getAttribute(userNameErrorIcon, "data-icon");
    }

    public boolean passwordErrorIconVisible() {
        return isVisible(passwordErrorIcon);
    }

    public String getPasswordErrorDataIcon() {
        return getAttribute(passwordErrorIcon, "data-icon");
    }

    public InventoryPage login(String username, String password) {
        open();
        enterUserName(username);
        enterPassword(password);
        clickLogin();
        page.waitForURL("**/inventory.html");
        return new InventoryPage(page);
    }
}