/*
 * Калькулятор
*/
package main.java.homework1;
import java.util.Scanner;

public class Calculator {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Программа: Калькулятор");
        System.out.println("\nПоддерживаемые операции:");
        System.out.println("'+'  - сложение");
        System.out.println("'-'  - вычитание");
        System.out.println("'*'  - умножение");
        System.out.println("'/'  - деление");
        System.out.println("'//' - целочисленное деление");
        System.out.println("'^'  - возведение в степень");
        System.out.println("'%'  - остаток от деления");
        System.out.println("Для выхода из программы введите 'exit' или 'выход'");

        // Цикл калькулятора
        while (true) {
            try {
                // Запрос выражения
                String expression = getExpression();

                // Проверка на команду выхода
                if (expression.equalsIgnoreCase("exit") || expression.equalsIgnoreCase("выход")) {
                    System.out.println("Программа: Работа калькулятора завершена.");
                    System.out.println("Для возобновления работы запустите программу заново.");
                    break;
                }

                // Проверка корректности выражения
                validationExpression(expression);

            } catch (Exception e) {
                System.err.println("Произошла ошибка: " + e.getMessage());
            }
        }
        scanner.close();
    }

     /*
     * Получение выражения, проверка на пустоту
     * @return строка с выражением
     */
    private static String getExpression() {
        System.out.print("\nВведите выражение:\n> ");
        String input = scanner.nextLine().trim();

        // Проверка на пустоту с циклом
        while (input.isEmpty()) {
            System.out.print("Ошибка: пустое выражение.\nПовторите ввод:\n> ");
            input = scanner.nextLine().trim();
        }

        return input;
    }

     /*
     * Проверка корректности выражения
     * @param expression строка с выражением
     */
    private static void validationExpression(String expression) {
        // Цикл для повторного ввода до корректного выражения
        while (true) {
            try {
                // Сплит строки по пробелам
                String[] parts = expression.split("\\s+");

                // Проверка количества частей
                if (parts.length != 3) {
                    System.out.println("Ошибка: неверный формат выражения.");
                    System.out.println("Ожидается: <число> <операция> <число> (Например: 4 + 2)");
                    expression = getExpressionRetry();
                    continue;
                }

                String operation = parts[1];
                String element1 = parts[0];
                String element2 = parts[2];

                // Проверка, что оба операнда являются числами
                double operand1, operand2;
                try {
                    operand1 = Double.parseDouble(element1);
                    operand2 = Double.parseDouble(element2);
                } catch (NumberFormatException e) {
                    System.out.println("Ошибка: один из операндов не является числом.");
                    expression = getExpressionRetry();
                    continue;
                }

                // Проверка, поддерживается ли операция
                if (!supportOperation(operation)) {
                    System.out.println("Ошибка: операция '" + operation + "' не поддерживается.");
                    System.out.println("Поддерживаемые операции: '+', '-', '*', '/', '//', '^', '%'");
                    expression = getExpressionRetry();
                    continue;
                }

                // Проверка деления на ноль
                if ((operation.equals("/") || operation.equals("//") || operation.equals("%")) && operand2 == 0) {
                    System.out.println("Ошибка: деление на ноль невозможно!");
                    expression = getExpressionRetry();
                    continue;
                }

                // Выполнение операции
                performOperation(operand1, operation, operand2);
                break; // Выход из цикла при успешном выполнении

            } catch (Exception e) {
                System.err.println("Произошла ошибка при обработке выражения: " + e.getMessage());
                expression = getExpressionRetry();
            }
        }
    }

     /*
     * Повторный запрос выражения при ошибке
     * @return новое выражение
     */
    private static String getExpressionRetry() {
        System.out.print("Повторите ввод:\n> ");
        String input = scanner.nextLine().trim();

        // Проверка на пустоту
        while (input.isEmpty()) {
            System.out.print("Ошибка: пустое выражение.\nПовторите ввод: ");
            input = scanner.nextLine().trim();
        }

        // Проверка на команду выхода
        if (input.equalsIgnoreCase("exit") || input.equalsIgnoreCase("выход")) {
            System.out.println("Программа: Работа калькулятора завершена.");
            System.out.println("Для возобновления работы запустите программу заново.");
            System.exit(0);
        }

        return input;
    }

    /*
     * Проверка, поддерживается ли операция
     * @param operation символ операции
     * @return true, если операция поддерживается
     */
    private static boolean supportOperation(String operation) {
        return operation.equals("+") ||
                operation.equals("-") ||
                operation.equals("*") ||
                operation.equals("/") ||
                operation.equals("//") ||
                operation.equals("^") ||
                operation.equals("%");
    }

    /*
     * Математические операции
     * @param a первый операнд
     * @param operation символ операции
     * @param b второй операнд
     */
    private static void performOperation(double a, String operation, double b) {
        double result = 0; // Переменная результата

        try {
            switch (operation) {
                case "+": // Операция сложения
                    result = sum(a, b);
                    break;
                case "-": // Операция вычитания
                    result = subtraction(a, b);
                    break;
                case "*": // Операция умножения
                    result = multiplication(a, b);
                    break;
                case "/": // Операция деления
                    result = dividing(a, b);
                    break;
                case "//": // Операция целочисленного деления
                    result = intDividing(a, b);
                    break;
                case "^": // Операция возведения в степень
                    result = exponentiation(a, b);
                    break;
                case "%": // Операция вычисления остатка от деления
                    result = remainder(a, b);
                    break;
                default:
                    System.out.println("Ошибка: Указанная операция не поддерживается.");
                    return;
            }

            // Вывод результата с форматированием
            System.out.printf("%.2f %s %.2f = %.2f%n", a, operation, b, result);

        } catch (Exception e) {
            System.err.println("Ошибка при выполнении операции: " + e.getMessage());
        }
    }

    /*
     * Сложение двух чисел
     * @param a первый операнд
     * @param b второй операнд
     * @return результат сложения
     */
    private static double sum(double a, double b) {
        return a + b;
    }

    /*
     * Вычитание двух чисел
     * @param a первый операнд
     * @param b второй операнд
     * @return результат вычитания
     */
    private static double subtraction(double a, double b) {
        return a - b;
    }

    /*
     * Умножение двух чисел
     * @param a первый операнд
     * @param b второй операнд
     * @return результат умножения
     */
    private static double multiplication(double a, double b) {
        return a * b;
    }

    /*
     * Деление двух чисел
     * @param a первый операнд
     * @param b второй операнд
     * @return результат деления
     * @throws IllegalArgumentException если делитель равен 0
     */
    private static double dividing(double a, double b) {
        if (b == 0) {
            throw new IllegalArgumentException("Деление на 0 невозможно!");
        }
        return a / b;
    }

    /*
     * Целочисленное деление
     * @param a первый операнд
     * @param b второй операнд
     * @return результат целочисленного деления
     * @throws IllegalArgumentException если делитель равен нулю
     */
    private static double intDividing(double a, double b) {
        if (b == 0) {
            throw new IllegalArgumentException("Деление на ноль невозможно!");
        }
        return (int) (a / b); // Преобразование к целому числу
    }

    /*
     * Возведение в степень
     * @param base основание степени
     * @param exponent показатель степени
     * @return результат возведения в степень
     */
    private static double exponentiation(double base, double exponent) {
        return Math.pow(base, exponent);
    }

    /*
     * Вычисление остатка от деления
     * @param a делимое
     * @param b делитель
     * @return остаток от деления
     * @throws IllegalArgumentException если делитель равен нулю
    */
    private static double remainder(double a, double b) {
        if (b == 0) {
            throw new IllegalArgumentException("Деление на ноль невозможно!");
        }
        return a % b;
    }
}