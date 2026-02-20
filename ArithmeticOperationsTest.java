package com.example;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class ArithmeticOperationsTest {
    
    private ArithmeticOperations arithmeticOperations;
    
    @BeforeEach
    void setUp() {
        arithmeticOperations = new ArithmeticOperations();
    }
    
    @Test
    @DisplayName("Тест сложения: 5 + 3 = 8")
    void testAddition() {
        assertEquals(8, arithmeticOperations.add(5, 3));
    }
    
    @Test
    @DisplayName("Тест вычитания: 5 - 3 = 2")
    void testSubtraction() {
        assertEquals(2, arithmeticOperations.subtract(5, 3));
    }
    
    @Test
    @DisplayName("Тест умножения: 5 * 3 = 15")
    void testMultiplication() {
        assertEquals(15, arithmeticOperations.multiply(5, 3));
    }
    
    @Test
    @DisplayName("Тест деления: 5 / 2 = 2.5")
    void testDivision() {
        assertEquals(2.5, arithmeticOperations.divide(5, 2), 0.001);
    }
    
    @Test
    @DisplayName("Тест деления на ноль - ожидаем исключение")
    void testDivisionByZero() {
        assertThrows(ArithmeticException.class,
            () -> arithmeticOperations.divide(5, 0));
    }
}
