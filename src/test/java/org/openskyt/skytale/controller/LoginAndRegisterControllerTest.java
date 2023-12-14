package org.openskyt.skytale.controller;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class LoginAndRegisterControllerTest {

    @Test
    void login() {
    }

    @Test
    void register() {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch();
            Page page = browser.newPage();
            page.navigate("http://127.0.0.1:8080/register");


            page.locator("input[name=username]").fill("=tester===cokoliv");
            page.locator("input[name=password]").fill("password");

            page.locator("input[type=submit]").click();

            // STEP 2 -- Login page
            assertEquals("http://127.0.0.1:8080/login", page.url());

            page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("login.png")));
            browser.close();
        }
    }

    @Test
    void registerPost() {
    }
}