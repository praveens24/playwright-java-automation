package org.example.core;

import com.microsoft.playwright.Page;
import org.example.test.BaseTest; // Ensure this import points to your test package
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ScreenshotOnFailureExtension implements AfterTestExecutionCallback {

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        if (context.getExecutionException().isEmpty()){
            return;
        }

        Object instance = context.getRequiredTestInstance();
        // FIXED: Changed TestBase to BaseTest
        if (!(instance instanceof BaseTest baseTest) || baseTest.getPage() == null) {
            return;
        }

        Path screenshotsDir = Paths.get("target", "screenshots");
        Files.createDirectories(screenshotsDir);

        String safeName = context.getDisplayName().replaceAll("[^a-zA-Z0-9._-]", "_");
        Path screenshotPath = screenshotsDir.resolve(safeName + ".png");

        // FIXED: Using getter or direct clean reference to capture the screenshot
        baseTest.getPage().screenshot(new Page.ScreenshotOptions().setPath(screenshotPath));
    }
}