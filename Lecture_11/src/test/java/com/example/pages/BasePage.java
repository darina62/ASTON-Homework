package com.example.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BasePage {
    protected WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    protected List<WebElement> findElements(By locator) {
        return driver.findElements(locator);
    }

    protected void acceptCookiesIfPresent() {
        try {
            List<WebElement> acceptCookies = driver.findElements(By.id("cookie-agree"));
            if (!acceptCookies.isEmpty() && acceptCookies.get(0).isDisplayed()) {
                acceptCookies.get(0).click();
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            // Игнорируем
        }
    }
}