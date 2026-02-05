// ==================== ЗАДАНИЕ 1: ЖИВОТНЫЕ ====================

// Базовый класс Животное
abstract class Animal {
    protected String name;
    private static int animalCount = 0;

    public Animal(String name) {
        this.name = name;
        animalCount++;
    }

    public abstract void run(int distance);
    public abstract void swim(int distance);

    public static int getAnimalCount() {
        return animalCount;
    }

    public String getName() {
        return name;
    }
}

// Класс Собака
class Dog extends Animal {
    private static int dogCount = 0;
    private final int maxRunDistance = 500;
    private final int maxSwimDistance = 10;

    public Dog(String name) {
        super(name);
        dogCount++;
    }

    @Override
    public void run(int distance) {
        if (distance <= maxRunDistance) {
            System.out.println(name + " пробежал " + distance + " м.");
        } else {
            System.out.println(name + " не может пробежать " + distance + " м. Максимум: " + maxRunDistance + " м.");
        }
    }

    @Override
    public void swim(int distance) {
        if (distance <= maxSwimDistance) {
            System.out.println(name + " проплыл " + distance + " м.");
        } else {
            System.out.println(name + " не может проплыть " + distance + " м. Максимум: " + maxSwimDistance + " м.");
        }
    }

    public static int getDogCount() {
        return dogCount;
    }
}

// Класс Миска
class Bowl {
    private int foodAmount;

    public Bowl(int foodAmount) {
        setFoodAmount(foodAmount);
    }

    public Bowl() {
        this(0);
    }

    public int getFoodAmount() {
        return foodAmount;
    }

    public void setFoodAmount(int foodAmount) {
        if (foodAmount >= 0) {
            this.foodAmount = foodAmount;
        } else {
            System.out.println("Количество еды не может быть отрицательным!");
        }
    }

    public void decreaseFood(int amount) {
        if (amount > 0 && foodAmount >= amount) {
            foodAmount -= amount;
        } else if (amount > foodAmount) {
            System.out.println("Недостаточно еды в миске!");
        }
    }

    public void addFood(int amount) {
        if (amount > 0) {
            foodAmount += amount;
            System.out.println("В миску добавлено " + amount + " еды. Теперь в миске: " + foodAmount + " еды.");
        }
    }

    public void info() {
        System.out.println("В миске " + foodAmount + " еды.");
    }
}

// Класс Кот
class Cat extends Animal {
    private static int catCount = 0;
    private final int maxRunDistance = 200;
    private boolean satiety;
    private final int appetite;

    public Cat(String name, int appetite) {
        super(name);
        this.appetite = appetite;
        this.satiety = false;
        catCount++;
    }

    public Cat(String name) {
        this(name, 10);
    }

    @Override
    public void run(int distance) {
        if (distance <= maxRunDistance) {
            System.out.println(name + " пробежал " + distance + " м.");
        } else {
            System.out.println(name + " не может пробежать " + distance + " м. Максимум: " + maxRunDistance + " м.");
        }
    }

    @Override
    public void swim(int distance) {
        System.out.println(name + " не умеет плавать!");
    }

    public void eat(Bowl bowl) {
        if (bowl.getFoodAmount() >= appetite) {
            bowl.decreaseFood(appetite);
            satiety = true;
            System.out.println(name + " поел и теперь сыт.");
        } else {
            System.out.println(name + " не может поесть. В миске недостаточно еды.");
        }
    }

    public boolean isSatiety() {
        return satiety;
    }

    public static int getCatCount() {
        return catCount;
    }

    public int getAppetite() {
        return appetite;
    }
}

// ==================== ЗАДАНИЕ 2: ГЕОМЕТРИЧЕСКИЕ ФИГУРЫ ====================

// Перечисление для цветов
enum ShapeColor {
    RED("Красный"),
    GREEN("Зеленый"),
    BLUE("Синий"),
    YELLOW("Желтый"),
    BLACK("Черный"),
    WHITE("Белый"),
    ORANGE("Оранжевый"),
    PURPLE("Фиолетовый"),
    PINK("Розовый"),
    GRAY("Серый");

    private final String name;

    ShapeColor(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}

// Интерфейс для геометрических фигур
interface GeometricShape {
    // Абстрактные методы
    double calculateArea();
    double calculatePerimeter();
    ShapeColor getFillColor();
    ShapeColor getBorderColor();

