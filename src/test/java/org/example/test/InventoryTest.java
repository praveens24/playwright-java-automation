package org.example.test;

import org.example.config.TestConfig;
import org.example.page.InventoryPage;
import org.example.page.LoginPage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class InventoryTest extends BaseTest{



    @Test
    void shouldHaveBuggerMenuInLeftSideOfThePage(){
        InventoryPage inventoryPage = new LoginPage(page).
                login(TestConfig.getUser(),TestConfig.getPassword());
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
}
