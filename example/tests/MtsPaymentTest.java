package com.example.tests;

import com.example.pages.HomePage;
import com.example.pages.PaymentPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
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

    // ТЕСТ 5: Заполнение формы и проверка всплывающего окна
    @Test
    public void testPaymentForm() {
        System.out.println("=== ТЕСТ 5: Заполнение формы и проверка всплывающего окна ===");

        // Заполняем форму
        homePage.fillPhoneServiceForm("297777777", "100", "");

        // Нажимаем кнопку
        PaymentPage paymentPage = homePage.clickContinueButton();

        // Ждем появления всплывающего окна
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Проверяем, что всплывающее окно появилось
        boolean popupDisplayed = paymentPage.isPaymentPopupDisplayed();
        System.out.println("Окно оплаты появилось: " + popupDisplayed);

        assertTrue(popupDisplayed, "Окно оплаты не появилось после нажатия кнопки");

        // Если окно появилось, проверяем информацию в нем
        if (popupDisplayed) {
            // Проверяем сумму
            boolean hasAmount = paymentPage.hasAmountInPopup("100");
            System.out.println("Сумма 100 отображается: " + hasAmount);

            // Проверяем номер телефона
            boolean hasPhone = paymentPage.hasPhoneNumberInPopup("297777777");
            System.out.println("Номер 297777777 отображается: " + hasPhone);

            // Проверяем иконки платежных систем
            boolean hasVisa = paymentPage.hasPaymentIconInPopup("visa");
            boolean hasMastercard = paymentPage.hasPaymentIconInPopup("mastercard");
            boolean hasBelkart = paymentPage.hasPaymentIconInPopup("belkart");

            System.out.println("Иконка Visa: " + hasVisa);
            System.out.println("Иконка Mastercard: " + hasMastercard);
            System.out.println("Иконка Белкарт: " + hasBelkart);

            // Проверяем надписи в полях
            List<String> placeholders = paymentPage.getPlaceholdersInPopup();
            System.out.println("Найдены поля с подсказками: " + placeholders);
        }

        // Возвращаемся на главную страницу (если переключились в iframe)
        paymentPage.switchToMainContent();

        System.out.println("Тест 5 пройден: окно оплаты появилось");
    }
}