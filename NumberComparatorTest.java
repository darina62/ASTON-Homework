package com.example;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class NumberComparatorTest {
    
    @Test
    @DisplayName("Сравнение: 5 больше 3")
    void testCompareGreater() {
        assertEquals("5 больше 3", NumberComparator.compare(5, 3));
    }
    
    @Test
    @DisplayName("Сравнение: 3 меньше 5")
    void testCompareLess() {
        assertEquals("3 меньше 5", NumberComparator.compare(3, 5));
    }
    
    @Test
    @DisplayName("Сравнение: 4 равно 4")
    void testCompareEqual() {
        assertEquals("4 равно 4", NumberComparator.compare(4, 4));
    }
    
    @Test
    @DisplayName("Поиск максимума: max(10, 5) = 10")
    void testGetMax() {
        assertEquals(10, NumberComparator.getMax(10, 5));
    }
    
    @Test
    @DisplayName("Поиск минимума: min(10, 5) = 5")
    void testGetMin() {
        assertEquals(5, NumberComparator.getMin(10, 5));
    }
}
