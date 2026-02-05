// 1. Класс "Товар"
class Product {
    // Поля класса
    private String name;               // название
    private String productionDate;     // дата производства
    private String manufacturer;       // производитель
    private String countryOfOrigin;    // страна происхождения
    private double price;              // цена
    private boolean isReserved;        // состояние бронирования покупателем

    // Конструктор класса
    public Product(String name, String productionDate, String manufacturer,
                   String countryOfOrigin, double price, boolean isReserved) {
        this.name = name;
        this.productionDate = productionDate;
        this.manufacturer = manufacturer;
        this.countryOfOrigin = countryOfOrigin;
        this.price = price;
        this.isReserved = isReserved;
    }

    // Метод для вывода информации об объекте
    public void printInfo() {
        System.out.println("Информация о товаре:");
        System.out.println("Название: " + name);
        System.out.println("Дата производства: " + productionDate);
        System.out.println("Производитель: " + manufacturer);
        System.out.println("Страна происхождения: " + countryOfOrigin);
        System.out.printf("Цена: %.2f$\n", price);
        System.out.println("Забронирован: " + (isReserved ? "Да" : "Нет"));
        System.out.println("------------------------");
    }

    
    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public boolean isReserved() {
        return isReserved;
    }
}

// 3. Класс Park с внутренним классом (БЕЗ ArrayList)
class Park {
    private String parkName;
    private String location;
    private Attraction[] attractions;      // Обычный массив вместо ArrayList
    private int attractionCount;           // Счетчик аттракционов

    // Конструктор парка
    public Park(String parkName, String location) {
        this.parkName = parkName;
        this.location = location;
        this.attractions = new Attraction[100];  // Максимум 100 аттракционов
        this.attractionCount = 0;
    }

    // ВНУТРЕННИЙ КЛАСС для аттракционов
    public class Attraction {
        private String attractionName;    // название аттракциона
        private String workingHours;      // время работы
        private double ticketPrice;       // стоимость билета

        // Конструктор аттракциона
        public Attraction(String attractionName, String workingHours, double ticketPrice) {
            this.attractionName = attractionName;
            this.workingHours = workingHours;
            this.ticketPrice = ticketPrice;
        }

        // Метод для вывода информации об аттракционе
        public void printAttractionInfo() {
            System.out.println("Аттракцион: " + attractionName);
            System.out.println("Время работы: " + workingHours);
            System.out.printf("Стоимость билета: %.2f$\n", ticketPrice);
            System.out.println("Расположен в парке: " + parkName);
        }

        // Геттеры
        public String getAttractionName() {
            return attractionName;
        }

        public double getTicketPrice() {
            return ticketPrice;
        }
    }

    // Методы парка для работы с аттракционами

    // Добавить аттракцион
    public void addAttraction(String name, String hours, double price) {
        if (attractionCount < attractions.length) {
            attractions[attractionCount] = new Attraction(name, hours, price);
            attractionCount++;
            System.out.println("Аттракцион '" + name + "' добавлен в парк '" + parkName + "'");
        } else {
            System.out.println("Ошибка: Достигнуто максимальное количество аттракционов!");
        }
    }

    // Показать все аттракционы
    public void showAllAttractions() {
        System.out.println("\n=== АТТРАКЦИОНЫ ПАРКА '" + parkName + "' ===");
        if (attractionCount == 0) {
            System.out.println("В парке пока нет аттракционов.");
        } else {
            for (int i = 0; i < attractionCount; i++) {
                System.out.println("\nАттракцион #" + (i + 1) + ":");
                attractions[i].printAttractionInfo();
            }
        }
    }

    // Найти аттракцион по имени
    public Attraction findAttractionByName(String name) {
        for (int i = 0; i < attractionCount; i++) {
            if (attractions[i].getAttractionName().equalsIgnoreCase(name)) {
                return attractions[i];
            }
        }
        return null;
    }

    // Рассчитать общую стоимость посещения всех аттракционов
    public double calculateTotalCost() {
        double total = 0;
        for (int i = 0; i < attractionCount; i++) {
            total += attractions[i].getTicketPrice();
        }
        return total;
    }

    
    public String getParkName() {
        return parkName;
    }

    public String getLocation() {
        return location;
    }

    public int getAttractionCount() {
        return attractionCount;
    }
}

