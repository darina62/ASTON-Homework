package com.example;

import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class NumberComparatorTest {
    
    @Test
    public void testCompareGreater() {
        assertEquals(NumberComparator.compare(5, 3), "5 больше 3");
    }
    
    @Test
    public void testCompareLess() {
        assertEquals(NumberComparator.compare(3, 5), "3 меньше 5");
    }
    
    @Test
    public void testCompareEqual() {
        assertEquals(NumberComparator.compare(4, 4), "4 равно 4");
    }
    
    @Test
    public void testGetMax() {
        assertEquals(NumberComparator.getMax(10, 5), 10);
    }
    
    @Test
    public void testGetMin() {
        assertEquals(NumberComparator.getMin(10, 5), 5);
    }
}
