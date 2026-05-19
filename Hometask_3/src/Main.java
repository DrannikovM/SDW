import java.util.Scanner;
import java.util.Random;

class Note {
    private String lastName;
    private String address;
    private String phone;
    private String additionalInfo;

    public Note(String lastName, String address, String phone, String additionalInfo) {
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
        this.additionalInfo = additionalInfo;
    }

    public String getLastName() {return lastName;}
    public String getPhone() {return phone;}

    public void printAsTableRow(int index) {
        System.out.printf("| %-3d | %-15s | %-20s | %-15s | %-25s |\n",
                index, lastName, address, phone, additionalInfo);
    }
}

public class Main {
    public static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Note[] notebook = generateData();

        System.out.println("--- ПОЧАТКОВІ ДАНІ ЗАПИСНОЇ КНИЖКИ ---");
        printTable(notebook);
        System.out.println();

        boolean running = true;
        while (running) {
            System.out.println("Оберіть дію:");
            System.out.println("0 - Вихід");
            System.out.println("1 - Пошук абонентів за першою літерою прізвища");
            System.out.println("2 - Список абонентів з мобільними телефонами");
            System.out.print("Ваш вибір: ");

            try {
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    throw new IllegalArgumentException("Помилка: Вибір не може бути порожнім рядком.");
                }

                int choice = Integer.parseInt(input);

                switch (choice) {
                    case 1:
                        searchByLetter(notebook);
                        break;
                    case 2:
                        searchMobileUsers(notebook);
                        break;
                    case 0:
                        running = false;
                        System.out.println("Програму завершено.");
                        break;
                    default:
                        System.out.println("Помилка: Некоректний пункт меню. Оберіть 0, 1 або 2.\n");
                }
            } catch (NumberFormatException e) {
                System.out.println("Помилка типу даних: Ви ввели текст замість цифри пункту меню. Спробуйте знову.\n");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage() + " Спробуйте знову.\n");
            }
        }
    }
    private static void searchByLetter(Note[] notebook) {
        System.out.print("Введіть першу літеру прізвища для пошуку: ");
        try {
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                throw new IllegalArgumentException("Помилка: Рядок введення порожній.");
            }
            if (input.length() > 1) {
                throw new IllegalArgumentException("Помилка: Потрібно ввести лише одну літеру, а не ціле слово.");
            }

            char letter = Character.toLowerCase(input.charAt(0));
            if (!Character.isLetter(letter)) {
                throw new IllegalArgumentException("Помилка: Введений символ не є літерою.");
            }

            int foundCount = 0;
            Note[] result = new Note[notebook.length];

            for (Note note : notebook) {
                if (Character.toLowerCase(note.getLastName().charAt(0)) == letter) {
                    result[foundCount] = note;
                    foundCount++;
                }
            }
            if (foundCount == 0) {
                System.out.println("Абонентів, прізвища яких починаються на '" + input + "', НЕ ЗНАЙДЕНО.\n");
            } else {
                Note[] finalResult = new Note[foundCount];
                System.arraycopy(result, 0, finalResult, 0, foundCount);
                System.out.println("Результати пошуку:");
                printTable(finalResult);
                System.out.println();
            }

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage() + " Повторіть введення.\n");
            searchByLetter(notebook);
        }
    }
    private static void searchMobileUsers(Note[] notebook) {
        int foundCount = 0;
        Note[] result = new Note[notebook.length];

        for (Note note : notebook) {
            String phone = note.getPhone().replaceAll("[\\s\\-\\(\\)]", "");
            if (phone.startsWith("+380") || (phone.startsWith("0") && phone.length() >= 10)) {
                result[foundCount] = note;
                foundCount++;
            }
        }

        if (foundCount == 0) {
            System.out.println("Абонентів з мобільними телефонами не знайдено.\n");
        } else {
            Note[] finalResult = new Note[foundCount];
            System.arraycopy(result, 0, finalResult, 0, foundCount);
            System.out.println("Список абонентів з мобільними номерами:");
            printTable(finalResult);
            System.out.println();
        }
    }
    private static void printTable(Note[] array) {
        String line = "+-----+-----------------+----------------------+-----------------+---------------------------+";
        System.out.println(line);
        System.out.printf("| %-3s | %-15s | %-20s | %-15s | %-25s |\n", "№", "Прізвище", "Адреса", "Телефон", "Додаткова інфо");
        System.out.println(line);
        for (int i = 0; i < array.length; i++) {
            array[i].printAsTableRow(i + 1);
        }
        System.out.println(line);
    }
    private static Note[] generateData() {
        return new Note[]{
                new Note("Петренко", "вул. Польова 12", "+380671112233", "Друг з університету"),
                new Note("Іванов", "вул. Хрещатик 5", "2-23-45", "Домашній міський"),
                new Note("Павлюк", "пр. Свободи 45", "+380934445566", "Колега по роботі"),
                new Note("Сидоров", "вул. Зелена 3", "7-11-22", "Сусід"),
                new Note("Поліщук", "вул. Квіткова 8", "+380507778899", "Родич"),
                new Note("Антоненко", "вул. Миру 101", "+380630001122", "Доставка")
        };
    }
}
