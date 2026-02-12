// Пользовательское исключение для ошибки размера массива
class MyArraySizeException extends Exception {
    public MyArraySizeException(String message) {
        super(message);
    }
}

// Пользовательское исключение для ошибки данных в ячейке
class MyArrayDataException extends Exception {
    private int row;
    private int col;

    public MyArrayDataException(String message, int row, int col) {
        super(message);
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}

public class Main {

    // Метод для обработки двумерного массива 4x4
    public static int processArray(String[][] array) throws MyArraySizeException, MyArrayDataException {
        // Проверка: массив должен быть ровно 4x4
        if (array == null) {
            throw new MyArraySizeException("Массив не должен быть null! Требуется массив 4x4.");
        }

        if (array.length != 4) {
            throw new MyArraySizeException("Массив должен быть размером 4x4! Текущий размер: " + array.length + "x?");
        }

        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) {
                throw new MyArraySizeException("Строка " + i + " массива равна null! Требуется массив 4x4.");
            }
            if (array[i].length != 4) {
                throw new MyArraySizeException("Массив должен быть размером 4x4! Строка " + i + " имеет длину " + array[i].length);
            }
        }

        int sum = 0;

        // Проход по всем элементам массива 4x4
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                try {
                    int value = Integer.parseInt(array[i][j]);
                    sum += value;
                } catch (NumberFormatException e) {
                    throw new MyArrayDataException(
                            "Нечисловое значение в ячейке [" + i + "][" + j + "]: '" + array[i][j] + "'",
                            i, j
                    );
                }
            }
        }

        return sum;
    }

    // Метод для демонстрации ArrayIndexOutOfBoundsException
    public static void demonstrateArrayIndexOutOfBounds() {
        System.out.println("\n========== ДЕМОНСТРАЦИЯ ArrayIndexOutOfBoundsException ==========");

        // Пример 1: Одномерный массив
        int[] numbers = {10, 20, 30, 40, 50};

        try {
            System.out.println("\n▶ Попытка обратиться к numbers[10] (длина массива: " + numbers.length + ")");
            int value = numbers[10];
            System.out.println("Значение: " + value);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("  ✓ ПЕРЕХВАЧЕНО: " + e.getClass().getSimpleName());
            System.out.println("  Сообщение: " + e.getMessage());
            System.out.println("  Причина: индекс 10 вне диапазона [0.." + (numbers.length - 1) + "]");
            // e.printStackTrace() - удалено, чтобы не засорять вывод
        }

        // Пример 2: Двумерный массив
        int[][] matrix = new int[3][4];

        try {
            System.out.println("\n▶ Попытка обратиться к matrix[5][2]");
            int value = matrix[5][2];
            System.out.println("Значение: " + value);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("  ✓ ПЕРЕХВАЧЕНО: " + e.getClass().getSimpleName());
            System.out.println("  Сообщение: " + e.getMessage());
            System.out.println("  Причина: индекс строки 5 вне границ [0..2]");
        }

        // Пример 3: Безопасный доступ (предотвращение исключения)
        System.out.println("\n▶ Безопасный доступ к массиву (без исключения):");
        int[] safeArray = {1, 2, 3, 4, 5};
        int index = 3;

        if (index >= 0 && index < safeArray.length) {
            System.out.println("  safeArray[" + index + "] = " + safeArray[index]);
        } else {
            System.out.println("  Индекс " + index + " вне границ массива!");
        }

        index = 10;
        if (index >= 0 && index < safeArray.length) {
            System.out.println("  safeArray[" + index + "] = " + safeArray[index]);
        } else {
            System.out.println("  Индекс " + index + " вне границ массива! (длина: " + safeArray.length + ")");
        }

        System.out.println("\n===============================================================");
    }

    public static void main(String[] args) {
        System.out.println("========== ЗАДАНИЕ: ОБРАБОТКА МАССИВА 4x4 ==========\n");

        // ТЕСТ 1: Корректный массив 4x4
        System.out.println("▶ ТЕСТ 1: Корректный массив 4x4");
        String[][] validArray = {
                {"1", "2", "3", "4"},
                {"5", "6", "7", "8"},
                {"9", "10", "11", "12"},
                {"13", "14", "15", "16"}
        };

        try {
            int result = processArray(validArray);
            System.out.println("  ✓ Сумма элементов: " + result);
        } catch (MyArraySizeException | MyArrayDataException e) {
            System.out.println("  ✗ Ошибка: " + e.getMessage());
        }

        // ТЕСТ 2: Массив неправильного размера (3x4)
        System.out.println("\n▶ ТЕСТ 2: Массив неправильного размера (3x4)");
        String[][] wrongSizeArray = {
                {"1", "2", "3", "4"},
                {"5", "6", "7", "8"},
                {"9", "10", "11", "12"}
        };

        try {
            int result = processArray(wrongSizeArray);
            System.out.println("  Сумма элементов: " + result);
        } catch (MyArraySizeException e) {
            System.out.println("  ✓ ПЕРЕХВАЧЕНО MyArraySizeException: " + e.getMessage());
        } catch (MyArrayDataException e) {
            System.out.println("  Ошибка данных: " + e.getMessage());
        }

        // ТЕСТ 3: Массив неправильного размера (4x3)
        System.out.println("\n▶ ТЕСТ 3: Массив неправильного размера (4x3)");
        String[][] wrongSizeArray2 = {
                {"1", "2", "3"},
                {"4", "5", "6"},
                {"7", "8", "9"},
                {"10", "11", "12"}
        };

        try {
            int result = processArray(wrongSizeArray2);
            System.out.println("  Сумма элементов: " + result);
        } catch (MyArraySizeException e) {
            System.out.println("  ✓ ПЕРЕХВАЧЕНО MyArraySizeException: " + e.getMessage());
        } catch (MyArrayDataException e) {
            System.out.println("  Ошибка данных: " + e.getMessage());
        }

        // ТЕСТ 4: Массив с некорректными данными
        System.out.println("\n▶ ТЕСТ 4: Массив 4x4 с НЕКОРРЕКТНЫМИ ДАННЫМИ");
        String[][] invalidDataArray = {
                {"1", "2", "3", "4"},
                {"5", "abc", "7", "8"},
                {"9", "10", "11", "12"},
                {"13", "14", "15", "16"}
        };

        try {
            int result = processArray(invalidDataArray);
            System.out.println("  Сумма элементов: " + result);
        } catch (MyArraySizeException e) {
            System.out.println("  Ошибка размера: " + e.getMessage());
        } catch (MyArrayDataException e) {
            System.out.println("  ✓ ПЕРЕХВАЧЕНО MyArrayDataException:");
            System.out.println("     Детали: " + e.getMessage());
            System.out.println("     Ячейка: [" + e.getRow() + "][" + e.getCol() + "]");
        }

        // ТЕСТ 5: Массив с null значением
        System.out.println("\n▶ ТЕСТ 5: Массив 4x4 с null значением");
        String[][] nullValueArray = {
                {"1", "2", "3", "4"},
                {"5", null, "7", "8"},
                {"9", "10", "11", "12"},
                {"13", "14", "15", "16"}
        };

        try {
            int result = processArray(nullValueArray);
            System.out.println("  Сумма элементов: " + result);
        } catch (MyArraySizeException e) {
            System.out.println("  Ошибка размера: " + e.getMessage());
        } catch (MyArrayDataException e) {
            System.out.println("  ✓ ПЕРЕХВАЧЕНО MyArrayDataException:");
            System.out.println("     Детали: " + e.getMessage());
            System.out.println("     Ячейка: [" + e.getRow() + "][" + e.getCol() + "]");
        }

        // ТЕСТ 6: Массив с пустой строкой
        System.out.println("\n▶ ТЕСТ 6: Массив 4x4 с пустой строкой");
        String[][] emptyStringArray = {
                {"1", "2", "3", "4"},
                {"5", "", "7", "8"},
                {"9", "10", "11", "12"},
                {"13", "14", "15", "16"}
        };

        try {
            int result = processArray(emptyStringArray);
            System.out.println("  Сумма элементов: " + result);
        } catch (MyArraySizeException e) {
            System.out.println("  Ошибка размера: " + e.getMessage());
        } catch (MyArrayDataException e) {
            System.out.println("  ✓ ПЕРЕХВАЧЕНО MyArrayDataException:");
            System.out.println("     Детали: " + e.getMessage());
            System.out.println("     Ячейка: [" + e.getRow() + "][" + e.getCol() + "]");
        }

        // ТЕСТ 7: null вместо массива
        System.out.println("\n▶ ТЕСТ 7: null вместо массива");
        try {
            int result = processArray(null);
            System.out.println("  Сумма элементов: " + result);
        } catch (MyArraySizeException e) {
            System.out.println("  ✓ ПЕРЕХВАЧЕНО MyArraySizeException: " + e.getMessage());
        } catch (MyArrayDataException e) {
            System.out.println("  Ошибка данных: " + e.getMessage());
        }

        // Демонстрация ArrayIndexOutOfBoundsException
        demonstrateArrayIndexOutOfBounds();

        System.out.println("\n✅ Программа завершена");
    }
}
