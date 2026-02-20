package com.example;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import static org.testng.Assert.*;

public class FactorialCalculatorTest {
    
    @Test
    public void testFactorialOfZero() {
        assertEquals(FactorialCalculator.calculateFactorial(0), 1);
    }
    
    @Test
    public void testFactorialOfOne() {
        assertEquals(FactorialCalculator.calculateFactorial(1), 1);
    }
    
    @Test
    public void testFactorialOfFive() {
        assertEquals(FactorialCalculator.calculateFactorial(5), 120);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFactorialOfNegativeNumber() {
        FactorialCalculator.calculateFactorial(-5);
    }
}
