class Student {
    private String name;
    private String group;
    private int course;
    private java.util.List<Integer> grades;

    public Student(String name, String group, int course, java.util.List<Integer> grades) {
        this.name = name;
        this.group = group;
        this.course = course;
        this.grades = new java.util.ArrayList<>(grades);
    }

    public String getName() {
        return name;
    }

    public String getGroup() {
        return group;
    }

    public int getCourse() {
        return course;
    }

    public java.util.List<Integer> getGrades() {
        return grades;
    }

    public double getAverageGrade() {
        if (grades.isEmpty()) return 0;
        int sum = 0;
        for (int grade : grades) {
            sum += grade;
        }
        return (double) sum / grades.size();
    }

    public void setCourse(int course) {
        this.course = course;
    }
}

public class Main {

    // Метод для удаления студентов со средним баллом < 3
    public static void removeUnderperformingStudents(java.util.Set<Student> students) {
        java.util.Iterator<Student> iterator = students.iterator();
        while (iterator.hasNext()) {
            Student student = iterator.next();
            if (student.getAverageGrade() < 3) {
                iterator.remove();
                System.out.println("  ✗ Удален: " + student.getName() + " (средний балл: " +
                        String.format("%.2f", student.getAverageGrade()) + ")");
            }
        }
    }

    // Метод для перевода студентов на следующий курс (средний балл >= 3)
    public static void promoteStudents(java.util.Set<Student> students) {
        for (Student student : students) {
            if (student.getAverageGrade() >= 3) {
                int oldCourse = student.getCourse();
                student.setCourse(oldCourse + 1);
                System.out.println("  ✓ Переведен: " + student.getName() + " с " + oldCourse + " на " +
                        student.getCourse() + " курс (средний балл: " +
                        String.format("%.2f", student.getAverageGrade()) + ")");
            }
        }
    }

    // Метод для печати студентов указанного курса
    public static void printStudents(java.util.Set<Student> students, int course) {
        System.out.println("\n📌 Студенты " + course + " курса:");
        boolean found = false;

        for (Student student : students) {
            if (student.getCourse() == course) {
                System.out.println("  • " + student.getName() + " (Группа: " + student.getGroup() +
                        ", Средний балл: " + String.format("%.2f", student.getAverageGrade()) + ")");
                found = true;
            }
        }

        if (!found) {
            System.out.println("  Нет студентов на " + course + " курсе");
        }
    }

    public static void main(String[] args) {
        System.out.println("========== ЗАДАНИЕ 1: УПРАВЛЕНИЕ СТУДЕНТАМИ ==========\n");

        // Создаем коллекцию студентов
        java.util.Set<Student> students = new java.util.HashSet<>();

        // Добавляем студентов
        students.add(new Student("Иванов Иван", "ИУ6-61Б", 2, java.util.Arrays.asList(5, 4, 5, 4, 5)));
        students.add(new Student("Петров Петр", "ИУ6-62Б", 2, java.util.Arrays.asList(3, 3, 4, 3, 3)));
        students.add(new Student("Сидорова Анна", "ИУ6-61Б", 2, java.util.Arrays.asList(2, 3, 2, 3, 2)));
        students.add(new Student("Смирнов Алексей", "ИУ6-63Б", 3, java.util.Arrays.asList(4, 4, 5, 4, 4)));
        students.add(new Student("Козлова Мария", "ИУ6-63Б", 3, java.util.Arrays.asList(5, 5, 5, 5, 5)));
        students.add(new Student("Морозов Дмитрий", "ИУ6-64Б", 3, java.util.Arrays.asList(2, 2, 3, 2, 2)));
        students.add(new Student("Волкова Елена", "ИУ6-62Б", 1, java.util.Arrays.asList(4, 4, 3, 4, 4)));
        students.add(new Student("Павлов Артем", "ИУ6-61Б", 1, java.util.Arrays.asList(3, 3, 3, 3, 4)));
        students.add(new Student("Николаев Сергей", "ИУ6-64Б", 4, java.util.Arrays.asList(5, 4, 5, 5, 4)));

        System.out.println("📚 Начальный список студентов:");
        System.out.println("Всего студентов: " + students.size());

        // Тестируем printStudents
        System.out.println("\n--- ТЕСТИРОВАНИЕ printStudents ---");
        printStudents(students, 2);
        printStudents(students, 3);
        printStudents(students, 5);

        // Удаляем неуспевающих
        System.out.println("\n--- УДАЛЕНИЕ СТУДЕНТОВ СО СРЕДНИМ БАЛЛОМ < 3 ---");
        removeUnderperformingStudents(students);
        System.out.println("Осталось студентов: " + students.size());

        // Переводим на следующий курс
        System.out.println("\n--- ПЕРЕВОД СТУДЕНТОВ НА СЛЕДУЮЩИЙ КУРС ---");
        promoteStudents(students);

        // Финальный список
        System.out.println("\n--- ИТОГОВЫЙ СПИСОК СТУДЕНТОВ ---");
        printStudents(students, 1);
        printStudents(students, 2);
        printStudents(students, 3);
        printStudents(students, 4);
        printStudents(students, 5);

        System.out.println("\n✅ Программа завершена");
    }
}
