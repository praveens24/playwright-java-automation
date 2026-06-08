package org.example.page;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;

public abstract class BasePage {

    protected final Page page;

    protected BasePage(Page page) {
        this.page = page;
    }

    protected void click(String locator) {
        page.locator(locator).click();
    }

    protected void fill(String locator, String value) {
        page.locator(locator).fill(value);
    }

    protected String getText(String locator) {
        return page.locator(locator).textContent();
    }

    protected boolean isVisible(String locator) {
        return page.locator(locator).isVisible();
    }
    protected boolean isTabindexNegative(String locator){
        String tabindex = page.locator(locator).getAttribute("tabindex");
        return "-1".equals(tabindex);
    }
    protected boolean isEditable(String locator) {
        return page.locator(locator).isEditable();
    }

    protected String getAttribute(String locator, String attribute) {
        return page.locator(locator).getAttribute(attribute);
    }

    protected void waitForPageLoad() {
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
    }

    protected String getTitle() {
        return page.title();
    }

}