import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class FileProcessor {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("--- Програма для обробки тексту у файлах ---");

        File inputFile = getInputFile("Введіть шлях до початкового (вхідного) файлу: ");
        System.out.print("Введіть шлях для збереження результуючого файлу: ");
        String outputFilePath = scanner.nextLine().trim();

        System.out.println("\n[ПРОЦЕС] Початок обробки файлу...");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {

            String line;
            int lineCounter = 0;
            int totalDeletedWords = 0;

            while ((line = reader.readLine()) != null) {
                lineCounter++;

                ProcessedLine result = processLine(line);

                writer.write(result.text);
                writer.newLine();

                totalDeletedWords += result.deletedCount;
                System.out.printf("[ЛОГ] Рядок %d оброблено. Видалено слів: %d\n", lineCounter, result.deletedCount);
            }

            System.out.println("\n[УСПІХ] Обробку завершено повністю!");
            System.out.println("[РЕЗУЛЬТАТ] Створено файл: " + outputFilePath);
            System.out.println("[СТАТИСТИКА] Всього видалено слів у файлі: " + totalDeletedWords);
        } catch (IOException e) {
            System.out.println("[ПОМИЛКА] Помилка під час роботи з файлами: " + e.getMessage());
        }
    }
    private static File getInputFile(String prompt) {
        while (true) {
            System.out.print(prompt);
            String path = scanner.nextLine().trim();
            File file = new File(path);

            if (file.exists() && file.isFile()) {
                return file;
            }
            System.out.println("[ПОМИЛКА] Файл не знайдено або це директорія. Спробуйте знову.\n");
        }
    }
    private static ProcessedLine processLine(String line) {
        if (line.isEmpty()) {
            return new ProcessedLine("", 0);
        }

        String[] tokens = line.split("(?U)(?<=\\b)|(?=\\b)");

        List<Integer> targetIndices = new ArrayList<>();
        for (int i = 0; i < tokens.length; i++) {
            String token = tokens[i];
            if (token.matches("[\\p{L}']+") && token.length() >= 3 && token.length() <= 5) {
                targetIndices.add(i);
            }
        }

        int totalTargets = targetIndices.size();
        int allowedToDelete = totalTargets - (totalTargets % 2);

        for (int i = 0; i < allowedToDelete; i++) {
            int targetIndex = targetIndices.get(i);
            tokens[targetIndex] = "";
        }

        StringBuilder sb = new StringBuilder();
        for (String token : tokens) {
            sb.append(token);
        }

        String cleanedLine = sb.toString().replaceAll(" {2,}", " ");

        return new ProcessedLine(cleanedLine, allowedToDelete);
    }
    private static class ProcessedLine {
        String text;
        int deletedCount;

        ProcessedLine(String text, int deletedCount) {
            this.text = text;
            this.deletedCount = deletedCount;
        }
    }
}