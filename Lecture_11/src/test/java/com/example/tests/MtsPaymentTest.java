package com.example.tests;

import com.example.pages.HomePage;
import com.example.utils.AllureListener;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Epic("MTS Payment Tests")
@Feature("Online Payment")
@DisplayName("Тесты онлайн-пополнения МТС")
//@ExtendWith(AllureListener.class)
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

    @Test
    @Story("Проверка заголовка")
    @DisplayName("Проверка названия блока 'Онлайн пополнение'")
    @Severity(SeverityLevel.NORMAL)
    @Description("Тест проверяет заголовок блока")
    public void testBlockTitle() {
        String title = homePage.getBlockTitle();
        assertNotNull(title, "Заголовок не найден");
        assertTrue(title.contains("Онлайн пополнение"), "Неверный заголовок");
        Allure.addAttachment("Заголовок", title);
    }

    @Test
    @Story("Проверка логотипов")
    @DisplayName("Проверка наличия логотипов платежных систем")
    @Severity(SeverityLevel.NORMAL)
    @Description("Тест проверяет наличие логотипов Visa, Mastercard, Белкарт")
    public void testPaymentLogos() {
        assertTrue(homePage.isPaymentLogoPresent("Visa"), "Логотип Visa не найден");
        assertTrue(homePage.isPaymentLogoPresent("MasterCard"), "Логотип MasterCard не найден");
        assertTrue(homePage.isPaymentLogoPresent("Белкарт"), "Логотип Белкарт не найден");
    }

    @Test
    @Story("Проверка ссылки")
    @DisplayName("Проверка работы ссылки 'Подробнее о сервисе'")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Тест проверяет, что клик по ссылке ведет на другую страницу")
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
        Allure.addAttachment("Новый URL", newUrl);
    }

    @Test
    @Story("Проверка полей")
    @DisplayName("Проверка надписей в незаполненных полях")
    @Severity(SeverityLevel.NORMAL)
    @Description("Тест проверяет placeholder'ы всех полей в форме")
    public void testEmptyFieldsPlaceholders() {
        assertEquals("Номер телефона", homePage.getFieldPlaceholder("connection-phone"),
                "Неверный placeholder для поля телефона");
        assertEquals("Сумма", homePage.getFieldPlaceholder("connection-sum"),
                "Неверный placeholder для поля суммы");
        assertEquals("E-mail для отправки чека", homePage.getFieldPlaceholder("connection-email"),
                "Неверный placeholder для поля email");
    }

    @Test
    @Story("Проверка оплаты")
    @DisplayName("Заполнение формы и проверка окна оплаты")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Тест заполняет форму, нажимает кнопку и проверяет появление окна оплаты")
    public void testPaymentForm() {
        homePage.fillPhoneServiceForm("297777777", "100", "");
        homePage.clickContinueButton();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<WebElement> iframes = driver.findElements(By.tagName("iframe"));

        int visibleIframeIndex = -1;
        for (int i = 0; i < iframes.size(); i++) {
            if (iframes.get(i).isDisplayed()) {
                visibleIframeIndex = i;
                break;
            }
        }

        assertTrue(visibleIframeIndex >= 0, "Нет видимого iframe с формой оплаты!");
        Allure.addAttachment("Индекс iframe", String.valueOf(visibleIframeIndex));

        driver.switchTo().frame(visibleIframeIndex);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String text = driver.findElement(By.tagName("body")).getText();
        Allure.addAttachment("Текст в iframe", text);

        assertTrue(text.contains("100.00 BYN"), "Сумма 100.00 BYN не отображается");
        assertTrue(text.contains("375297777777"), "Номер 375297777777 не отображается");

        driver.switchTo().defaultContent();
    }
}