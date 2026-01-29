package org.example;

public class Main {

    public static void main(String[] args) {

        printThreeWords();          // Задание 1
        checkSumSign();             // Задание 2
        printColor();               // Задание 3
        compareNumbers();           // Задание 4

        // Задание 5
        System.out.println("\nЗадание 5: " + checkSumRange(5, 7));
        System.out.println("Задание 5: " + checkSumRange(5, 20));

        // Задание 6
        checkNumberSign(-10);
        checkNumberSign(0);
        checkNumberSign(10);

        // Задание 7
        System.out.println("Задание 7: " + checkIfNegative(-10));
        System.out.println("Задание 7: " + checkIfNegative(0));
        System.out.println("Задание 7: " + checkIfNegative(10));

        // Задание 8
        printStringMultipleTimes("Hello", 3);

        // Задание 9
        System.out.println("Задание 9: " + isLeapYear(2020));
        System.out.println("Задание 9: " + isLeapYear(2021));
        System.out.println("Задание 9: " + isLeapYear(1900));
        System.out.println("Задание 9: " + isLeapYear(2000));

        // Задание 10
        invertArray();

        // Задание 11
        fillArray();

        // Задание 12
        multiplyNumbers();

        // Задание 13
        fillDiagonals();

        // Задание 14
        System.out.print("\nЗадание 14: ");
        int[] array14 = createArray(9, 21);
        for (int num : array14) {
            System.out.print(num + " ");
        }
        System.out.println();
    }

    // 1. Метод printThreeWords()
    public static void printThreeWords() {
        System.out.println("Задание 1:");
        System.out.println("Orange");
        System.out.println("Banana");
        System.out.println("Apple");
    }

    // 2. Метод checkSumSign()
    public static void checkSumSign() {
        System.out.println("\nЗадание 2:");
        int a = 24;
        int b = -16;

        if (a + b >= 0) {
            System.out.println("Сумма положительная");
        } else {
            System.out.println("Сумма отрицательная");
        }
    }

    // 3. Метод printColor()
    public static void printColor() {
        System.out.println("\nЗадание 3:");
        int value = 147;

        if (value <= 0) {
            System.out.println("Красный");
        } else if (value <= 100) {
            System.out.println("Желтый");
        } else {
            System.out.println("Зеленый");
        }
    }

    // 4. Метод compareNumbers()
    public static void compareNumbers() {
        System.out.println("\nЗадание 4:");
        int a = 39;
        int b = -132;

        if (a >= b) {
            System.out.println("a >= b");
        } else {
            System.out.println("a < b");
        }
    }

    // 5. Метод checkSumRange()
    public static boolean checkSumRange(int a, int b) {
        int sum = a + b;
        return sum >= 10 && sum <= 20;
    }

    // 6. Метод checkNumberSign()
    public static void checkNumberSign(int number) {
        if (number >= 0) {
            System.out.println("Задание 6: " + number + " - Положительное");
        } else {
            System.out.println("Задание 6: " + number + " - Отрицательное");
        }
    }

    // 7. Метод checkIfNegative()
    public static boolean checkIfNegative(int number) {
        return number < 0;
    }

    // 8. Метод printStringMultipleTimes()
    public static void printStringMultipleTimes(String text, int count) {
        System.out.println("\nЗадание 8:");
        for (int i = 0; i < count; i++) {
            System.out.println(text);
        }
    }

    // 9. Метод isLeapYear()
    public static boolean isLeapYear(int year) {
        return (year % 400 == 0) || (year % 4 == 0 && year % 100 != 0);
    }

    // 10. Метод invertArray()
    public static void invertArray() {
        System.out.println("\nЗадание 10:");
        int[] array = {1, 1, 0, 0, 1, 0, 1, 1, 0, 0};

        System.out.print("Исходный массив: ");
        for (int num : array) {
            System.out.print(num + " ");
        }

        System.out.print("\nИнвертированный массив: ");
        for (int i = 0; i < array.length; i++) {
            array[i] = (array[i] == 0) ? 1 : 0;
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }

    // 11. Метод fillArray()
    public static void fillArray() {
        System.out.println("\nЗадание 11:");
        int[] array = new int[100];

        for (int i = 0; i < array.length; i++) {
            array[i] = i + 1;
        }

        System.out.print("Первые 15 элементов: ");
        for (int i = 0; i < 15; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println("...");
    }

    // 12. Метод multiplyNumbers()
    public static void multiplyNumbers() {
        System.out.println("\nЗадание 12:");
        int[] array = {1, 5, 3, 2, 11, 4, 5, 2, 4, 8, 9, 1};

        System.out.print("Исходный массив: ");
        for (int num : array) {
            System.out.print(num + " ");
        }

        System.out.print("\nПреобразованный массив: ");
        for (int i = 0; i < array.length; i++) {
            if (array[i] < 6) {
                array[i] *= 2;
            }
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }

    // 13. Метод fillDiagonals()
    public static void fillDiagonals() {
        System.out.println("\nЗадание 13:");
        int size = 5;
        int[][] matrix = new int[size][size];

        for (int i = 0; i < size; i++) {
            matrix[i][i] = 1;
            matrix[i][size - 1 - i] = 1;
        }

        System.out.println("Матрица " + size + "x" + size + ":");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    // 14. Метод createArray()
    public static int[] createArray(int len, int initialValue) {
        int[] array = new int[len];
        for (int i = 0; i < len; i++) {
            array[i] = initialValue;
        }
        return array;
    }
}
