package com.example.pages;

import org.openqa.selenium.*;
import java.util.List;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
        acceptCookiesIfPresent();
    }

    // Получить заголовок блока
    public String getBlockTitle() {
        List<WebElement> headers = driver.findElements(By.tagName("h2"));
        for (WebElement header : headers) {
            String text = header.getText();
            if (text.contains("Онлайн пополнение")) {
                return text;
            }
        }
        return null;
    }

    // Проверить наличие логотипа
    public boolean isPaymentLogoPresent(String logoName) {
        List<WebElement> images = driver.findElements(By.tagName("img"));
        for (WebElement img : images) {
            String alt = img.getAttribute("alt");
            if (alt != null && alt.equals(logoName)) {
                return true;
            }
        }
        return false;
    }

    // Клик по ссылке "Подробнее о сервисе"
    public void clickMoreDetailsLink() {
        List<WebElement> links = driver.findElements(By.tagName("a"));
        for (WebElement link : links) {
            if (link.getText().contains("Подробнее о сервисе")) {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].click();", link);
                return;
            }
        }
    }

    // Получить placeholder поля
    public String getFieldPlaceholder(String fieldId) {
        try {
            WebElement field = driver.findElement(By.id(fieldId));
            return field.getAttribute("placeholder");
        } catch (Exception e) {
            return null;
        }
    }

    // Заполнить форму для услуги связи
    public void fillPhoneServiceForm(String phone, String amount, String email) {
        try {
            WebElement phoneField = driver.findElement(By.id("connection-phone"));
            phoneField.clear();
            phoneField.sendKeys(phone);

            WebElement amountField = driver.findElement(By.id("connection-sum"));
            amountField.clear();
            amountField.sendKeys(amount);

            if (email != null && !email.isEmpty()) {
                WebElement emailField = driver.findElement(By.id("connection-email"));
                emailField.clear();
                emailField.sendKeys(email);
            }
        } catch (Exception e) {
            System.out.println("Ошибка заполнения формы: " + e.getMessage());
        }
    }

    // Нажать кнопку "Продолжить"
    public PaymentPage clickContinueButton() {
        List<WebElement> buttons = driver.findElements(By.tagName("button"));
        for (WebElement button : buttons) {
            if (button.getText().contains("Продолжить")) {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].click();", button);
                break;
            }
        }

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return new PaymentPage(driver);
    }
}