package com.example.tests;

import com.example.pages.HomePage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MtsPaymentTest {
    private WebDriver driver;
    private HomePage homePage;

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        options.addArguments("--start-maximized");

        driver = new ChromeDriver(options);
        driver.get("https://www.mts.by");

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        homePage = new HomePage(driver);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    // ТЕСТ 1: Проверка названия блока
    @Test
    public void testBlockTitle() {
        String title = homePage.getBlockTitle();
        assertNotNull(title, "Заголовок не найден");
        assertTrue(title.contains("Онлайн пополнение"), "Неверный заголовок");
        System.out.println("Тест 1 пройден: " + title);
    }

    // ТЕСТ 2: Проверка наличия логотипов
    @Test
    public void testPaymentLogos() {
        assertTrue(homePage.isPaymentLogoPresent("Visa"), "Логотип Visa не найден");
        assertTrue(homePage.isPaymentLogoPresent("MasterCard"), "Логотип MasterCard не найден");
        assertTrue(homePage.isPaymentLogoPresent("Белкарт"), "Логотип Белкарт не найден");
        System.out.println("Тест 2 пройден: все логотипы найдены");
    }

    // ТЕСТ 3: Проверка ссылки "Подробнее о сервисе"
    @Test
    public void testMoreDetailsLink() {
        String currentUrl = driver.getCurrentUrl();
        homePage.clickMoreDetailsLink();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String newUrl = driver.getCurrentUrl();
        assertNotEquals(currentUrl, newUrl, "URL не изменился");
        System.out.println("Тест 3 пройден: переход выполнен");
    }

    // ТЕСТ 4: Проверка надписей в полях
    @Test
    public void testEmptyFieldsPlaceholders() {
        assertEquals("Номер телефона", homePage.getFieldPlaceholder("connection-phone"));
        assertEquals("Сумма", homePage.getFieldPlaceholder("connection-sum"));
        assertEquals("E-mail для отправки чека", homePage.getFieldPlaceholder("connection-email"));
        System.out.println("Тест 4 пройден: все надписи корректны");
    }

    // ТЕСТ 5: Заполнение формы и проверка всплывающего окна оплаты
    @Test
    public void testPaymentForm() {
        System.out.println("\n=== ТЕСТ 5: Заполнение формы и проверка всплывающего окна оплаты ===");

        // 1. Заполняем форму на главной странице
        homePage.fillPhoneServiceForm("297777777", "100", "");

        // 2. Нажимаем кнопку "Продолжить"
        homePage.clickContinueButton();

        // 3. Ждем появления всплывающего окна
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 4. Находим видимый iframe
        List<WebElement> iframes = driver.findElements(By.tagName("iframe"));

        int visibleIframeIndex = -1;
        for (int i = 0; i < iframes.size(); i++) {
            if (iframes.get(i).isDisplayed()) {
                visibleIframeIndex = i;
                break;
            }
        }

        assertTrue(visibleIframeIndex >= 0, "Нет видимого iframe с формой оплаты!");

        // 5. Переключаемся в видимый iframe
        driver.switchTo().frame(visibleIframeIndex);

        // 6. Ждем загрузки
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 7. Получаем текст из iframe
        String entireText = driver.findElement(By.tagName("body")).getText();

        // 8. Проверяем сумму
        boolean hasAmount = entireText.contains("100.00 BYN");
        assertTrue(hasAmount, "Сумма не отображается в окне оплаты");

        // 9. Проверяем номер телефона
        boolean hasPhone = entireText.contains("375297777777");
        assertTrue(hasPhone, "Номер не отображается в окне оплаты");

        // 10. Проверяем иконки (опционально)
        List<WebElement> images = driver.findElements(By.tagName("img"));
        boolean hasVisa = false;
        boolean hasMastercard = false;

        for (WebElement img : images) {
            String src = img.getAttribute("src");
            if (src != null) {
                if (src.contains("visa")) hasVisa = true;
                if (src.contains("mastercard")) hasMastercard = true;
            }
        }

        // 11. Проверяем поля ввода
        List<WebElement> inputs = driver.findElements(By.tagName("input"));
        assertTrue(inputs.size() >= 4, "Не все поля для ввода карты найдены");

        // 12. Проверяем кнопку оплаты
        List<WebElement> buttons = driver.findElements(By.tagName("button"));
        boolean hasPayButton = false;
        for (WebElement button : buttons) {
            if (button.getText().contains("Оплатить 100.00 BYN")) {
                hasPayButton = true;
                break;
            }
        }
        assertTrue(hasPayButton, "Кнопка оплаты не найдена");

        // 13. Возвращаемся на главную страницу
        driver.switchTo().defaultContent();

        System.out.println("✅ ТЕСТ 5 ПРОЙДЕН!");
    }
}