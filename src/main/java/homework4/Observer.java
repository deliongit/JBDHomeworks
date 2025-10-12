package main.java.homework4;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.function.Consumer;

class ObserverStringBuilder {
    private final StringBuilder delegate = new StringBuilder();
    private final List<Consumer<String>> observers = new ArrayList<>();

    public void addObserver(Consumer<String> observer) {
        observers.add(observer);
    }

    public void removeObserver(Consumer<String> observer) {
        observers.remove(observer);
    }

    private void notifyObserver() {
        if (observers.isEmpty()) {
            return;
        }
        String state = delegate.toString();
        for (Consumer<String> obs : observers) {
            obs.accept(state);
        }
    }

    private ObserverStringBuilder mutate(Runnable mutation) {
        mutation.run();
        notifyObserver();
        return this;
    }

    public ObserverStringBuilder append(String str) {
        return mutate(() -> delegate.append(str));
    }

    public ObserverStringBuilder append(char c) {
        return mutate(() -> delegate.append(c));
    }

    public ObserverStringBuilder append(int i) {
        return mutate(() -> delegate.append(i));
    }

    public ObserverStringBuilder append(long l) {
        return mutate(() -> delegate.append(l));
    }

    public ObserverStringBuilder insert(int offset, String str) {
        return mutate(() -> delegate.insert(offset, str));
    }

    public ObserverStringBuilder removeFirst(String substring) {
        if (substring == null || substring.isEmpty()) {
            return this;
        }
        return mutate(() -> {
            int index = delegate.indexOf(substring);
            if (index != -1) {
                delegate.delete(index, index + substring.length());
            }
        });
    }

    public ObserverStringBuilder replaceAll(String target, String replace) {
        if (target == null || target.isEmpty() || replace == null) {
            return this;
        }
        return mutate(() -> {
            String current = delegate.toString();
            String updated = current.replace(target, replace);
            delegate.setLength(0);
            delegate.append(updated);
        });
    }

    public ObserverStringBuilder delete(int start, int end) {
        return mutate(() -> delegate.delete(start, end));
    }

    public ObserverStringBuilder replace(int start, int end, String str) {
        return mutate(() -> delegate.replace(start, end, str));
    }

    public ObserverStringBuilder reverse() {
        return mutate(delegate::reverse);
    }

    @Override
    public String toString() {
        return delegate.toString();
    }
}

public class Observer {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Random random = new Random();
    private static final String[] SERVICE_NAMES = {
            "Google", "Amazon", "Netflix", "Spotify", "YouTube", "Instagram", "Twitter", "Facebook",
            "TikTok", "WhatsApp", "Zoom", "Slack", "GitHub", "StackOverflow", "Reddit", "Discord"
    };

    private static record ObserverEntry(int id, String serviceName, Consumer<String> observer) {}

    private static final List<ObserverEntry> observers = new ArrayList<>();

