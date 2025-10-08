package main.java.homework2;

import java.util.Scanner;

// Основной класс приложения
public class RefinedCalculator {
    public static void main(String[] args) {
        Calculation calculation = new Calculation();
        calculation.run();
    }
}

// Класс калькуляции
class Calculation {
    private Scanner scanner;

    public Calculation() {
        this.scanner = new Scanner(System.in);
    }

    public void run() {
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
                runExpression(expression);

            } catch (Exception e) {
                System.err.println("Произошла ошибка: " + e.getMessage());
            }
        }
        scanner.close();
    }

    private String getExpression() {
        System.out.print("\nВведите выражение:\n> ");
        String input = scanner.nextLine().trim();

        // Проверка на пустоту
        while (input.isEmpty()) {
            System.out.print("Ошибка: пустое выражение.\nПовторите ввод:\n> ");
            input = scanner.nextLine().trim();
        }

        return input;
    }

    private void runExpression(String expression) {
        // Цикл для повторного ввода
        while (true) {
            try {
                // Сплит строки по пробелам
                String[] parts = expression.split("\\s+");

                // Проверка количества частей
                if (!ExpressionValidator.validationFormat(parts)) {
                    System.out.println("Ошибка: неверный формат выражения.");
                    System.out.println("Ожидается: <число> <операция> <число> (Например: 4 + 2)");
                    expression = getExpressionRetry();
                    continue;
                }

                String operation = parts[1];
                String element1 = parts[0];
                String element2 = parts[2];

                // Проверка операндов
                if (!ExpressionValidator.validationOperands(element1, element2)) {
                    System.out.println("Ошибка: один из операндов не является числом.");
                    expression = getExpressionRetry();
                    continue;
                }

                double operand1 = Double.parseDouble(element1);
                double operand2 = Double.parseDouble(element2);

                // Проверка поддержки операции
                if (!ExpressionValidator.validationOperationsSupport(operation)) {
                    System.out.println("Ошибка: операция '" + operation + "' не поддерживается.");
                    System.out.println("Поддерживаемые операции: '+', '-', '*', '/', '//', '^', '%'");
                    expression = getExpressionRetry();
                    continue;
                }

                // Проверка деления на ноль
                if (ExpressionValidator.validationDivision(operation, operand2)) {
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

    private String getExpressionRetry() {
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

    // Форматирование числа
    private String formatNumber(double number) {
        // Проверяем, является ли число целым
        if (number == (long) number) {
            return String.valueOf((long) number);
        } else {
            // Форматируем с одним знаком после запятой
            return String.format("%.1f", number);
        }
    }

    private void performOperation(double operand1, String operation, double operand2) {
        try {
            Operation op = createOperation(operation, operand1, operand2);
            double result = op.run();

            // Форматируем вывод в зависимости от типа числа
            String formattedOperand1 = formatNumber(operand1);
            String formattedOperand2 = formatNumber(operand2);
            String formattedResult = formatNumber(result);

            System.out.println(formattedOperand1 + " " + operation + " " + formattedOperand2 + " = " + formattedResult);
        } catch (Exception e) {
            System.err.println("Ошибка при выполнении операции: " + e.getMessage());
        }
    }

    private Operation createOperation(String operation, double operand1, double operand2) {
        switch (operation) {
            case "+":
                return new Addition(operand1, operand2);
            case "-":
                return new Subtraction(operand1, operand2);
            case "*":
                return new Multiplication(operand1, operand2);
            case "/":
                return new Division(operand1, operand2);
            case "//":
                return new IntegerDivision(operand1, operand2);
            case "^":
                return new Exponentiation(operand1, operand2);
            case "%":
                return new Remainder(operand1, operand2);
            default:
                throw new IllegalArgumentException("Операция '" + operation + "' не поддерживается");
        }
    }
}

// Проверка выражений
class ExpressionValidator {
    private static final String[] SUPPORTED_OPERATIONS = {"+", "-", "*", "/", "//", "^", "%"};

    public static boolean validationFormat(String[] parts) {
        return parts.length == 3;
    }

    public static boolean validationOperands(String element1, String element2) {
        try {
            Double.parseDouble(element1);
            Double.parseDouble(element2);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean validationOperationsSupport(String operation) {
        for (String op : SUPPORTED_OPERATIONS) {
            if (op.equals(operation)) {
                return true;
            }
        }
        return false;
    }

    public static boolean validationDivision(String operation, double operand2) {
        return (operation.equals("/") || operation.equals("//") || operation.equals("%")) && operand2 == 0;
    }
}

// Арифметические операций
abstract class Operation {
    protected double operand1;
    protected double operand2;

    public Operation(double operand1, double operand2) {
        this.operand1 = operand1;
        this.operand2 = operand2;
    }

    public abstract double run();
}

// Операция сложения
class Addition extends Operation {
    public Addition(double operand1, double operand2) {
        super(operand1, operand2);
    }

    @Override
    public double run() {
        return operand1 + operand2;
    }
}

// Операция вычитания
class Subtraction extends Operation {
    public Subtraction(double operand1, double operand2) {
        super(operand1, operand2);
    }

    @Override
    public double run() {
        return operand1 - operand2;
    }
}

// Операция умножения
class Multiplication extends Operation {
    public Multiplication(double operand1, double operand2) {
        super(operand1, operand2);
    }

    @Override
    public double run() {
        return operand1 * operand2;
    }
}

// Операция деления
class Division extends Operation {
    public Division(double operand1, double operand2) {
        super(operand1, operand2);
    }

    @Override
    public double run() {
        if (operand2 == 0) {
            throw new IllegalArgumentException("Деление на ноль невозможно!");
        }
        return operand1 / operand2;
    }
}

// Операция целочисленного деления
class IntegerDivision extends Operation {
    public IntegerDivision(double operand1, double operand2) {
        super(operand1, operand2);
    }

    @Override
    public double run() {
        if (operand2 == 0) {
            throw new IllegalArgumentException("Деление на ноль невозможно!");
        }
        return (int) (operand1 / operand2);
    }
}

// Операция возведения в степень
class Exponentiation extends Operation {
    public Exponentiation(double operand1, double operand2) {
        super(operand1, operand2);
    }

    @Override
    public double run() {
        return Math.pow(operand1, operand2);
    }
}

// Операция вычисления остатка от деления
class Remainder extends Operation {
    public Remainder(double operand1, double operand2) {
        super(operand1, operand2);
    }

    @Override
    public double run() {
        if (operand2 == 0) {
            throw new IllegalArgumentException("Деление на ноль невозможно!");
        }
        return operand1 % operand2;
    }
}