package com.example.pages;

import org.openqa.selenium.*;
import java.util.List;

public class PaymentPage extends BasePage {

    public PaymentPage(WebDriver driver) {
        super(driver);
    }

    // Проверить, появилось ли всплывающее окно с оплатой
    public boolean isPaymentPopupDisplayed() {
        System.out.println("Проверка появления окна оплаты...");

        // 1. Проверяем наличие iframe (обычно форма оплаты в iframe)
        List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
        System.out.println("Найдено iframe: " + iframes.size());

        if (iframes.size() > 0) {
            // Пробуем переключиться в первый iframe
            try {
                driver.switchTo().frame(0);
                System.out.println("Переключились в iframe");
                return true;
            } catch (Exception e) {
                System.out.println("Не удалось переключиться в iframe");
            }
        }

        // 2. Проверяем наличие всплывающих окон (попапов)
        List<WebElement> popups = driver.findElements(By.className("popup"));
        popups.addAll(driver.findElements(By.className("modal")));
        popups.addAll(driver.findElements(By.className("payment")));

        System.out.println("Найдено всплывающих окон: " + popups.size());

        for (WebElement popup : popups) {
            if (popup.isDisplayed()) {
                System.out.println("Найдено видимое всплывающее окно");
                return true;
            }
        }

        // 3. Проверяем, есть ли на странице новые элементы, которых не было раньше
        // Например, форма ввода карты
        List<WebElement> inputs = driver.findElements(By.tagName("input"));
        System.out.println("Всего полей ввода: " + inputs.size());

        // Если полей ввода стало много - вероятно, появилась форма оплаты
        if (inputs.size() > 5) {
            System.out.println("Много полей ввода - вероятно, форма оплаты");
            return true;
        }

        return false;
    }

    // Вернуться из iframe обратно на главную страницу
    public void switchToMainContent() {
        driver.switchTo().defaultContent();
        System.out.println("Вернулись на главную страницу");
    }

    // Проверить наличие суммы в окне оплаты
    public boolean hasAmountInPopup(String expectedAmount) {
        String pageSource = driver.getPageSource();
        return pageSource.contains(expectedAmount);
    }

    // Проверить наличие номера телефона в окне оплаты
    public boolean hasPhoneNumberInPopup(String expectedPhone) {
        String pageSource = driver.getPageSource();
        return pageSource.contains(expectedPhone);
    }

    // Проверить наличие иконок платежных систем в окне оплаты
    public boolean hasPaymentIconInPopup(String iconName) {
        List<WebElement> images = driver.findElements(By.tagName("img"));
        for (WebElement img : images) {
            String alt = img.getAttribute("alt");
            String src = img.getAttribute("src");
            if (alt != null && alt.toLowerCase().contains(iconName.toLowerCase())) {
                return true;
            }
            if (src != null && src.toLowerCase().contains(iconName.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    // Получить все placeholder'ы полей в окне оплаты
    public List<String> getPlaceholdersInPopup() {
        List<WebElement> inputs = driver.findElements(By.tagName("input"));
        List<String> placeholders = new java.util.ArrayList<>();

        for (WebElement input : inputs) {
            String placeholder = input.getAttribute("placeholder");
            if (placeholder != null && !placeholder.isEmpty()) {
                placeholders.add(placeholder);
                System.out.println("Найден placeholder: " + placeholder);
            }
        }
        return placeholders;
    }
}