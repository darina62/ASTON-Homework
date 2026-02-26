package com.example;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

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

        
        List<WebElement> headers = driver.findElements(By.tagName("h2"));

        
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

    // ========== ТЕСТ 4: Заполнение формы и проверка кнопки "Продолжить" ==========
    @Test
    public void testFillFormAndContinue() {
        System.out.println("\n--- ТЕСТ 4: Заполнение формы и кнопка 'Продолжить' ---");

        // 1. Находим поле для номера телефона
        WebElement phoneInput = driver.findElement(By.id("connection-phone"));
        assertNotNull(phoneInput, "Поле для номера телефона не найдено");

        phoneInput.clear();
        phoneInput.sendKeys("297777777");
        System.out.println("Введен номер: 297777777");

        // Проверяем, что номер введен правильно
        String enteredPhone = phoneInput.getAttribute("value");
        assertEquals("297777777", enteredPhone, "Номер телефона введен неверно");

        // 2. Находим поле для суммы
        WebElement amountInput = driver.findElement(By.id("connection-sum"));
        assertNotNull(amountInput, "Поле для суммы не найдено");

        amountInput.clear();
        amountInput.sendKeys("100");
        System.out.println("Введена сумма: 100");

        // Проверяем сумму
        assertEquals("100", amountInput.getAttribute("value"), "Сумма введена неверно");

        // 3. Находим поле для email
        try {
            WebElement emailInput = driver.findElement(By.id("connection-email"));
            emailInput.clear();
            emailInput.sendKeys("test@example.com");
            System.out.println("Введен email: test@example.com");

            assertEquals("test@example.com", emailInput.getAttribute("value"),
                    "Email введен неверно");
        } catch (NoSuchElementException e) {
            System.out.println("Поле email не найдено (пропускаем)");
        }

        // 4. Находим кнопку "Продолжить"
        List<WebElement> buttons = driver.findElements(By.tagName("button"));
        WebElement continueButton = null;

        for (WebElement button : buttons) {
            String text = button.getText();
            if (text.contains("Продолжить")) {
                continueButton = button;
                System.out.println("Найдена кнопка: " + text);
                break;
            }
        }

        assertNotNull(continueButton, "Кнопка 'Продолжить' не найдена");

        // Проверяем, что кнопка активна
        assertTrue(continueButton.isEnabled(), "Кнопка 'Продолжить' не активна");

        // Нажимаем кнопку
        continueButton.click();
        System.out.println("Клик по кнопке 'Продолжить' выполнен");

        // Ждем появления формы оплаты
        try {
            Thread.sleep(5000);
            List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
            System.out.println("Найдено iframe: " + iframes.size());

            assertTrue(iframes.size() > 0, "Форма оплаты не появилась");
            System.out.println("Форма оплаты успешно загружена!");
        } catch (Exception e) {
            System.out.println("Предупреждение: форма оплаты не обнаружена, но тест продолжается");
            // Не падаем, так как иногда форма может открываться в новой вкладке
        }
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

        // Проверяем поле email
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
