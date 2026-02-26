package com.example.pages;

import org.openqa.selenium.*;
import java.util.List;

public class PaymentPage extends BasePage {

    public PaymentPage(WebDriver driver) {
        super(driver);
    }

    // Проверить, что окно оплаты появилось
    public boolean isPaymentPopupDisplayed() {
        System.out.println("Проверка появления окна оплаты...");

        // Проверяем наличие iframe
        List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
        System.out.println("Найдено iframe: " + iframes.size());

        if (iframes.size() > 0) {
            try {
                // Переключаемся в первый iframe
                driver.switchTo().frame(0);
                System.out.println("Переключились в iframe");

                // Ждем загрузки контента
                Thread.sleep(2000);

                // Проверяем наличие полей ввода
                List<WebElement> inputs = driver.findElements(By.tagName("input"));
                if (inputs.size() > 0) {
                    System.out.println("✓ В iframe найдены поля ввода");
                    return true;
                }

                // Проверяем наличие кнопок
                List<WebElement> buttons = driver.findElements(By.tagName("button"));
                if (buttons.size() > 0) {
                    System.out.println("✓ В iframe найдены кнопки");
                    return true;
                }

            } catch (Exception e) {
                System.out.println("Ошибка при переключении в iframe: " + e.getMessage());
            }
        }

        System.out.println("✗ Окно оплаты НЕ появилось");
        return false;
    }

    // Проверить наличие суммы в окне оплаты
    public boolean hasAmountInPopup(String expectedAmount) {
        System.out.println("Проверка суммы: " + expectedAmount);
        String pageSource = driver.getPageSource();
        boolean found = pageSource.contains(expectedAmount) ||
                pageSource.contains(expectedAmount + ".00");
        System.out.println("Сумма найдена: " + found);
        return found;
    }

    // Проверить наличие номера телефона в окне оплаты
    public boolean hasPhoneNumberInPopup(String expectedPhone) {
        System.out.println("Проверка номера: " + expectedPhone);
        String pageSource = driver.getPageSource();
        boolean found = pageSource.contains("375" + expectedPhone) ||
                pageSource.contains(expectedPhone);
        System.out.println("Номер найден: " + found);
        return found;
    }

    // Проверить наличие иконки платежной системы
    public boolean hasPaymentIconInPopup(String iconName) {
        System.out.println("Проверка иконки: " + iconName);
        List<WebElement> images = driver.findElements(By.tagName("img"));
        for (WebElement img : images) {
            String alt = img.getAttribute("alt");
            String src = img.getAttribute("src");
            if (alt != null && alt.toLowerCase().contains(iconName.toLowerCase())) {
                System.out.println("Найдена иконка в alt: " + alt);
                return true;
            }
            if (src != null && src.toLowerCase().contains(iconName.toLowerCase())) {
                System.out.println("Найдена иконка в src: " + src);
                return true;
            }
        }
        System.out.println("Иконка " + iconName + " не найдена");
        return false;
    }

    // Получить все placeholder'ы полей
    public List<String> getPlaceholdersInPopup() {
        System.out.println("Сбор placeholder'ов...");
        List<WebElement> inputs = driver.findElements(By.tagName("input"));
        List<String> placeholders = new java.util.ArrayList<>();

        for (WebElement input : inputs) {
            String placeholder = input.getAttribute("placeholder");
            if (placeholder != null && !placeholder.isEmpty()) {
                placeholders.add(placeholder);
                System.out.println("Найден placeholder: " + placeholder);
            }
        }

        System.out.println("Всего placeholder'ов: " + placeholders.size());
        return placeholders;
    }

    // Вернуться на главную страницу
    public void switchToMainContent() {
        driver.switchTo().defaultContent();
        System.out.println("Вернулись на главную страницу");
    }
}
