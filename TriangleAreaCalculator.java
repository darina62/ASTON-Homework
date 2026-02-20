package com.example;

public class TriangleAreaCalculator {
    
    public static double calculateArea(double base, double height) {
        if (base <= 0 || height <= 0) {
            throw new IllegalArgumentException("Основание и высота должны быть положительными числами");
        }
        return 0.5 * base * height;
    }
    
    public static double calculateAreaByHeron(double a, double b, double c) {
        if (a <= 0 || b <= 0 || c <= 0) {
            throw new IllegalArgumentException("Стороны должны быть положительными числами");
        }
        if (a + b <= c || a + c <= b || b + c <= a) {
            throw new IllegalArgumentException("Треугольник с такими сторонами не существует");
        }
        
        double semiPerimeter = (a + b + c) / 2;
        return Math.sqrt(semiPerimeter * 
                        (semiPerimeter - a) * 
                        (semiPerimeter - b) * 
                        (semiPerimeter - c));
    }
}
