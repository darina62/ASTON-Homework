package com.example;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MtsOnlinePaymentTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.mts.by");
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Ждем загрузку страницы
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        acceptCookiesIfPresent();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    // Принимаем куки
    private void acceptCookiesIfPresent() {
        try {
            // Кнопка "Принять"
            WebElement acceptCookies = driver.findElement(By.id("cookie-agree"));
            acceptCookies.click();
            System.out.println("Куки приняты");
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println("Нет баннера с куки");
        }
    }

    // ========== ТЕСТ 1: Проверка названия блока ==========
    @Test
    public void testBlockTitle() {
        System.out.println("\n--- ТЕСТ 1: Проверка названия блока ---");

        // Ищем все заголовки H2
        List<WebElement> headers = driver.findElements(By.tagName("h2"));

        // Ищем нужный заголовок
        WebElement titleElement = null;
        for (WebElement header : headers) {
            String text = header.getText();
            System.out.println("Заголовок: " + text);
            if (text.contains("Онлайн пополнение")) {
                titleElement = header;
                break;
            }
        }

        assertNotNull(titleElement, "Заголовок 'Онлайн пополнение' не найден");

        String titleText = titleElement.getText().trim();
        System.out.println("Найден заголовок: " + titleText);

        // Проверяем, что заголовок содержит нужный текст
        assertTrue(titleText.contains("Онлайн пополнение"),
                "Заголовок должен содержать 'Онлайн пополнение'");
    }

    // ========== ТЕСТ 2: Проверка наличия логотипов платежных систем ==========
    @Test
    public void testPaymentLogosPresent() {
        System.out.println("\n--- ТЕСТ 2: Проверка логотипов платежных систем ---");

        // Ищем все изображения
        List<WebElement> images = driver.findElements(By.tagName("img"));

        boolean hasVisa = false;
        boolean hasVerifiedVisa = false;
        boolean hasMastercard = false;
        boolean hasMastercardSecure = false;
        boolean hasBelcart = false;

        System.out.println("Поиск логотипов...");

        for (WebElement img : images) {
            String alt = img.getAttribute("alt");
            String src = img.getAttribute("src");

            if (alt != null && !alt.isEmpty()) {
                System.out.println("Найден логотип: alt=" + alt);

                if (alt.equals("Visa")) hasVisa = true;
                if (alt.equals("Verified By Visa")) hasVerifiedVisa = true;
                if (alt.equals("MasterCard")) hasMastercard = true;
                if (alt.equals("MasterCard Secure Code")) hasMastercardSecure = true;
                if (alt.equals("Белкарт")) hasBelcart = true;
            }
        }

        // Проверяем наличие всех логотипов
        assertTrue(hasVisa, "Логотип Visa не найден");
        assertTrue(hasVerifiedVisa, "Логотип Verified By Visa не найден");
        assertTrue(hasMastercard, "Логотип MasterCard не найден");
        assertTrue(hasMastercardSecure, "Логотип MasterCard Secure Code не найден");
        assertTrue(hasBelcart, "Логотип Белкарт не найден");

        System.out.println("Все логотипы найдены!");
    }

    // ========== ТЕСТ 3: Проверка работы ссылки "Подробнее о сервисе" ==========
    @Test
    public void testMoreDetailsLink() {
        System.out.println("\n--- ТЕСТ 3: Проверка ссылки 'Подробнее о сервисе' ---");

        // Ищем все ссылки
        List<WebElement> links = driver.findElements(By.tagName("a"));

        WebElement moreLink = null;
        for (WebElement link : links) {
            String text = link.getText();
            if (text.contains("Подробнее о сервисе")) {
                moreLink = link;
                System.out.println("Найдена ссылка: " + text);
                break;
            }
        }

        assertNotNull(moreLink, "Ссылка 'Подробнее о сервисе' не найдена");

        String currentUrl = driver.getCurrentUrl();
        System.out.println("Текущий URL: " + currentUrl);

        moreLink.click();
        System.out.println("Клик по ссылке выполнен");

        // Ждем загрузки новой страницы
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String newUrl = driver.getCurrentUrl();
        System.out.println("Новый URL: " + newUrl);

        assertNotEquals(currentUrl, newUrl, "URL не изменился после клика");
    }

    // ТЕСТ 4: Заполнение формы и проверка кнопки "Продолжить"
    @Test
    public void testFillFormAndContinue() {
        System.out.println("\n--- ТЕСТ 4: Заполнение формы и кнопка 'Продолжить' ---");

        // Находим поле для номера телефона
        WebElement phoneInput = driver.findElement(By.id("connection-phone"));
        phoneInput.clear();
        phoneInput.sendKeys("297777777");
        System.out.println("Введен номер: 297777777");

        // Получаем значение из поля
        String enteredPhone = phoneInput.getAttribute("value");
        System.out.println("Значение в поле: " + enteredPhone);

        // ВАРИАНТ 1: Проверяем, что номер содержит правильные цифры (без форматирования)
        String phoneDigits = enteredPhone.replaceAll("[^0-9]", ""); // убираем все не-цифры
        System.out.println("Только цифры: " + phoneDigits);
        assertEquals("297777777", phoneDigits, "Номер телефона введен неверно");

        // ВАРИАНТ 2: Или проверяем отформатированный вид
        // assertEquals("(29)777-77-77", enteredPhone, "Номер телефона отформатирован неверно");

        // Заполняем сумму
        WebElement amountInput = driver.findElement(By.id("connection-sum"));
        amountInput.clear();
        amountInput.sendKeys("100");

        // Нажимаем кнопку
        WebElement continueButton = driver.findElement(By.className("button__default"));
        continueButton.click();

        System.out.println("Тест 4 пройден");
    }

    // ========== ТЕСТ 5: Проверка наличия всех полей формы ==========
    @Test
    public void testFormFieldsArePresent() {
        System.out.println("\n--- ТЕСТ 5: Проверка наличия полей формы ---");

        // Проверяем поле телефона
        WebElement phoneInput = driver.findElement(By.id("connection-phone"));
        assertTrue(phoneInput.isDisplayed(), "Поле телефона не отображается");

        String phonePlaceholder = phoneInput.getAttribute("placeholder");
        assertEquals("Номер телефона", phonePlaceholder, "Неверный placeholder для поля телефона");
        System.out.println("Поле телефона: placeholder='" + phonePlaceholder + "'");

        // Проверяем поле суммы
        WebElement amountInput = driver.findElement(By.id("connection-sum"));
        assertTrue(amountInput.isDisplayed(), "Поле суммы не отображается");

        String amountPlaceholder = amountInput.getAttribute("placeholder");
        assertEquals("Сумма", amountPlaceholder, "Неверный placeholder для поля суммы");
        System.out.println("Поле суммы: placeholder='" + amountPlaceholder + "'");

        // Проверяем поле email (опционально)
        try {
            WebElement emailInput = driver.findElement(By.id("connection-email"));
            assertTrue(emailInput.isDisplayed(), "Поле email не отображается");

            String emailPlaceholder = emailInput.getAttribute("placeholder");
            assertEquals("E-mail для отправки чека", emailPlaceholder,
                    "Неверный placeholder для поля email");
            System.out.println("Поле email: placeholder='" + emailPlaceholder + "'");
        } catch (NoSuchElementException e) {
            System.out.println("Поле email отсутствует (это нормально)");
        }

        System.out.println("Все поля формы найдены и отображаются!");
    }
}
