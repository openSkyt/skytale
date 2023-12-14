package org.openskyt.skytale.controller;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginAndRegisterControllerTest {
    @Test
    void registerAndLogIn() {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch();
            Page page = browser.newPage();
            //STEP 1 -- Register page
            page.navigate("http://127.0.0.1:8080/register");
            page.locator("input[name=username]").fill("=tester=tester");
            page.locator("input[name=password]").fill("password");
            page.locator("input[type=submit]").click();
            // STEP 2 -- Login page
            assertEquals("http://127.0.0.1:8080/login", page.url());
            page.locator("input[name=username]").fill("=tester=tester");
            page.locator("input[name=password]").fill("password");
            page.locator("input[type=submit]").click();
            assertEquals("http://127.0.0.1:8080/", page.url());
            // use this for print screen page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("login.png")));
            //always close browser!
            browser.close();
        }
    }

    @Test
    void loginWithHardAdmin() {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch();
            Page page = browser.newPage();
            page.navigate("http://127.0.0.1:8080/login");
            page.locator("input[name=username]").fill("admin");
            page.locator("input[name=password]").fill("admin");
            page.locator("input[type=submit]").click();
            assertEquals("http://127.0.0.1:8080/", page.url());
            browser.close();
        }
    }

    @Test
    void loginWithWrongPassword() {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch();
            Page page = browser.newPage();
            page.navigate("http://127.0.0.1:8080/login");
            page.locator("input[name=username]").fill("admin");
            page.locator("input[name=password]").fill("wrongpassword");
            page.locator("input[type=submit]").click();
            assertEquals("http://127.0.0.1:8080/login?error", page.url());
            browser.close();
        }
    }
}