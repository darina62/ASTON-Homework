package com.example;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class FactorialCalculatorTest {
    
    @Test
    @DisplayName("Факториал 0 должен быть 1")
    void testFactorialOfZero() {
        assertEquals(1, FactorialCalculator.calculateFactorial(0));
    }
    
    @Test
    @DisplayName("Факториал 1 должен быть 1")
    void testFactorialOfOne() {
        assertEquals(1, FactorialCalculator.calculateFactorial(1));
    }
    
    @Test
    @DisplayName("Факториал 5 должен быть 120")
    void testFactorialOfFive() {
        assertEquals(120, FactorialCalculator.calculateFactorial(5));
    }
    
    @Test
    @DisplayName("Факториал отрицательного числа должен выбрасывать исключение")
    void testFactorialOfNegativeNumber() {
        assertThrows(IllegalArgumentException.class, 
            () -> FactorialCalculator.calculateFactorial(-5));
    }
}
