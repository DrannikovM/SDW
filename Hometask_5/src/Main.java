import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class RecordBook {
    private String studentName;
    private String bookNumber;
    private List<Session> sessions;

    public RecordBook(String studentName, String bookNumber) {
        this.studentName = studentName;
        this.bookNumber = bookNumber;
        this.sessions = new ArrayList<>();
        System.out.println("[ЛОГ] Створено залікову книжку для студента: " + studentName + " (№ " + bookNumber + ")");
    }

    public void addSession(String discipline, String controlForm, int grade) {
        Session newSession = new Session(discipline, controlForm, grade);
        this.sessions.add(newSession);
        System.out.println("[ЛОГ] До залікової книжки додано запис про дисципліну: " + discipline);
    }
    public void printRecordBook() {
        System.out.println("\n=====================================================================");
        System.out.printf(" ЗАЛІКОВА КНИЖКА СТУДЕНТА: %s | Номер: %s\n", studentName.toUpperCase(), bookNumber);
        System.out.println("=====================================================================");

        if (sessions.isEmpty()) {
            System.out.println("|                      Записи про сесії відсутні                    |");
            System.out.println("=====================================================================");
            return;
        }

        System.out.printf("| %-3s | %-30s | %-16s | %-7s |\n", "№", "Дисципліна", "Форма контролю", "Оцінка");
        System.out.println("---------------------------------------------------------------------");
        for (int i = 0; i < sessions.size(); i++) {
            sessions.get(i).printAsTableRow(i + 1);
        }
        System.out.println("=====================================================================");
    }
    public void searchByControlForm(String searchForm) {
        System.out.println("\n[ПОШУК] Запущено пошук сесій за формою контролю: \"" + searchForm + "\"");
        boolean found = false;

        String line = "---------------------------------------------------------------------";
        System.out.println(line);
        System.out.printf("| %-3s | %-30s | %-16s | %-7s |\n", "№", "Дисципліна", "Форма контролю", "Оцінка");
        System.out.println(line);

        int counter = 1;
        for (Session session : sessions) {
            if (session.getControlForm().equalsIgnoreCase(searchForm)) {
                session.printAsTableRow(counter++);
                found = true;
            }
        }
        System.out.println(line);

        if (!found) {
            System.out.println("[РЕЗУЛЬТАТ] Записів із формою контролю \"" + searchForm + "\" НЕ ЗНАЙДЕНО.");
        } else {
            System.out.println("[УСПІХ] Пошук завершено. Знайдено збігів: " + (counter - 1));
        }
    }
    class Session {
        private String discipline;
        private String controlForm;
        private int grade;

        public Session(String discipline, String controlForm, int grade) {
            this.discipline = discipline;
            this.controlForm = controlForm;
            this.grade = grade;

            System.out.println("[ВНУТРІШНІЙ ЛОГ] Ініціалізовано сесію по предмету '" + discipline + "' для " + studentName);
        }

        public String getControlForm() {
            return controlForm;
        }
        public void printAsTableRow(int index) {
            System.out.printf("| %-3d | %-30s | %-16s | %-7d |\n",
                    index, discipline, controlForm, grade);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("--- Створення нової залікової книжки ---");

        System.out.print("Введіть ПІБ студента: ");
        String name = scanner.nextLine().trim();

        System.out.print("Введіть номер залікової книжки: ");
        String bookNum = scanner.nextLine().trim();

        RecordBook myRecordBook = new RecordBook(name, bookNum);

        System.out.print("\nСкільки записів про сесію ви хочете ввести? (мінімум 1): ");
        int count = Integer.parseInt(scanner.nextLine().trim());

        for (int i = 0; i < count; i++) {
            System.out.println("\nВведення даних для дисципліни №" + (i + 1) + ":");
            System.out.print("Назва дисципліни: ");
            String discipline = scanner.nextLine().trim();

            System.out.print("Форма контролю (наприклад: Іспит, Залік): ");
            String controlForm = scanner.nextLine().trim();

            System.out.print("Оцінка (за 100-бальною шкалою): ");
            int grade = Integer.parseInt(scanner.nextLine().trim());

            myRecordBook.addSession(discipline, controlForm, grade);
        }

        myRecordBook.printRecordBook();

        System.out.print("\nВведіть форму контролю для пошуку (наприклад: Іспит): ");
        String searchCriterion = scanner.nextLine().trim();

        myRecordBook.searchByControlForm(searchCriterion);

        scanner.close();
    }
}