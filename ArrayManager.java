/*
 * Работа с массивом
 */

import java.util.Scanner;
import java.util.Random;

public class ArrayManager {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Random random = new Random(); // Используем вместо Math.random()

    // Добавил цикл при ошибках, чтобы не требовался перезапуск программы
    public static void main(String[] args) {
        try {
            int size = getSize();
            double[] bounds = getBounds();
            double min = bounds[0];
            double max = bounds[1];

            // Выбор типа чисел
            String charType = getType();
            processArray(charType, size, min, max);

        } catch (Exception e) {
            System.out.println("Ошибка ввода данных: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    // Получение размера массива
    private static int getSize() {
        while (true) {
            try {
                System.out.print("Укажите размер массива: ");
                int size = scanner.nextInt();

                if (size <= 0) {
                    System.out.println("Ошибка: размер массива должен быть строго больше 0.");
                    continue;
                }
                return size;
            } catch (Exception e) {
                System.out.println("Ошибка: введите целое число. Попробуйте снова.");
                scanner.nextLine(); // Очистка буфера
            }
        }
    }

    // Получение границ
    private static double[] getBounds() {
        double min = 0, max = 0;
        boolean validationInput = false;

        // Получение нижней границы
        while (!validationInput) {
            try {
                System.out.print("Нижняя граница случайных чисел: ");
                min = scanner.nextDouble();
                validationInput = true;
            } catch (Exception e) {
                System.out.println("Ошибка: введите число. Попробуйте снова.");
                scanner.nextLine();
            }
        }

        validationInput = false;
        // Получение верхней границы
        while (!validationInput) {
            try {
                System.out.print("Верхняя граница случайных чисел: ");
                max = scanner.nextDouble();

                if (min > max) {
                    System.out.println("Ошибка: нижняя граница чисел не может быть больше верхней границы.");
                    continue;
                }
                validationInput = true;
            } catch (Exception e) {
                System.out.println("Ошибка: необходимо ввести число.");
                scanner.nextLine();
            }
        }

        return new double[]{min, max};
    }

    // Получение типа массива
    private static String getType() {
        while (true) {
            System.out.print("\nУкажите тип чисел массива ('int' или 'double' / 'целые' или 'вещественные'): ");
            String userInput = scanner.next().trim().toLowerCase();

            if (userInput.equals("int") || userInput.equals("целые") ||
                    userInput.equals("double") || userInput.equals("вещественные")) {
                return userInput;
            } else {
                System.out.println("Ошибка: необходимо указать корректный тип чисел ('int', 'double', 'целые' или 'вещественные').");
            }
        }
    }

    private static void processArray(String userInput, int size, double min, double max) {
        switch (userInput) {
            case "int":
            case "целые":
                processIntegerArray(size, (int) min, (int) max);
                break;
            case "double":
            case "вещественные":
                processDoubleArray(size, min, max);
                break;
        }
    }

    private static void processIntegerArray(int size, int min, int max) {
        System.out.println("\nВыбор: целочисленный массив.");

        int[] arrayInt = randomizeIntArray(size, min, max);
        System.out.println("Исходный массив:");
        printArray(arrayInt);

        // Статистика
        System.out.println("\nСтатистика:");
        System.out.println("Максимальное значение: " + findMax(arrayInt));
        System.out.println("Минимальное значение: " + findMin(arrayInt));
        System.out.println("Среднее значение: " + String.format("%.2f", findAvg(arrayInt)));

        // Сортировка
        System.out.println("\nСортировка по возрастанию:");
        printArray(sortArray(arrayInt.clone(), true));

        System.out.println("\nСортировка по убыванию:");
        printArray(sortArray(arrayInt.clone(), false));
    }

    private static void processDoubleArray(int size, double min, double max) {
        System.out.println("\nВыбор: Вещественный массив");

        double[] arrayDouble = randomizeDoubleArray(size, min, max);
        System.out.println("\nИсходный массив:");
        printArray(arrayDouble);

        // Статистика
        System.out.println("\nСтатистика:");
        System.out.println("Максимальное значение: " + findMax(arrayDouble));
        System.out.println("Минимальное значение: " + findMin(arrayDouble));
        System.out.println("Среднее значение: " + String.format("%.2f", findAvg(arrayDouble)));

        // Сортировка
        System.out.println("\nСортировка по возрастанию (ASC):");
        printArray(sortArray(arrayDouble.clone(), true));

        System.out.println("\nСортировка по убыванию (DESC):");
        printArray(sortArray(arrayDouble.clone(), false));
    }

    // Рандомизация массива (целые числа (int))
    public static int[] randomizeIntArray(int size, int min, int max) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(max - min + 1) + min;
        }
        return array;
    }

    // Поиск максимума (целые числа (int))
    public static int findMax(int[] array) {
        int max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        return max;
    }

    // Поиск минимума (целые числа (int))
    public static int findMin(int[] array) {
        int min = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] < min) {
                min = array[i];
            }
        }
        return min;
    }

    // Вычисление среднего значения (целые числа (int))
    public static double findAvg(int[] array) {
        long sum = 0;
        for (int value : array) {
            sum += value;
        }
        return (double) sum / array.length;
    }

    // Сортировка (целые числа (int))
    public static int[] sortArray(int[] array, boolean asc) {
        int[] result = array.clone();
        int n = result.length;

        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                boolean shouldSwap = asc ?
                        result[j] > result[j + 1]:
                        result[j] < result[j + 1];

                if (shouldSwap) {
                    // Обмен значениями
                    int temp = result[j];
                    result[j] = result[j + 1];
                    result[j + 1] = temp;
                    swapped = true;
                }
            }

            if (!swapped) {
                break;
            }
        }
        return result;
    }

    // Вывод массива (целые числа (int))
    public static void printArray(int[] array) {
        for (int value : array) {
            System.out.print(value + " ");
        }
        System.out.println();
    }

    // Рандомизация массива (вещественные числа (double))
    public static double[] randomizeDoubleArray(int size, double min, double max) {
        double[] array = new double[size];
        for (int i = 0; i < size; i++) {
            array[i] = min + (max - min) * random.nextDouble();
        }
        return array;
    }

    // Поиск максимума (вещественные числа (double))
    public static double findMax(double[] array) {
        double max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        return max;
    }

    // Поиск минимума (вещественные числа (double))
    public static double findMin(double[] array) {
        double min = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] < min) {
                min = array[i];
            }
        }
        return min;
    }

    // Вычисление среднего значения (вещественные числа (double))
    public static double findAvg(double[] array) {
        double sum = 0;
        for (double value : array) {
            sum += value;
        }
        return sum / array.length;
    }

    // Сортировка (вещественные числа (double))
    public static double[] sortArray(double[] array, boolean asc) {
        double[] result = array.clone();
        int n = result.length;

        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                boolean shouldSwap = asc ?
                        result[j] > result[j + 1]:
                        result[j] < result[j + 1];

                if (shouldSwap) {
                    // Обмен значениями
                    double temp = result[j];
                    result[j] = result[j + 1];
                    result[j + 1] = temp;
                    swapped = true;
                }
            }

            if (!swapped) {
                break;
            }
        }
        return result;
    }

    // Вывод массива (вещественные числа (double))
    public static void printArray(double[] array) {
        for (double value : array) {
            System.out.printf("%.2f ", value);
        }
        System.out.println();
    }
}