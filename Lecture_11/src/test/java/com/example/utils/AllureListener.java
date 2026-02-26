package com.example.utils;

import io.qameta.allure.Attachment;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.util.Optional;

public class AllureListener implements TestWatcher {

    private WebDriver driver;

    public AllureListener(WebDriver driver) {
        this.driver = driver;
    }

    @Attachment(value = "Скриншот при падении", type = "image/png")
    public byte[] takeScreenshot() {
        if (driver != null) {
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        }
        return new byte[0];
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        takeScreenshot();
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        // Можно добавить логирование
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        // Тест прерван
    }

    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        // Тест отключен
    }
}