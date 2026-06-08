package org.example.test;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import org.example.core.PlaywrightManager;
import org.example.core.ScreenshotOnFailureExtension;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.RegisterExtension;

public class BaseTest {
    protected static PlaywrightManager playwrightManager;
    protected BrowserContext context;
    protected Page page;
    @RegisterExtension
    static final ScreenshotOnFailureExtension screenshotOnFailureExtension = new ScreenshotOnFailureExtension();

    @BeforeAll
    static void globalSetup()
    {
        playwrightManager=new PlaywrightManager();
    }
    @BeforeEach()
    void setup(){
        context= playwrightManager.browserContext();
        page=playwrightManager.createPage(context);
    }
    @AfterEach
    void tearDown() {
        if (context != null) {
            context.close();
        }
    }

    @AfterAll
    static void globalTearDown() {
        if (playwrightManager != null) {
            playwrightManager.close();
        }
    }
    public Page getPage() {
        return page;
    }
}
