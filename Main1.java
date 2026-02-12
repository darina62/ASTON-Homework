class PhoneDirectory {

    private java.util.Map<String, java.util.List<String>> directory;

    public PhoneDirectory() {
        this.directory = new java.util.HashMap<>();
    }

    public void add(String lastName, String phoneNumber) {
        if (lastName == null || lastName.trim().isEmpty()) {
            System.out.println("Ошибка: фамилия не может быть пустой");
            return;
        }
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            System.out.println("Ошибка: номер телефона не может быть пустым");
            return;
        }

        lastName = lastName.trim();
        phoneNumber = phoneNumber.trim();

        java.util.List<String> phones = directory.get(lastName);
        if (phones == null) {
            phones = new java.util.ArrayList<>();
            directory.put(lastName, phones);
        }

        if (!phones.contains(phoneNumber)) {
            phones.add(phoneNumber);
            System.out.println("  ✓ Добавлено: " + lastName + " - " + phoneNumber);
        } else {
            System.out.println("  ⚠ Номер " + phoneNumber + " уже существует для фамилии " + lastName);
        }
    }

    public java.util.List<String> get(String lastName) {
        if (lastName == null || lastName.trim().isEmpty()) {
            return new java.util.ArrayList<>();
        }
        lastName = lastName.trim();
        java.util.List<String> phones = directory.get(lastName);
        if (phones == null) {
            return new java.util.ArrayList<>();
        }
        return new java.util.ArrayList<>(phones);
    }

    public void printPhones(String lastName) {
        java.util.List<String> phones = get(lastName);

        if (phones.isEmpty()) {
            System.out.println("  ℹ Для фамилии \"" + lastName + "\" номера не найдены");
        } else {
            System.out.println("  📞 Номера для \"" + lastName + "\" (" + phones.size() + "):");
            for (int i = 0; i < phones.size(); i++) {
                System.out.println("     " + (i + 1) + ". " + phones.get(i));
            }
        }
    }

    public void printAll() {
        System.out.println("\n  === ТЕЛЕФОННЫЙ СПРАВОЧНИК ===");
        if (directory.isEmpty()) {
            System.out.println("  Справочник пуст");
            return;
        }

        java.util.List<String> sortedLastNames = new java.util.ArrayList<>(directory.keySet());
        java.util.Collections.sort(sortedLastNames);

        for (String lastName : sortedLastNames) {
            java.util.List<String> phones = directory.get(lastName);
            System.out.println("\n  " + lastName + ":");
            for (int i = 0; i < phones.size(); i++) {
                System.out.println("    " + (i + 1) + ". " + phones.get(i));
            }
        }
    }

    public String findByPhone(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            return "Некорректный номер";
        }

        phoneNumber = phoneNumber.trim();

        for (java.util.Map.Entry<String, java.util.List<String>> entry : directory.entrySet()) {
            if (entry.getValue().contains(phoneNumber)) {
                return entry.getKey();
            }
        }

        return "Номер не найден";
    }

    public boolean removePhone(String lastName, String phoneNumber) {
        java.util.List<String> phones = directory.get(lastName);
        if (phones != null && phones.remove(phoneNumber)) {
            if (phones.isEmpty()) {
                directory.remove(lastName);
            }
            System.out.println("  ✓ Удален номер " + phoneNumber + " у фамилии " + lastName);
            return true;
        }
        System.out.println("  ✗ Номер " + phoneNumber + " не найден у фамилии " + lastName);
        return false;
    }

    public boolean removeLastName(String lastName) {
        if (directory.containsKey(lastName)) {
            directory.remove(lastName);
            System.out.println("  ✓ Удалена запись для фамилии " + lastName);
            return true;
        }
        System.out.println("  ✗ Фамилия " + lastName + " не найдена");
        return false;
    }

    public int size() {
        return directory.size();
    }

    public int totalPhoneCount() {
        int count = 0;
        for (java.util.List<String> phones : directory.values()) {
            count += phones.size();
        }
        return count;
    }
}

public class Main {

    public static void main(String[] args) {
        System.out.println("========== ЗАДАНИЕ 2: ТЕЛЕФОННЫЙ СПРАВОЧНИК ==========\n");

        PhoneDirectory phoneBook = new PhoneDirectory();

        System.out.println("--- ДОБАВЛЕНИЕ ЗАПИСЕЙ ---");
        phoneBook.add("Иванов", "+7-916-123-45-67");
        phoneBook.add("Петров", "+7-903-234-56-78");
        phoneBook.add("Сидоров", "+7-925-345-67-89");
        phoneBook.add("Иванов", "+7-499-456-78-90");
        phoneBook.add("Иванов", "+7-916-987-65-43");
        phoneBook.add("Смирнов", "+7-926-567-89-01");
        phoneBook.add("Петров", "+7-985-678-90-12");
        phoneBook.add("Козлов", "+7-915-789-01-23");
        phoneBook.add("Иванов", "+7-916-123-45-67");
        phoneBook.add("", "+7-999-111-22-33");
        phoneBook.add("Тестов", "");

        phoneBook.printAll();

        System.out.println("\n--- ПОИСК ПО ФАМИЛИИ ---");
        phoneBook.printPhones("Иванов");
        phoneBook.printPhones("Петров");
        phoneBook.printPhones("Сидоров");
        phoneBook.printPhones("Кузнецов");

        System.out.println("\n--- ТЕСТИРОВАНИЕ get() МЕТОДА ---");
        System.out.println("  Иванов: " + phoneBook.get("Иванов"));
        System.out.println("  Петров: " + phoneBook.get("Петров"));
        System.out.println("  Кузнецов: " + phoneBook.get("Кузнецов"));

        System.out.println("\n--- ОБРАТНЫЙ ПОИСК (ПО НОМЕРУ) ---");
        System.out.println("  Владелец +7-916-123-45-67: " + phoneBook.findByPhone("+7-916-123-45-67"));
        System.out.println("  Владелец +7-903-234-56-78: " + phoneBook.findByPhone("+7-903-234-56-78"));
        System.out.println("  Владелец +7-999-000-11-22: " + phoneBook.findByPhone("+7-999-000-11-22"));

        System.out.println("\n--- УДАЛЕНИЕ ЗАПИСЕЙ ---");
        phoneBook.removePhone("Иванов", "+7-499-456-78-90");
        phoneBook.removePhone("Петров", "+7-999-999-99-99");
        phoneBook.removeLastName("Сидоров");
        phoneBook.removeLastName("Кузнецов");

        phoneBook.printAll();

        System.out.println("\n--- СТАТИСТИКА ---");
        System.out.println("  Всего записей (фамилий): " + phoneBook.size());
        System.out.println("  Всего номеров: " + phoneBook.totalPhoneCount());

        System.out.println("\n--- ДОПОЛНИТЕЛЬНЫЕ ТЕСТЫ ---");
        phoneBook.add("Иванов", "+7-926-111-22-33");
        phoneBook.printPhones("Иванов");

        System.out.println("\n  Удаляем последний номер у Петрова...");
        phoneBook.removePhone("Петров", "+7-903-234-56-78");
        phoneBook.removePhone("Петров", "+7-985-678-90-12");
        phoneBook.printPhones("Петров");

        phoneBook.printAll();

        System.out.println("\n✅ Программа завершена");
    }
}
