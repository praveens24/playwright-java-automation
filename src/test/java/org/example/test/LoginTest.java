package org.example.test;

import org.example.config.TestConfig;
import org.example.page.LoginPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest extends BaseTest{
    private LoginPage loginPage;
    @BeforeEach
    void init() {
        loginPage = new LoginPage(page);
        loginPage.open();
    }
    @Test
    @DisplayName("AUTH-01: Valid user should log in successfully and land on the inventory page")
    void shouldSuccessfullyLogin(){
        assertEquals("Swag Labs",loginPage.pageTitle() );
        assertTrue(loginPage.isPageLoaded());
        assertEquals("Swag Labs", loginPage.getLabelText());
        loginPage.enterUserName(TestConfig.getUser());
        loginPage.enterPassword(TestConfig.getPassword());
        loginPage.clickLogin();
        assertThat(page.locator("[data-test='title']")).hasText("Products");

    }

    @Test
    @DisplayName("AUTH-02: Locked out user should be denied access and see specific warning banner")
    void shouldShowErrorForLockedOutUser() {
        loginPage.enterUserName("locked_out_user");
        loginPage.enterPassword(TestConfig.getPassword());
        loginPage.clickLogin();
        assertThat(loginPage.getErrorBannerLocator()).hasText("Epic sadface: Sorry, this user has been locked out.");
    }

    @Test
    @DisplayName("AUTH-06: Providing incorrect credentials should trigger matching system validation messages")
    void shouldShowErrorForInvalidCredentials() {
        loginPage.enterUserName("standard_user");
        loginPage.enterPassword("wrong_sauce");
        loginPage.clickLogin();

        assertThat(loginPage.getErrorBannerLocator()).containsText("Username and password do not match any user in this service");
    }

    @ParameterizedTest(name = "AUTH-Boundary: SQL injection or special characters test for user input: {0}")
    @CsvSource({
            "'or 1=1--', 'secret_sauce'",
            "standard_user, ' ';drop table users;--'",
            "invalid_💥_emoji, secret_sauce"
    })
    void shouldFailGracefullyWithSanitizedInputProfiles(String heavyUser, String heavyPass) {
        loginPage.enterUserName(heavyUser);
        loginPage.enterPassword(heavyPass);
        loginPage.clickLogin();

        assertThat(loginPage.getErrorBannerLocator()).containsText("Username and password do not match any user in this service");
    }

    @Test
    @DisplayName("AUTH-07/08: Should show sequential errors and error icons for missing credentials")
    void shouldShowErrorLoginBlankUserBlankPassword() {

        loginPage.clickLogin();
        assertThat(loginPage.getErrorBannerLocator()).hasText("Epic sadface: Username is required");


        assertThat(loginPage.getErrorBannerLocator()).hasText("Epic sadface: Username is required");

        assertThat(loginPage.getUserNameErrorIconLocator()).hasAttribute("data-icon", "times-circle");
        assertThat(loginPage.getPasswordErrorIconLocator()).isVisible();
        assertThat(loginPage.getPasswordErrorIconLocator()).hasAttribute("data-icon", "times-circle");


        loginPage.closeError();
        assertThat(loginPage.getErrorBannerLocator()).isHidden();

        loginPage.enterUserName(TestConfig.getUser());
        loginPage.clickLogin();
        assertThat(loginPage.getErrorBannerLocator()).hasText("Epic sadface: Password is required");

        loginPage.closeError();
        assertThat(loginPage.getErrorBannerLocator()).isHidden();
    }
}