    // Дефолтные методы
    default String getShapeInfo() {
        return String.format("Периметр: %.2f, Площадь: %.2f",
                calculatePerimeter(), calculateArea());
    }

    default String getColorInfo() {
        return String.format("Цвет фона: %s, Цвет границ: %s",
                getFillColor(), getBorderColor());
    }

    // Статический метод для вывода полной информации
    static void printFullInfo(GeometricShape shape, String shapeName) {
        System.out.println(shapeName + ":");
        System.out.println(shape.getShapeInfo());
        System.out.println(shape.getColorInfo());
    }
}

// Класс Круг
class Circle implements GeometricShape {
    private double radius;
    private ShapeColor fillColor;
    private ShapeColor borderColor;

    public Circle(double radius, ShapeColor fillColor, ShapeColor borderColor) {
        if (radius <= 0) {
            throw new IllegalArgumentException("Радиус должен быть положительным числом!");
        }
        this.radius = radius;
        this.fillColor = fillColor;
        this.borderColor = borderColor;
    }

    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public double calculatePerimeter() {
        return 2 * Math.PI * radius;
    }

    @Override
    public ShapeColor getFillColor() {
        return fillColor;
    }

    @Override
    public ShapeColor getBorderColor() {
        return borderColor;
    }

    public double getRadius() {
        return radius;
    }
}

// Класс Прямоугольник
class Rectangle implements GeometricShape {
    private double width;
    private double height;
    private ShapeColor fillColor;
    private ShapeColor borderColor;

    public Rectangle(double width, double height, ShapeColor fillColor, ShapeColor borderColor) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Ширина и высота должны быть положительными числами!");
        }
        this.width = width;
        this.height = height;
        this.fillColor = fillColor;
        this.borderColor = borderColor;
    }

    @Override
    public double calculateArea() {
        return width * height;
    }

    @Override
    public double calculatePerimeter() {
        return 2 * (width + height);
    }

    @Override
    public ShapeColor getFillColor() {
        return fillColor;
    }

    @Override
    public ShapeColor getBorderColor() {
        return borderColor;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}

// Класс Треугольник
class Triangle implements GeometricShape {
    private double sideA;
    private double sideB;
    private double sideC;
    private ShapeColor fillColor;
    private ShapeColor borderColor;

    public Triangle(double sideA, double sideB, double sideC, ShapeColor fillColor, ShapeColor borderColor) {
        // Проверка на существование треугольника
        if (sideA <= 0 || sideB <= 0 || sideC <= 0) {
            throw new IllegalArgumentException("Стороны треугольника должны быть положительными числами!");
        }
        if (sideA + sideB <= sideC || sideA + sideC <= sideB || sideB + sideC <= sideA) {
            throw new IllegalArgumentException("Треугольник с такими сторонами не существует!");
        }
        this.sideA = sideA;
        this.sideB = sideB;
        this.sideC = sideC;
        this.fillColor = fillColor;
        this.borderColor = borderColor;
    }

    @Override
    public double calculateArea() {
        // Используем формулу Герона
        double p = calculatePerimeter() / 2;
        return Math.sqrt(p * (p - sideA) * (p - sideB) * (p - sideC));
    }

    @Override
    public double calculatePerimeter() {
        return sideA + sideB + sideC;
    }

    @Override
    public ShapeColor getFillColor() {
        return fillColor;
    }

    @Override
    public ShapeColor getBorderColor() {
        return borderColor;
    }

    public double getSideA() {
        return sideA;
    }

    public double getSideB() {
        return sideB;
    }

    public double getSideC() {
        return sideC;
    }
}

