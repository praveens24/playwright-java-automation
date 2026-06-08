package org.example.core;

import com.microsoft.playwright.*;
import org.example.config.TestConfig;

public class PlaywrightManager {
    private final Playwright playwright;
    private final Browser browser;


    public PlaywrightManager() {
        this.playwright = Playwright.create();
        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions()
                .setHeadless(TestConfig.headLessStatus());
        this.browser = switch (TestConfig.getBrowser().toLowerCase()) {
            case "chrome" -> playwright.chromium().launch(options);
            case "firefox" -> playwright.firefox().launch(options);
            case "webkit" -> playwright.webkit().launch(options);
            default -> playwright.chromium().launch(options);
        };

    }

    public BrowserContext browserContext() {

        BrowserContext context = browser.newContext(new Browser.NewContextOptions()
                .setViewportSize(1280, 720));
        context.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true));

        return context;
    }

    public Page createPage(BrowserContext context) {
        return context.newPage();
    }

    public void close() {
        browser.close();
        playwright.close();
    }

}
