package com.example.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import java.util.List;

public class PaymentPage extends BasePage {

    public PaymentPage(WebDriver driver) {
        super(driver);
    }

    @Step("Проверка появления окна оплаты")
    public boolean isPaymentPopupDisplayed() {
        List<WebElement> iframes = driver.findElements(By.tagName("iframe"));

        for (int i = 0; i < iframes.size(); i++) {
            if (iframes.get(i).isDisplayed()) {
                driver.switchTo().frame(i);
                return true;
            }
        }
        return false;
    }

    @Step("Проверка наличия суммы {expectedAmount}")
    public boolean hasAmount(String expectedAmount) {
        String pageSource = driver.getPageSource();
        return pageSource.contains(expectedAmount);
    }

    @Step("Проверка наличия номера {expectedPhone}")
    public boolean hasPhoneNumber(String expectedPhone) {
        String pageSource = driver.getPageSource();
        return pageSource.contains(expectedPhone) ||
                pageSource.contains("375" + expectedPhone);
    }

    @Step("Проверка наличия иконки {iconName}")
    public boolean hasPaymentIcon(String iconName) {
        List<WebElement> images = driver.findElements(By.tagName("img"));
        for (WebElement img : images) {
            String src = img.getAttribute("src");
            if (src != null && src.toLowerCase().contains(iconName.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    @Step("Возврат на главную страницу")
    public void switchToMainContent() {
        driver.switchTo().defaultContent();
    }
}