    public static void main(String[] args) {
        ObserverStringBuilder builder = new ObserverStringBuilder();

        while (true) {
            System.out.println("\nГлавное меню:");
            System.out.println("1. Работа со строкой");
            System.out.println("2. Управление наблюдателями");
            System.out.println("0. Выйти из программы");
            System.out.print("\nВыберите раздел: ");
            int choice = intInput();

            switch (choice) {
                case 1:
                    operationsMenu(builder);
                    break;
                case 2:
                    observersMenu(builder);
                    break;
                case 0:
                    System.out.println("\nВыход из программы.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }

    // Управление строкой
    private static void operationsMenu(ObserverStringBuilder builder) {
        while (true) {
            stringInfo(builder);
            System.out.println("\nРабота со строкой:");
            System.out.println("1. Показать строку");
            System.out.println("2. Добавить текст в конец строки");
            System.out.println("3. Добавить один символ в конец строки");
            System.out.println("4. Добавить целое число в конец строки");
            System.out.println("5. Добавить большое число в конец строки");
            System.out.println("6. Вставить текст в указанную позицию");
            System.out.println("7. Удалить первое совпадение в строке");
            System.out.println("8. Заменить все совпадения в строке");
            System.out.println("9. Удалить часть строки по диапазону");
            System.out.println("10. Заменить часть строки по диапазону");
            System.out.println("11. Развернуть строку задом наперед");
            System.out.println("0. Вернуться в главное меню");
            System.out.print("\nВыберите действие: ");
            int choice = intInput();

            try {
                switch (choice) {
                    case 1:
                        stringInfo(builder);
                        break;
                    case 2:
                        System.out.print("Введите строку: ");
                        builder.append(scanner.nextLine());
                        break;
                    case 3:
                        System.out.print("Введите один символ: ");
                        String input = scanner.nextLine();
                        if (!input.isEmpty()) {
                            builder.append(input.charAt(0));
                        } else {
                            System.out.println("Пустой ввод. Операция игнорируется.");
                        }
                        break;
                    case 4:
                        System.out.print("Введите целое число: ");
                        builder.append(intInput());
                        break;
                    case 5:
                        System.out.print("Введите большое число: ");
                        builder.append(longInput());
                        break;
                    case 6:
                        System.out.print("Позиция вставки (от 0 до " + builder.toString().length() + "): ");
                        int offset = intInput();
                        if (offset >= 0 && offset <= builder.toString().length()) {
                            System.out.print("Строка для вставки: ");
                            builder.insert(offset, scanner.nextLine());
                        } else {
                            System.out.println("Недопустимая позиция.");
                        }
                        break;
                    case 7:
                        System.out.print("Значение для удаления: ");
                        String toRemove = scanner.nextLine();
                        if (toRemove.isEmpty()) {
                            System.out.println("Пустое значение.");
                        } else {
                            builder.removeFirst(toRemove);
                            System.out.println("Первое совпадение значения удалено.");
                        }
                        break;
                    case 8:
                        System.out.print("Значение, которое меняем: ");
                        String target = scanner.nextLine();
                        if (target.isEmpty()) {
                            System.out.println("Пустое значение для поиска.");
                        } else {
                            System.out.print("Значение, на которое меняем: ");
                            String replacement = scanner.nextLine();
                            builder.replaceAll(target, replacement);
                            System.out.println("Все совпадения заменены.");
                        }
                        break;
                    case 9: {
                        System.out.print("Начало диапазона (включительно): ");
                        int start = intInput();
                        System.out.print("Конец диапазона (исключительно): ");
                        int end = intInput();
                        if (validationRange(builder, start, end)) {
                            builder.delete(start, end);
                        }
                        break;
                    }
                    case 10: {
                        System.out.print("Начало диапазона (включительно): ");
                        int start = intInput();
                        System.out.print("Конец диапазона (исключительно): ");
                        int end = intInput();
                        if (validationRange(builder, start, end)) {
                            System.out.print("Новая строка: ");
                            builder.replace(start, end, scanner.nextLine());
                        }
                        break;
                    }
                    case 11:
                        builder.reverse();
                        System.out.println("Строка развернута.");
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Неверный выбор. Попробуйте снова.");
                }
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
                scanner.nextLine();
            }
        }
    }

    // Управление наблюдателями
    private static void observersMenu(ObserverStringBuilder builder) {
        while (true) {
            System.out.println("\nУправление наблюдателями:");
            System.out.println("1. Добавить нового наблюдателя");
            System.out.println("2. Удалить последнего наблюдателя");
            System.out.println("3. Показать всех наблюдателей");
            System.out.println("4. Удалить наблюдателя по идентификатору");
            System.out.println("0. Вернуться в главное меню");

            System.out.print("\nВыберите действие: ");
            int choice = intInput();

            try {
                switch (choice) {
                    case 1:
                        String serviceName = SERVICE_NAMES[random.nextInt(SERVICE_NAMES.length)];
                        int id = observers.isEmpty() ? 1 : observers.get(observers.size() - 1).id() + 1;
                        Consumer<String> obs = state ->
                                System.out.println("[Наблюдатель " + id + " (" + serviceName + ")] Строка изменена. Новое значение: \"" + state + "\"");
                        observers.add(new ObserverEntry(id, serviceName, obs));
                        builder.addObserver(obs);
                        System.out.println("Добавлен наблюдатель. Идентификатор: " + id + ". Наименование: " + serviceName);
                        break;

                    case 2:
                        if (observers.isEmpty()) {
                            System.out.println("Нет наблюдателей для удаления.");
                        } else {
                            ObserverEntry last = observers.remove(observers.size() - 1);
                            builder.removeObserver(last.observer());
                            System.out.println("Последний наблюдатель. Идентификатор: " + last.id() + " / " + last.serviceName() + " удален.");
                        }
                        break;

                    case 3:
                        System.out.println("Подключено наблюдателей: " + observers.size());
                        System.out.println("Наблюдатели: ");
                        if (!observers.isEmpty()) {
                            for (ObserverEntry entry : observers) {
                                System.out.println("Идентификатор: " + entry.id() + ". Наименование: " + entry.serviceName());
                            }
                        }
                        break;

                    case 4:
                        if (observers.isEmpty()) {
                            System.out.println("Нет подключенных наблюдателей.");
                        } else {
                            System.out.print("Введите идентификатор наблюдателя для удаления: ");
                            int removeID = intInput();

                            boolean removed = false;
                            for (int i = 0; i < observers.size(); i++) {
                                ObserverEntry entry = observers.get(i);
                                if (entry.id() == removeID) {
                                    builder.removeObserver(entry.observer());
                                    observers.remove(i);
                                    System.out.println("Наблюдатель: " + entry.serviceName() + ". Идентификатор: " + removeID +
                                            " - успешно удален.");
                                    removed = true;
                                    break;
                                }
                            }
                            if (!removed) {
                                System.out.println("Наблюдатель с идентификатором " + removeID + " не найден.");
                            }
                        }
                        break;

                    case 0:
                        return;
                    default:
                        System.out.println("Неверный выбор. Попробуйте снова.");
                }
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
                scanner.nextLine();
            }
        }
    }

    // Информация о строке
    private static void stringInfo(ObserverStringBuilder builder) {
        System.out.println("\nТекущая строка: " + builder.toString());
        System.out.println("Наблюдателей подключено: " + observers.size());
    }

    private static int intInput() {
        while (true) {
            try {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) {
                    System.out.print("Ввод не может быть пустым. Повторите: ");
                    continue;
                }
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.print("Введите корректное целое число: ");
            }
        }
    }

    private static long longInput() {
        while (true) {
            try {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) {
                    System.out.print("Ввод не может быть пустым. Повторите: ");
                    continue;
                }
                return Long.parseLong(line);
            } catch (NumberFormatException e) {
                System.out.print("Введите корректное число: ");
            }
        }
    }

    // Проверка диапазона
    private static boolean validationRange(ObserverStringBuilder builder, int start, int end) {
        String current = builder.toString();
        int len = current.length();

        if (start < 0) {
            System.out.println("Начало диапазона не может быть меньше 0.");
            return false;
        }
        if (end < 0) {
            System.out.println("Конец диапазона не может быть меньше 0.");
            return false;
        }
        if (start > end) {
            System.out.println("Начало не может быть больше конца.");
            return false;
        }
        if (start > len) {
            System.out.println("Начало выходит за пределы строки (длина = " + len + ").");
            return false;
        }
        if (end > len) {
            System.out.println("Конец выходит за пределы строки (длина = " + len + ").");
            return false;
        }
        return true;
    }
}