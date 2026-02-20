package com.example;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class TriangleAreaCalculatorTest {
    
    @Test
    @DisplayName("Площадь треугольника с основанием 5 и высотой 4 должна быть 10")
    void testTriangleArea() {
        assertEquals(10.0, TriangleAreaCalculator.calculateArea(5, 4), 0.001);
    }
    
    @Test
    @DisplayName("Площадь треугольника по формуле Герона (3,4,5) должна быть 6")
    void testHeronFormula() {
        assertEquals(6.0, TriangleAreaCalculator.calculateAreaByHeron(3, 4, 5), 0.001);
    }
    
    @Test
    @DisplayName("Исключение при отрицательном основании")
    void testNegativeBase() {
        assertThrows(IllegalArgumentException.class,
            () -> TriangleAreaCalculator.calculateArea(-5, 4));
    }
    
    @Test
    @DisplayName("Исключение при отрицательной высоте")
    void testNegativeHeight() {
        assertThrows(IllegalArgumentException.class,
            () -> TriangleAreaCalculator.calculateArea(5, -4));
    }
    
    @Test
    @DisplayName("Исключение при несуществующем треугольнике")
    void testInvalidTriangle() {
        assertThrows(IllegalArgumentException.class,
            () -> TriangleAreaCalculator.calculateAreaByHeron(1, 1, 3));
    }
}
