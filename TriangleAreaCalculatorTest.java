package com.example;

import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class TriangleAreaCalculatorTest {
    
    @Test
    public void testTriangleArea() {
        assertEquals(TriangleAreaCalculator.calculateArea(5, 4), 10.0, 0.001);
    }
    
    @Test
    public void testHeronFormula() {
        assertEquals(TriangleAreaCalculator.calculateAreaByHeron(3, 4, 5), 6.0, 0.001);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testNegativeBase() {
        TriangleAreaCalculator.calculateArea(-5, 4);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testNegativeHeight() {
        TriangleAreaCalculator.calculateArea(5, -4);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testInvalidTriangle() {
        TriangleAreaCalculator.calculateAreaByHeron(1, 1, 3);
    }
}