// Главный класс с методом main
public class Main {
    public static void main(String[] args) {
        System.out.println("=== ВЫПОЛНЕНИЕ ЗАДАНИЙ ===");

        // Задание 1-2: Создание класса Товар и массива из 5 товаров
        System.out.println("\n--- ЗАДАНИЕ 1-2: ТОВАРЫ ---");

        // Создаем массив из 5 товаров
        Product[] productsArray = new Product[5];

        // Заполняем массив объектами
        productsArray[0] = new Product("Samsung S25 Ultra", "01.02.2025",
                "Samsung Corp.", "Korea", 5599, true);
        productsArray[1] = new Product("iPhone 16 Pro", "15.01.2025",
                "Apple Inc.", "USA", 4999, false);
        productsArray[2] = new Product("Xiaomi 14 Ultra", "20.03.2025",
                "Xiaomi Corporation", "China", 3499, true);
        productsArray[3] = new Product("MacBook Pro 16", "10.12.2024",
                "Apple Inc.", "USA", 3299, false);
        productsArray[4] = new Product("Sony PlayStation 6", "05.11.2025",
                "Sony Interactive", "Japan", 899, true);

        // Выводим информацию о всех товарах
        for (int i = 0; i < productsArray.length; i++) {
            System.out.println("\nТовар #" + (i + 1) + ":");
            productsArray[i].printInfo();
        }

        // Дополнительный анализ товаров
        analyzeProducts(productsArray);

        // Задание 3: Класс Park с внутренним классом
        System.out.println("\n--- ЗАДАНИЕ 3: ПАРК С АТТРАКЦИОНАМИ ---");

        // Создаем парк
        Park disneyland = new Park("Диснейленд", "Париж, Франция");

        // Добавляем аттракционы
        disneyland.addAttraction("Космическая гора", "09:00-22:00", 15.50);
        disneyland.addAttraction("Пираты Карибского моря", "10:00-21:00", 12.00);
        disneyland.addAttraction("Замок Спящей красавицы", "09:00-20:00", 8.50);
        disneyland.addAttraction("Американские горки", "11:00-23:00", 18.00);

        // Показываем все аттракционы
        disneyland.showAllAttractions();

        // Поиск конкретного аттракциона
        System.out.println("\n--- ПОИСК КОНКРЕТНОГО АТТРАКЦИОНА ---");
        Park.Attraction foundAttraction = disneyland.findAttractionByName("Космическая гора");
        if (foundAttraction != null) {
            System.out.println("Найден аттракцион:");
            foundAttraction.printAttractionInfo();
        }

        // Статистика парка
        System.out.println("\n--- СТАТИСТИКА ПАРКА ---");
        System.out.println("Название парка: " + disneyland.getParkName());
        System.out.println("Местоположение: " + disneyland.getLocation());
        System.out.println("Количество аттракционов: " + disneyland.getAttractionCount());
        System.out.printf("Общая стоимость посещения всех аттракционов: %.2f$\n",
                disneyland.calculateTotalCost());
    }

    // Метод для анализа товаров (дополнительно)
    public static void analyzeProducts(Product[] products) {
        System.out.println("\n=== АНАЛИЗ ТОВАРОВ ===");

        double totalPrice = 0;
        int reservedCount = 0;
        Product mostExpensive = products[0];
        Product cheapest = products[0];

        for (int i = 0; i < products.length; i++) {
            Product product = products[i];
            totalPrice += product.getPrice();

            if (product.isReserved()) {
                reservedCount++;
            }

            if (product.getPrice() > mostExpensive.getPrice()) {
                mostExpensive = product;
            }

            if (product.getPrice() < cheapest.getPrice()) {
                cheapest = product;
            }
        }

        double averagePrice = totalPrice / products.length;

        System.out.printf("Общая стоимость всех товаров: %.2f$\n", totalPrice);
        System.out.printf("Средняя цена товара: %.2f$\n", averagePrice);
        System.out.println("Количество забронированных товаров: " + reservedCount);
        System.out.println("Самый дорогой товар: " + mostExpensive.getName() +
                " (" + mostExpensive.getPrice() + "$)");
        System.out.println("Самый дешевый товар: " + cheapest.getName() +
                " (" + cheapest.getPrice() + "$)");
    }
}
