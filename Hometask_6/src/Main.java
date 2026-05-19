import java.util.HashSet;
import java.util.Objects;
import java.util.Scanner;

class Point {
    private final double x;
    private final double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() { return x; }
    public double getY() { return y; }

    public double distanceTo(Point other) {
        return Math.sqrt(Math.pow(other.x - this.x, 2) + Math.pow(other.y - this.y, 2));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Double.compare(point.x, x) == 0 && Double.compare(point.y, y) == 0;
    }
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
    @Override
    public String toString() {
        return String.format("(%.1f; %.1f)", x, y);
    }
}

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        HashSet<Point> pointsSet = new HashSet<>();

        System.out.println("--- Створення колекції точок ---");
        System.out.println("[ЛОГ] Ініціалізовано порожню множину HashSet.");

        int requiredPointsCount = 5;
        while (pointsSet.size() < requiredPointsCount) {
            int currentNumber = pointsSet.size() + 1;
            System.out.printf("Введіть координати для точки №%d:\n", currentNumber);
            try {
                System.out.print("  Координата X: ");
                double x = Double.parseDouble(scanner.nextLine().trim());

                System.out.print("  Координата Y: ");
                double y = Double.parseDouble(scanner.nextLine().trim());

                Point newPoint = new Point(x, y);

                if (pointsSet.add(newPoint)) {
                    System.out.println("[ЛОГ] Точку " + newPoint + " успішно додано до HashSet.");
                } else {
                    System.out.println("[ПОПЕРЕДЖЕННЯ] Точка з такими координатами вже є у множині! Спробуйте інші координати.");
                }
            } catch (NumberFormatException e) {
                System.out.println("[ПОМИЛКА ТИПУ ДАНИХ] Введіть числове значення (можна з крапкою). Спробуйте знову.");
            }
            System.out.println();
        }

        System.out.println("--- Розрахунок сум відстаней для кожної точки ---");
        String line = "------------------------------------------------------------------";
        System.out.println(line);
        System.out.printf("| %-12s | %-35s | %-10s |\n", "Точка", "Розрахунок", "Сума");
        System.out.println(line);

        Point bestPoint = null;
        double minTotalDistance = Double.MAX_VALUE;

        for (Point currentPoint : pointsSet) {
            double currentSum = 0;
            StringBuilder calculationLog = new StringBuilder();

            for (Point otherPoint : pointsSet) {
                if (!currentPoint.equals(otherPoint)) {
                    double distance = currentPoint.distanceTo(otherPoint);
                    currentSum += distance;

                    calculationLog.append(String.format("%.2f + ", distance));
                }
            }

            if (calculationLog.length() > 0) {
                calculationLog.setLength(calculationLog.length() - 2);
            }

            System.out.printf("| %-12s | %-35s | %-10.2f |\n",
                    currentPoint.toString(), calculationLog.toString(), currentSum);

            if (currentSum < minTotalDistance) {
                minTotalDistance = currentSum;
                bestPoint = currentPoint;
            }
        }
        System.out.println(line);

        System.out.println("\n--- ФІНАЛЬНИЙ РЕЗУЛЬТАТ ПОШУКУ ---");
        if (bestPoint != null) {
            System.out.printf("[УСПІХ] Точка з найменшою сумою відстаней до інших: %s\n", bestPoint);
            System.out.printf("[РЕЗУЛЬТАТ] Мінімальна сума відстаней становить: %.2f\n", minTotalDistance);
        } else {
            System.out.println("[РЕЗУЛЬТАТ] Дані відсутні.");
        }
    }
}