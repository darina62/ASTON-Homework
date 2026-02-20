package com.example;

import org.testng.annotations.*;
import static org.testng.Assert.*;

public class ArithmeticOperationsTest {
    
    private ArithmeticOperations arithmeticOperations;
    
    @BeforeMethod
    public void setUp() {
        arithmeticOperations = new ArithmeticOperations();
    }
    
    @Test
    public void testAddition() {
        assertEquals(arithmeticOperations.add(5, 3), 8);
    }
    
    @Test
    public void testSubtraction() {
        assertEquals(arithmeticOperations.subtract(5, 3), 2);
    }
    
    @Test
    public void testMultiplication() {
        assertEquals(arithmeticOperations.multiply(5, 3), 15);
    }
    
    @Test
    public void testDivision() {
        assertEquals(arithmeticOperations.divide(5, 2), 2.5, 0.001);
    }
    
    @Test(expectedExceptions = ArithmeticException.class)
    public void testDivisionByZero() {
        arithmeticOperations.divide(5, 0);
    }
}