// ==================== ОСНОВНОЙ КЛАСС ====================
public class Main {
    public static void main(String[] args) {
        System.out.println("========== ЗАДАНИЕ 1: ЖИВОТНЫЕ ==========\n");

        // Часть 1: Бег и плавание
        System.out.println("=== Часть 1: Бег и плавание ===");

        Dog dog1 = new Dog("Бобик");
        Dog dog2 = new Dog("Рекс");
        Cat cat1 = new Cat("Мурзик");
        Cat cat2 = new Cat("Барсик");

        dog1.run(150);
        dog1.run(600);
        dog1.swim(5);
        dog1.swim(15);

        cat1.run(100);
        cat1.run(250);
        cat1.swim(10);

        System.out.println("\nВсего животных: " + Animal.getAnimalCount());
        System.out.println("Собак: " + Dog.getDogCount());
        System.out.println("Котов: " + Cat.getCatCount());

        // Часть 2: Коты и миска
        System.out.println("\n=== Часть 2: Коты и миска ===");

        Cat[] cats = {
                new Cat("Мурзик", 15),
                new Cat("Барсик", 10),
                new Cat("Васька", 20),
                new Cat("Рыжик", 12),
                new Cat("Пушок", 18)
        };

        Bowl bowl = new Bowl(50);
        bowl.info();

        System.out.println("\nКормим котов:");
        for (Cat cat : cats) {
            cat.eat(bowl);
        }

        System.out.println("\nСтатус сытости котов:");
        for (Cat cat : cats) {
            System.out.println(cat.getName() + ": " + (cat.isSatiety() ? "сыт" : "голоден"));
        }

        bowl.info();

        System.out.println("\nДобавляем еду и кормим голодных котов снова:");
        bowl.addFood(30);

        for (Cat cat : cats) {
            if (!cat.isSatiety()) {
                cat.eat(bowl);
            }
        }

        System.out.println("\nФинальный статус сытости:");
        for (Cat cat : cats) {
            System.out.println(cat.getName() + ": " + (cat.isSatiety() ? "сыт" : "голоден"));
        }

        bowl.info();

        System.out.println("\n\n========== ЗАДАНИЕ 2: ГЕОМЕТРИЧЕСКИЕ ФИГУРЫ ==========\n");

        System.out.println("=== Основные фигуры ===");

        // Создаем фигуры
        GeometricShape[] shapes = new GeometricShape[3];

        shapes[0] = new Circle(5.0, ShapeColor.RED, ShapeColor.BLACK);
        shapes[1] = new Rectangle(4.0, 6.0, ShapeColor.GREEN, ShapeColor.BLUE);
        shapes[2] = new Triangle(3.0, 4.0, 5.0, ShapeColor.YELLOW, ShapeColor.ORANGE);

        // Выводим информацию о каждой фигуре
        String[] shapeNames = {"Круг", "Прямоугольник", "Треугольник"};

        for (int i = 0; i < shapes.length; i++) {
            System.out.println("\n" + shapeNames[i] + ":");
            GeometricShape.printFullInfo(shapes[i], shapeNames[i]);

            // Дополнительная информация для каждой фигуры
            if (shapes[i] instanceof Circle) {
                Circle circle = (Circle) shapes[i];
                System.out.println("Радиус: " + circle.getRadius());
            } else if (shapes[i] instanceof Rectangle) {
                Rectangle rectangle = (Rectangle) shapes[i];
                System.out.println("Ширина: " + rectangle.getWidth() + ", Высота: " + rectangle.getHeight());
            } else if (shapes[i] instanceof Triangle) {
                Triangle triangle = (Triangle) shapes[i];
                System.out.println("Стороны: " + triangle.getSideA() + ", " + triangle.getSideB() + ", " + triangle.getSideC());
            }
        }

        // Дополнительные фигуры
        System.out.println("\n=== Дополнительные фигуры ===");

        GeometricShape circle2 = new Circle(7.5, ShapeColor.BLUE, ShapeColor.WHITE);
        GeometricShape rectangle2 = new Rectangle(8.2, 3.5, ShapeColor.ORANGE, ShapeColor.BLACK);
        GeometricShape triangle2 = new Triangle(5.0, 12.0, 13.0, ShapeColor.PURPLE, ShapeColor.PINK);

        GeometricShape.printFullInfo(circle2, "Круг 2");
        System.out.println("Радиус: " + ((Circle) circle2).getRadius());

        GeometricShape.printFullInfo(rectangle2, "Прямоугольник 2");
        System.out.println("Ширина: " + ((Rectangle) rectangle2).getWidth() +
                ", Высота: " + ((Rectangle) rectangle2).getHeight());

        GeometricShape.printFullInfo(triangle2, "Треугольник 2");
        System.out.println("Стороны: " + ((Triangle) triangle2).getSideA() + ", " +
                ((Triangle) triangle2).getSideB() + ", " + ((Triangle) triangle2).getSideC());

        System.out.println("\n========== ПРОГРАММА ЗАВЕРШЕНА ==========");
    }
}
