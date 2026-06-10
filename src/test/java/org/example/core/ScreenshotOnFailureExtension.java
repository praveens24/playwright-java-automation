package org.example.core;

import com.microsoft.playwright.Page;
import org.example.test.BaseTest;
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

        if (!(instance instanceof BaseTest baseTest) || baseTest.getPage() == null) {
            return;
        }

        Path screenshotsDir = Paths.get("target", "screenshots");
        Files.createDirectories(screenshotsDir);

        String safeName = context.getDisplayName().replaceAll("[^a-zA-Z0-9._-]", "_");
        Path screenshotPath = screenshotsDir.resolve(safeName + ".png");


        baseTest.getPage().screenshot(new Page.ScreenshotOptions().setPath(screenshotPath));
    }
}