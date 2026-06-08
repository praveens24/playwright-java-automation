package org.example.test;

import org.example.config.TestConfig;
import org.example.page.LoginPage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoginTest extends BaseTest{
    @Test
    void shouldSuccessfullyLogin(){
        LoginPage loginPage = new LoginPage(page);
        loginPage.open();
        assertEquals("Swag Labs",loginPage.pageTitle() );
        assertTrue(loginPage.isPageLoaded());
        assertEquals("Swag Labs", loginPage.getLabelText());
        loginPage.enterUserName(TestConfig.getUser());
        loginPage.enterPassword(TestConfig.getPassword());
        loginPage.clickLogin();

    }
    @Test
    void shouldShowErrorLoginBlankUserBlankPassword(){
        LoginPage loginPage = new LoginPage(page);
        loginPage.open();
        assertTrue(loginPage.isPageLoaded());
        loginPage.clickLogin();
        assertTrue(loginPage.errorMessageDisplayed());
        assertEquals("Epic sadface: Username is required",
                loginPage.getErrorMessage());
        assertTrue(loginPage.userNameErrorIconVisible());
        assertEquals("times-circle", loginPage.getUserNameErrorDataIcon());
        assertTrue(loginPage.passwordErrorIconVisible());
        assertEquals("times-circle", loginPage.getPasswordErrorDataIcon());
        loginPage.closeError();
        loginPage.enterUserName(TestConfig.getUser());
        loginPage.clickLogin();
        assertEquals("Epic sadface: Password is required",
                loginPage.getErrorMessage());
        loginPage.closeError();




    }
}
