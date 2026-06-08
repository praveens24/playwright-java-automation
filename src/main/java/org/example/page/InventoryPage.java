package org.example.page;

import com.microsoft.playwright.Page;

public class InventoryPage extends BasePage{
    private final String pageHeader="[data-test='title']";
    private final String menuButton = "#react-burger-menu-btn";
    private final String allItemsMenu = "#inventory_sidebar_link";
    private final String aboutMenu = "#about_sidebar_link";
    private final String logoutMenu = "#logout_sidebar_link";
    private final String resetAppStateMenu = "#reset_sidebar_link";
    private final String menuCloseButton ="#react-burger-cross-btn";
    private final String sortDropdown =
            "[data-test='product-sort-container']";
    private final String shoppingCart =
            "[data-test='shopping-cart-link']";

    public InventoryPage(Page page) {
        super(page);
    }
    public String pageTitle(){
        return getText(pageHeader);
    }
    public String getNameOfMenuButton(){
        return getText(menuButton);
    }
    public boolean isSideBarMenuVisible(){
        return isVisible(allItemsMenu)
                && isVisible(aboutMenu)
                && isVisible(logoutMenu)
                && isVisible(resetAppStateMenu)
                && isVisible(menuCloseButton);
    }
    public String getNameOfMenuCloseButton(){
        return getText(menuCloseButton);
    }
    public void clickMenuButton(){
        click(menuButton);
    }
    public void clickMenuCloseButton(){
        click(menuCloseButton);
    }
    public boolean isSideBarMenuClosed(){
        return isTabindexNegative(menuCloseButton)
                && isTabindexNegative(allItemsMenu)
                && isTabindexNegative(aboutMenu)
                && isTabindexNegative(logoutMenu)
                && isTabindexNegative(resetAppStateMenu);
    }
    public boolean isProductSortVisible(){
        return isVisible(sortDropdown);
    }
    public boolean isKartVisible(){
        return isVisible(shoppingCart);
    }
}
