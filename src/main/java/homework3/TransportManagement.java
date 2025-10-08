package main.java.homework3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

// Изготовление и управление транспортными средствами
public class TransportManagement {
    public static void main(String[] args) {
        new UserInterface().factory();
    }
}

// Пользовательский интерфейс
class UserInterface {
    private final List<Vehicle> vehicles = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);
    private final Random random = new Random();

    // Марки транспортных средств для рандомайзера
    private final String[] carBrands = {
            "Toyota", "Ford", "BMW", "Mercedes", "Honda", "Nissan", "Audi", "Volkswagen"
    };
    private final String[] helicopterBrands = {
            "Bell", "Sikorsky", "Boeing", "Airbus Helicopters", "Robinson", "AgustaWestland"
    };
    private final String[] submarineBrands = {
            "Lockheed Martin", "Northrop Grumman", "BAE Systems", "Thales", "Saab", "Navantia"
    };
    private final String[] shipBrands = {
            "Titanic", "Queen Mary", "Carnival", "Royal Caribbean", "Norwegian", "Princess"
    };
    private final String[] truckBrands = {
            "Volvo", "Scania", "Mercedes-Benz", "MAN", "DAF", "Iveco", "Kamaz", "Peterbilt"
    };

    public void factory() {
        while (true) {
            try {
                showMainMenu();
                int choice = validateInput("Выберите действие: ");

                switch (choice) {
                    case 1 -> createVehicle();
                    case 2 -> showVehicles();
                    case 3 -> controlVehicle();
                    case 4 -> createRandomVehicle();
                    case 5 -> utilizationVehicle();
                    case 6 -> showDetailedInfo();
                    case 7 -> {
                        System.out.println("Выход.");
                        return;
                    }
                    default -> System.out.println("Неверный выбор действия.");
                }
            } catch (Exception e) {
                System.out.println("Произошла ошибка: " + e.getMessage());
                scanner.nextLine();
            }
        }
    }

    private void showMainMenu() {
        System.out.println("\nГлавное меню:");
        System.out.println("1. Изготовить транспортное средство");
        System.out.println("2. Список всех транспортных средств");
        System.out.println("3. Управление транспортным средством");
        System.out.println("4. Изготовить случайное транспортное средство");
        System.out.println("5. Утилизировать транспортное средство");
        System.out.println("6. Детальная информация по транспортным средствам");
        System.out.println("7. Выход");
        System.out.println("Транспортных средств в наличии: " + vehicles.size());
        System.out.println();
    }

    private int validateInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print("Введите корректное число: ");
            scanner.next();
        }
        int value = scanner.nextInt();
        scanner.nextLine();
        return value;
    }

    // Вывод детальной информации о транспортном средстве
    private void showDetailedInfo() {
        Vehicle vehicle = selectVehicle();
        if (vehicle == null) return;
        System.out.println("\nДетальная информация по транспортному средству:");
        System.out.println(vehicle.detailedInfo());
    }

    // Изготовление рандомного транспортного средства
    private void createRandomVehicle() {
        System.out.print("Укажите количество транспортных средств: ");
        int count = scanner.nextInt();
        scanner.nextLine();
        if (count <= 0) {
            System.out.println("Количество транспортных средств должно быть натуральным числом.");
            return;
        }
        for (int i = 0; i < count; i++) {
            int randomType = random.nextInt(5);
            String randomBrand = null;
            int randomSpeed = random.nextInt(300) + 10;
            Vehicle vehicle = switch (randomType) {
                case 0 -> {
                    randomBrand = carBrands[random.nextInt(carBrands.length)];
                    yield new Car(randomBrand, randomSpeed);
                }
                case 1 -> {
                    randomBrand = helicopterBrands[random.nextInt(helicopterBrands.length)];
                    yield new Helicopter(randomBrand, randomSpeed);
                }
                case 2 -> {
                    randomBrand = submarineBrands[random.nextInt(submarineBrands.length)];
                    yield new Submarine(randomBrand, randomSpeed);
                }
                case 3 -> {
                    randomBrand = shipBrands[random.nextInt(shipBrands.length)];
                    yield new Ship(randomBrand, randomSpeed);
                }
                case 4 -> {
                    randomBrand = truckBrands[random.nextInt(truckBrands.length)];
                    yield new Truck(randomBrand, randomSpeed);
                }
                default -> null;
            };
            if (vehicle != null) {
                vehicles.add(vehicle);
            }
        }
        System.out.println("Изготовлено " + count + " транспортных средств.");
    }

    // Изготовление кастомного транспортного средства
    private void createVehicle() {
        System.out.println("\nВыберите тип транспортного средства:");
        System.out.println("1. " + VehicleType.CAR.showTitle());
        System.out.println("2. " + VehicleType.HELICOPTER.showTitle());
        System.out.println("3. " + VehicleType.SUBMARINE.showTitle());
        System.out.println("4. " + VehicleType.SHIP.showTitle());
        System.out.println("5. " + VehicleType.TRUCK.showTitle());
        int type = scanner.nextInt();
        scanner.nextLine();
        System.out.print("\nВведите марку транспортного средства: ");
        String brand = scanner.nextLine();
        int speed;
        while (true) {
            System.out.print("Укажите максимальную скорость транспортного средства: ");
            if (scanner.hasNextInt()) {
                speed = scanner.nextInt();
                if (speed <= 0) {
                    System.out.println("Максимальная скорость должна быть натуральным числом.");
                } else {
                    break;
                }
            } else {
                System.out.println("Введите натуральное число.");
                scanner.next();
            }
        }
        scanner.nextLine();
        Vehicle vehicle = switch (type) {
            case 1 -> new Car(brand, speed);
            case 2 -> new Helicopter(brand, speed);
            case 3 -> new Submarine(brand, speed);
            case 4 -> new Ship(brand, speed);
            case 5 -> new Truck(brand, speed);
            default -> null;
        };
        if (vehicle != null) {
            vehicles.add(vehicle);
            System.out.println("Транспортное средство изготовлено: " + vehicle.shortInfo());
        } else {
            System.out.println("Неверный тип транспортного средства.");
        }
    }

    // Вывод списка транспортных средств
    private void showVehicles() {
        if (vehicles.isEmpty()) {
            System.out.println("Транспортные средства отсутствуют.");
        } else {
            for (int i = 0; i < vehicles.size(); i++) {
                System.out.println((i + 1) + ". " + vehicles.get(i).shortInfo());
            }
        }
    }

    // Утилизация транспортным средством
    private void utilizationVehicle() {
        Vehicle vehicle = selectVehicle();
        if (vehicle == null) return;
        vehicles.remove(vehicle);
        System.out.println("Транспортное средство утилизировано: " + vehicle.shortInfo());
    }

    private Vehicle selectVehicle() {
        if (vehicles.isEmpty()) {
            System.out.println("Транспортные средства отсутствуют.");
            return null;
        }
        showVehicles();
        System.out.print("\nВведите номер транспортного средства: ");
        if (!scanner.hasNextInt()) {
            System.out.println("Введите число.");
            scanner.next();
            return null;
        }
        int index = scanner.nextInt() - 1;
        if (index < 0 || index >= vehicles.size()) {
            System.out.println("Неверный номер.");
            return null;
        }
        return vehicles.get(index);
    }

    // Управление транспортным средством
    private void controlVehicle() {
        Vehicle vehicle = selectVehicle();
        if (vehicle == null) return;
        System.out.println("Выбран: " + vehicle.shortInfo());
        while (true) {
            System.out.println("\nУправление транспортным средством:");

            Set<Action> availableSet = new HashSet<>(vehicle.getAvailableActions());
            List<Action> actions = Arrays.stream(Action.values())
                    .filter(availableSet::contains)
                    .collect(Collectors.toList());

            for (int i = 0; i < actions.size(); i++) {
                System.out.println((i + 1) + ". " + actions.get(i).getDescription());
            }
            System.out.println("0. Главное меню");
            System.out.print("\nВыберите действие: ");
            if (!scanner.hasNextInt()) {
                System.out.println("Введите число.");
                scanner.next();
                continue;
            }
            int choice = scanner.nextInt();
            if (choice == 0) {
                System.out.println("Возврат в главное меню.");
                return;
            }
            if (choice > 0 && choice <= actions.size()) {
                vehicle.performAction(actions.get(choice - 1));
            } else {
                System.out.println("Неверный выбор.");
            }
        }
    }
}

// Типы транспортных средств
enum VehicleType {
    CAR, HELICOPTER, SUBMARINE, SHIP, TRUCK;

    public String showTitle() {
        return switch (this) {
            case CAR -> "Автомобиль";
            case HELICOPTER -> "Вертолет";
            case SUBMARINE -> "Подводная лодка";
            case SHIP -> "Корабль";
            case TRUCK -> "Грузовик";
        };
    }
}

// Типы топлива
enum FuelType {
    GASOLINE, DIESEL, ELECTRIC, KEROSENE, NUCLEAR;

    public String showTitle() {
        return switch (this) {
            case GASOLINE -> "Бензин";
            case DIESEL -> "Дизельное топливо";
            case ELECTRIC -> "Электроэнергия";
            case KEROSENE -> "Керосин";
            case NUCLEAR -> "Ядерное топливо";
        };
    }
}

// Типы двигателей
enum EngineType {
    COMBUSTION, ELECTRIC, JET, NUCLEAR;

    public String showTitle() {
        return switch (this) {
            case COMBUSTION -> "Двигатель внутреннего сгорания";
            case ELECTRIC -> "Электрический двигатель";
            case JET -> "Реактивный двигатель";
            case NUCLEAR -> "Ядерный реактор";
        };
    }
}

// Действия транспортного средства
interface TransportActions {
    void start();
    void stop();
    void move();
    void left();
    void right();
    void brake();
    String shortInfo();
    String detailedInfo();
    void performAction(Action action);
    List<Action> getAvailableActions();
}

// Возможные действия
enum Action {
    START("Запустить двигатель"),
    STOP("Остановить двигатель"),
    MOVE("Начать движение"),
    LEFT("Поворот налево"),
    RIGHT("Поворот направо"),
    BRAKE("Тормозить"),
    GEAR("Переключить передачу"),
    LIGHTS_ON("Включить фары"),
    LIGHTS_OFF("Выключить фары"),
    LIGHT_TURN("Включить или выключить поворотник"),
    WINDOWS("Открыть или закрыть окна"),
    RISE("Взлет"),
    LAND("Посадка"),
    ALTITUDE("Изменить высоту"),
    ROTATE("Развернуть вертолет"),
    ANCHOR_DROP("Бросить якорь"),
    ANCHOR_RISE("Поднять якорь"),
    RADAR("Включить радар"),
    DISTRESS("Отправить сигнал бедствия"),
    DIVE("Погрузиться"),
    SURFACE("Всплыть"),
    HYDROLOCATOR("Включить гидролокатор"),
    FIRE("Выпустить торпеду"),
    FILL_BALAST("Заполнить балласт"),
    EMPTY_BALAST("Осушить балласт"),
    ATTACH_TRAILER("Подключить прицеп"),
    DETACH_TRAILER("Отключить прицеп"),
    HORN("Подать звуковой сигнал");

    private final String description;

    Action(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

// Управление скоростью
class ControlSpeed {
    private int currentSpeed;
    private final int maxSpeed;
    private final Random random = new Random();

    public ControlSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
        this.currentSpeed = 0;
    }

    public void randomizeSpeed() {
        this.currentSpeed = 1 + random.nextInt(Math.max(1, this.maxSpeed));
    }

    public void setSpeed(int speed) {
        this.currentSpeed = Math.max(0, Math.min(speed, maxSpeed));
    }

    public void resetSpeed() {
        this.currentSpeed = 0;
    }

    public int getCurrentSpeed() {
        return this.currentSpeed;
    }

    public int getMaxSpeed() {
        return this.maxSpeed;
    }
}

// Двигатель
abstract sealed class Engine permits CombustionEngine, ElectricEngine, JetEngine, NuclearEngine {
    protected final EngineType engineType;
    protected final FuelType fuelType;
    protected States.Engine engineState;

    public Engine(EngineType engineType, FuelType fuelType) {
        this.engineType = engineType;
        this.fuelType = fuelType;
        this.engineState = States.Engine.OFF;
    }

    public abstract void startEngine();
    public abstract void stopEngine();

    public String engineInfo() {
        return "Тип двигателя: " + engineType.showTitle() +
                ", Состояние двигателя: " + engineState.showTitle() +
                ", Топливо: " + fuelType.showTitle();
    }
}

final class CombustionEngine extends Engine {
    public CombustionEngine(FuelType fuelType) {
        super(EngineType.COMBUSTION, fuelType);
    }

    @Override
    public void startEngine() {
        engineState = States.Engine.ON;
        System.out.println(engineType.showTitle() + " запущен. Использует: " + fuelType.showTitle() + ".");
    }

    @Override
    public void stopEngine() {
        engineState = States.Engine.OFF;
        System.out.println(engineType.showTitle() + " остановлен.");
    }
}

final class ElectricEngine extends Engine {
    public ElectricEngine() {
        super(EngineType.ELECTRIC, FuelType.ELECTRIC);
    }

    @Override
    public void startEngine() {
        engineState = States.Engine.ON;
        System.out.println(engineType.showTitle() + " запущен. Использует: " + fuelType.showTitle() + ".");
    }

    @Override
    public void stopEngine() {
        engineState = States.Engine.OFF;
        System.out.println(engineType.showTitle() + " остановлен.");
    }
}

final class JetEngine extends Engine {
    public JetEngine() {
        super(EngineType.JET, FuelType.KEROSENE);
    }

    @Override
    public void startEngine() {
        engineState = States.Engine.ON;
        System.out.println(engineType.showTitle() + " запущен. Использует: " + fuelType.showTitle() + ".");
    }

    @Override
    public void stopEngine() {
        engineState = States.Engine.OFF;
        System.out.println(engineType.showTitle() + " остановлен.");
    }
}

final class NuclearEngine extends Engine {
    public NuclearEngine() {
        super(EngineType.NUCLEAR, FuelType.NUCLEAR);
    }

    @Override
    public void startEngine() {
        engineState = States.Engine.ON;
        System.out.println(engineType.showTitle() + " запущен. Использует: " + fuelType.showTitle() + ".");
    }

    @Override
    public void stopEngine() {
        engineState = States.Engine.OFF;
        System.out.println(engineType.showTitle() + " остановлен.");
    }
}

// Транспортное средство
abstract sealed class Vehicle implements TransportActions permits Car, Helicopter, Submarine, Ship, Truck {
    protected final String brand;
    protected final ControlSpeed speedManager;
    protected final Engine engine;
    protected final VehicleType vehicleType;

    public Vehicle(String brand, int maxSpeed, Engine engine, VehicleType vehicleType) {
        this.brand = brand;
        this.speedManager = new ControlSpeed(maxSpeed);
        this.engine = engine;
        this.vehicleType = vehicleType;
    }

    @Override
    public String shortInfo() {
        return "Тип: " + vehicleType.showTitle() +
                ", Марка: " + brand +
                ", Максимальная скорость: " + speedManager.getMaxSpeed() + " км/ч, " +
                engine.engineInfo();
    }

    @Override
    public String detailedInfo() {
        return "Тип: " + vehicleType.showTitle() + "\n" +
                "Марка: " + brand + "\n" +
                "Максимальная скорость: " + speedManager.getMaxSpeed() + " км/ч\n" +
                "Текущая скорость: " + speedManager.getCurrentSpeed() + " км/ч\n" +
                engine.engineInfo() + "\n";
    }

    protected int getCurrentSpeed() {
        return speedManager.getCurrentSpeed();
    }

    protected void resetSpeed() {
        speedManager.resetSpeed();
    }
}

// Проверка возможности выполнить действие
class VehicleValidator {
    public static final String ERROR = "Невозможно. ";

    public static StatusValidator tryEngineStart(States.Engine engineState) {
        return switch (engineState) {
            case OFF -> StatusValidator.ALLOWED;
            case ON -> StatusValidator.ENGINE_ON;
        };
    }

    public static StatusValidator tryEngineStop(States.Engine engineState) {
        return switch (engineState) {
            case ON -> StatusValidator.ALLOWED;
            case OFF -> StatusValidator.ENGINE_OFF;
        };
    }

    public static StatusValidator tryMove(States.Engine engineState) {
        return engineState == States.Engine.OFF ? StatusValidator.ENGINE_OFF : StatusValidator.ALLOWED;
    }

    public static StatusValidator tryBrake(States.Engine engineState) {
        return engineState == States.Engine.OFF ? StatusValidator.ENGINE_OFF : StatusValidator.ALLOWED;
    }

    public static StatusValidator tryLightsOn(States.Light lightState) {
        return lightState == States.Light.ON ? StatusValidator.LIGHTS_ON : StatusValidator.ALLOWED;
    }

    public static StatusValidator tryLightsOff(States.Light lightState) {
        return lightState == States.Light.OFF ? StatusValidator.LIGHTS_OFF : StatusValidator.ALLOWED;
    }

    public static StatusValidator tryWindowsOpen(States.Window windowState) {
        return windowState == States.Window.OPEN ? StatusValidator.WINDOWS_OPEN : StatusValidator.ALLOWED;
    }

    public static StatusValidator tryWindowsClose(States.Window windowState) {
        return windowState == States.Window.CLOSED ? StatusValidator.WINDOWS_CLOSED : StatusValidator.ALLOWED;
    }

    public static StatusValidator tryAnchorDrop(States.Anchor anchorState) {
        return anchorState == States.Anchor.DROP ? StatusValidator.ANCHOR_DROP : StatusValidator.ALLOWED;
    }

    public static StatusValidator tryAnchorRise(States.Anchor anchorState) {
        return anchorState == States.Anchor.RISE ? StatusValidator.ANCHOR_RISE : StatusValidator.ALLOWED;
    }

    public static StatusValidator tryRadarUse(States.Engine engineState) {
        return engineState == States.Engine.OFF ? StatusValidator.ENGINE_OFF : StatusValidator.ALLOWED;
    }

    public static StatusValidator tryDive(States.Submersion submersionState, States.Engine engineState) {
        if (engineState == States.Engine.OFF) return StatusValidator.ENGINE_OFF;
        if (submersionState == States.Submersion.SUBMERGED) return StatusValidator.SUBMERGED;
        return StatusValidator.ALLOWED;
    }

    public static StatusValidator trySurface(States.Submersion submersionState, States.Engine engineState) {
        if (engineState == States.Engine.OFF) return StatusValidator.ENGINE_OFF;
        if (submersionState == States.Submersion.SURFACED) return StatusValidator.SURFACED;
        return StatusValidator.ALLOWED;
    }

    public static StatusValidator tryHydrolocatorUse(States.Engine engineState) {
        return engineState == States.Engine.OFF ? StatusValidator.ENGINE_OFF : StatusValidator.ALLOWED;
    }

    public static StatusValidator tryRise(States.Rotor rotorState, States.Engine engineState) {
        if (engineState == States.Engine.OFF) return StatusValidator.ENGINE_OFF;
        if (rotorState == States.Rotor.SPINNING) return StatusValidator.ROTOR_SPINNING;
        return StatusValidator.ALLOWED;
    }

    public static StatusValidator tryLand(States.Rotor rotorState) {
        return rotorState == States.Rotor.STOPPED ? StatusValidator.ROTOR_STOPPED : StatusValidator.ALLOWED;
    }

    public static StatusValidator tryAltitudeChange(States.Engine engineState) {
        return engineState == States.Engine.OFF ? StatusValidator.ENGINE_OFF : StatusValidator.ALLOWED;
    }

    public static StatusValidator tryAttachTrailer(States.Trailer trailerState) {
        return trailerState == States.Trailer.ATTACHED ? StatusValidator.TRAILER_ATTACHED : StatusValidator.ALLOWED;
    }

    public static StatusValidator tryDetachTrailer(States.Trailer trailerState) {
        return trailerState == States.Trailer.DETACHED ? StatusValidator.TRAILER_DETACHED : StatusValidator.ALLOWED;
    }

    public static StatusValidator tryFillBallast(States.Ballast ballastState) {
        return ballastState == States.Ballast.FILLED ? StatusValidator.BALLAST_FILLED : StatusValidator.ALLOWED;
    }

    public static StatusValidator tryEmptyBallast(States.Ballast ballastState) {
        return ballastState == States.Ballast.EMPTIED ? StatusValidator.BALLAST_EMPTIED : StatusValidator.ALLOWED;
    }
}

// Результат проверки возможности выполнить действие
enum StatusValidator {
    ALLOWED("Действие разрешено."),
    ENGINE_OFF("Двигатель выключен."),
    ENGINE_ON("Двигатель уже запущен."),
    ANCHOR_DROP("Якорь уже брошен."),
    ANCHOR_RISE("Якорь уже поднят."),
    SUBMERGED("Подводная лодка погружена."),
    SURFACED("Подводная лодка на поверхности."),
    LIGHTS_ON("Фары уже включены."),
    LIGHTS_OFF("Фары уже выключены."),
    WINDOWS_OPEN("Окна уже открыты."),
    WINDOWS_CLOSED("Окна уже закрыты."),
    ROTOR_STOPPED("Ротор остановлен."),
    ROTOR_SPINNING("Ротор уже вращается."),
    TRAILER_ATTACHED("Прицеп уже подключен."),
    TRAILER_DETACHED("Прицеп не подключен."),
    BALLAST_FILLED("Балластные цистерны уже заполнены."),
    BALLAST_EMPTIED("Балластные цистерны уже осушены.");

    private final String message;

    StatusValidator(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

// Статусы модулей транспортных средств
interface Stateful {
    String showTitle();
}

class States {
    // Статус двигателя
    enum Engine implements Stateful {
        ON, OFF;

        @Override
        public String showTitle() {
            return this == ON ? "Работает" : "Остановлен";
        }
    }

    // Статус фар
    enum Light implements Stateful {
        ON, OFF;

        @Override
        public String showTitle() {
            return this == ON ? "Включены" : "Выключены";
        }
    }

    // Статус поворотника
    enum Signal implements Stateful {
        ON, OFF;

        @Override
        public String showTitle() {
            return this == ON ? "Включен" : "Выключен";
        }
    }

    // Статус окон
    enum Window implements Stateful {
        OPEN, CLOSED;

        @Override
        public String showTitle() {
            return this == OPEN ? "Открыты" : "Закрыты";
        }
    }

    // Статус якоря
    enum Anchor implements Stateful {
        DROP, RISE;

        @Override
        public String showTitle() {
            return this == DROP ? "Брошен" : "Поднят";
        }
    }

    // Статус сенсоров
    enum Sensor implements Stateful {
        ON, OFF;

        @Override
        public String showTitle() {
            return this == ON ? "Включен" : "Выключен";
        }
    }

    // Статус ротора
    enum Rotor implements Stateful {
        SPINNING, STOPPED;

        @Override
        public String showTitle() {
            return this == SPINNING ? "Вращается" : "Остановлен";
        }
    }

    // Статус подводной лодки
    enum Submersion implements Stateful {
        SURFACED, SUBMERGED;

        @Override
        public String showTitle() {
            return this == SURFACED ? "На поверхности" : "Погружена";
        }
    }

    // Статус прицепа
    enum Trailer implements Stateful {
        ATTACHED, DETACHED;

        @Override
        public String showTitle() {
            return this == ATTACHED ? "Подключен" : "Отключен";
        }
    }

    // Статус балластных цистерн
    enum Ballast implements Stateful {
        FILLED, EMPTIED;

        @Override
        public String showTitle() {
            return this == FILLED ? "Заполнены" : "Осушены";
        }
    }
}

// Автомобиль
final class Car extends Vehicle {
    private States.Light lights = States.Light.OFF;
    private States.Signal signal = States.Signal.OFF;
    private States.Window windows = States.Window.CLOSED;
    private int gear = 0;

    public Car(String brand, int maxSpeed) {
        super(brand, maxSpeed, new CombustionEngine(FuelType.GASOLINE), VehicleType.CAR);
    }

    @Override
    public void start() {
        StatusValidator result = VehicleValidator.tryEngineStart(engine.engineState);
        if (result == StatusValidator.ALLOWED) {
            System.out.println("Запуск двигателя автомобиля.");
            engine.startEngine();
        } else {
            System.out.println(VehicleValidator.ERROR + result.getMessage());
        }
    }

    @Override
    public void stop() {
        StatusValidator result = VehicleValidator.tryEngineStop(engine.engineState);
        if (result == StatusValidator.ALLOWED) {
            System.out.println("Остановка двигателя автомобиля.");
            engine.stopEngine();
            resetSpeed();
        } else {
            System.out.println(VehicleValidator.ERROR + result.getMessage());
        }
    }

    @Override
    public void move() {
        StatusValidator result = VehicleValidator.tryMove(engine.engineState);
        if (result == StatusValidator.ALLOWED) {
            speedManager.randomizeSpeed();
            System.out.println(vehicleType.showTitle() + " начинает движение по дороге со скоростью " + getCurrentSpeed() + " км/ч.");
        } else {
            System.out.println(VehicleValidator.ERROR + result.getMessage());
        }
    }

    @Override
    public void left() {
        System.out.println(vehicleType.showTitle() + " поворачивает налево.");
    }

    @Override
    public void right() {
        System.out.println(vehicleType.showTitle() + " поворачивает направо.");
    }

    @Override
    public void brake() {
        StatusValidator result = VehicleValidator.tryBrake(engine.engineState);
        if (result == StatusValidator.ALLOWED) {
            int currentSpeed = getCurrentSpeed();
            System.out.println(vehicleType.showTitle() + " тормозит на скорости " + currentSpeed + " км/ч.");
            resetSpeed();
        } else {
            System.out.println(VehicleValidator.ERROR + result.getMessage());
        }
    }

    public void gear() {
        gear = (gear + 1) % 6;
        System.out.println("Переключение передачи автомобиля: " + gear + " передача.");
    }

    public void lightsOn() {
        StatusValidator result = VehicleValidator.tryLightsOn(lights);
        if (result == StatusValidator.ALLOWED) {
            lights = States.Light.ON;
            System.out.println("Фары автомобиля включены.");
        } else {
            System.out.println(VehicleValidator.ERROR + result.getMessage());
        }
    }

    public void lightsOff() {
        StatusValidator result = VehicleValidator.tryLightsOff(lights);
        if (result == StatusValidator.ALLOWED) {
            lights = States.Light.OFF;
            System.out.println("Фары автомобиля выключены.");
        } else {
            System.out.println(VehicleValidator.ERROR + result.getMessage());
        }
    }

    public void lightTurn() {
        signal = (signal == States.Signal.ON) ? States.Signal.OFF : States.Signal.ON;
        System.out.println("Поворотник автомобиля " + signal.showTitle() + ".");
    }

    public void windows() {
        if (windows == States.Window.OPEN) {
            StatusValidator result = VehicleValidator.tryWindowsClose(windows);
            if (result == StatusValidator.ALLOWED) {
                windows = States.Window.CLOSED;
                System.out.println("Окна автомобиля закрыты.");
            } else {
                System.out.println(VehicleValidator.ERROR + result.getMessage());
            }
        } else {
            StatusValidator result = VehicleValidator.tryWindowsOpen(windows);
            if (result == StatusValidator.ALLOWED) {
                windows = States.Window.OPEN;
                System.out.println("Окна автомобиля открыты.");
            } else {
                System.out.println(VehicleValidator.ERROR + result.getMessage());
            }
        }
    }

    @Override
    public void performAction(Action action) {
        switch (action) {
            case START -> start();
            case STOP -> stop();
            case MOVE -> move();
            case LEFT -> left();
            case RIGHT -> right();
            case BRAKE -> brake();
            case GEAR -> gear();
            case LIGHTS_ON -> lightsOn();
            case LIGHTS_OFF -> lightsOff();
            case LIGHT_TURN -> lightTurn();
            case WINDOWS -> windows();
            default -> System.out.println("Действие '" + action.getDescription() + "' недоступно для автомобиля.");
        }
    }

    @Override
    public List<Action> getAvailableActions() {
        return List.of(
                Action.START, Action.STOP, Action.MOVE, Action.LEFT, Action.RIGHT, Action.BRAKE,
                Action.GEAR, Action.LIGHTS_ON, Action.LIGHTS_OFF, Action.LIGHT_TURN, Action.WINDOWS
        );
    }

    @Override
    public String detailedInfo() {
        return super.detailedInfo() +
                "Фары: " + lights.showTitle() + "\n" +
                "Поворотник: " + signal.showTitle() + "\n" +
                "Окна: " + windows.showTitle() + "\n" +
                "Передача: " + gear + "\n";
    }
}

// Грузовик
final class Truck extends Vehicle {
    private States.Light lights = States.Light.OFF;
    private States.Signal signal = States.Signal.OFF;
    private States.Window windows = States.Window.CLOSED;
    private States.Trailer trailer = States.Trailer.DETACHED;
    private int gear = 0;

    public Truck(String brand, int maxSpeed) {
        super(brand, maxSpeed, new CombustionEngine(FuelType.DIESEL), VehicleType.TRUCK);
    }

    @Override
    public void start() {
        StatusValidator result = VehicleValidator.tryEngineStart(engine.engineState);
        if (result == StatusValidator.ALLOWED) {
            System.out.println("Запуск двигателя грузовика.");
            engine.startEngine();
        } else {
            System.out.println(VehicleValidator.ERROR + result.getMessage());
        }
    }

    @Override
    public void stop() {
        StatusValidator result = VehicleValidator.tryEngineStop(engine.engineState);
        if (result == StatusValidator.ALLOWED) {
            System.out.println("Остановка двигателя грузовика.");
            engine.stopEngine();
            resetSpeed();
        } else {
            System.out.println(VehicleValidator.ERROR + result.getMessage());
        }
    }

    @Override
    public void move() {
        StatusValidator result = VehicleValidator.tryMove(engine.engineState);
        if (result == StatusValidator.ALLOWED) {
            speedManager.randomizeSpeed();
            System.out.println(vehicleType.showTitle() + " начинает движение по дороге со скоростью " + getCurrentSpeed() + " км/ч.");
        } else {
            System.out.println(VehicleValidator.ERROR + result.getMessage());
        }
    }

    @Override
    public void left() {
        System.out.println(vehicleType.showTitle() + " поворачивает налево.");
    }

    @Override
    public void right() {
        System.out.println(vehicleType.showTitle() + " поворачивает направо.");
    }

    @Override
    public void brake() {
        StatusValidator result = VehicleValidator.tryBrake(engine.engineState);
        if (result == StatusValidator.ALLOWED) {
            int currentSpeed = getCurrentSpeed();
            System.out.println(vehicleType.showTitle() + " тормозит на скорости " + currentSpeed + " км/ч.");
            resetSpeed();
        } else {
            System.out.println(VehicleValidator.ERROR + result.getMessage());
        }
    }

    public void gear() {
        gear = (gear + 1) % 12;
        System.out.println("Переключение передачи грузовика: " + gear + " передача.");
    }

    public void lightsOn() {
        StatusValidator result = VehicleValidator.tryLightsOn(lights);
        if (result == StatusValidator.ALLOWED) {
            lights = States.Light.ON;
            System.out.println("Фары грузовика включены.");
        } else {
            System.out.println(VehicleValidator.ERROR + result.getMessage());
        }
    }

    public void lightsOff() {
        StatusValidator result = VehicleValidator.tryLightsOff(lights);
        if (result == StatusValidator.ALLOWED) {
            lights = States.Light.OFF;
            System.out.println("Фары грузовика выключены.");
        } else {
            System.out.println(VehicleValidator.ERROR + result.getMessage());
        }
    }

    public void lightTurn() {
        signal = (signal == States.Signal.ON) ? States.Signal.OFF : States.Signal.ON;
        System.out.println("Поворотник грузовика " + signal.showTitle() + ".");
    }

    public void windows() {
        if (windows == States.Window.OPEN) {
            StatusValidator result = VehicleValidator.tryWindowsClose(windows);
            if (result == StatusValidator.ALLOWED) {
                windows = States.Window.CLOSED;
                System.out.println("Окна грузовика закрыты.");
            } else {
                System.out.println(VehicleValidator.ERROR + result.getMessage());
            }
        } else {
            StatusValidator result = VehicleValidator.tryWindowsOpen(windows);
            if (result == StatusValidator.ALLOWED) {
                windows = States.Window.OPEN;
                System.out.println("Окна грузовика открыты.");
            } else {
                System.out.println(VehicleValidator.ERROR + result.getMessage());
            }
        }
    }

    public void attachTrailer() {
        StatusValidator result = VehicleValidator.tryAttachTrailer(trailer);
        if (result == StatusValidator.ALLOWED) {
            trailer = States.Trailer.ATTACHED;
            System.out.println("Прицеп подключен к грузовику.");
        } else {
            System.out.println(VehicleValidator.ERROR + result.getMessage());
        }
    }

    public void detachTrailer() {
        StatusValidator result = VehicleValidator.tryDetachTrailer(trailer);
        if (result == StatusValidator.ALLOWED) {
            trailer = States.Trailer.DETACHED;
            System.out.println("Прицеп отключен от грузовика.");
        } else {
            System.out.println(VehicleValidator.ERROR + result.getMessage());
        }
    }

    @Override
    public void performAction(Action action) {
        switch (action) {
            case START -> start();
            case STOP -> stop();
            case MOVE -> move();
            case LEFT -> left();
            case RIGHT -> right();
            case BRAKE -> brake();
            case GEAR -> gear();
            case LIGHTS_ON -> lightsOn();
            case LIGHTS_OFF -> lightsOff();
            case LIGHT_TURN -> lightTurn();
            case WINDOWS -> windows();
            case ATTACH_TRAILER -> attachTrailer();
            case DETACH_TRAILER -> detachTrailer();
            default -> System.out.println("Действие '" + action.getDescription() + "' недоступно для грузовика.");
        }
    }

    @Override
    public List<Action> getAvailableActions() {
        return List.of(
                Action.START, Action.STOP, Action.MOVE, Action.LEFT, Action.RIGHT, Action.BRAKE,
                Action.GEAR, Action.LIGHTS_ON, Action.LIGHTS_OFF, Action.LIGHT_TURN, Action.WINDOWS,
                Action.ATTACH_TRAILER, Action.DETACH_TRAILER
        );
    }

    @Override
    public String detailedInfo() {
        return super.detailedInfo() +
                "Фары: " + lights.showTitle() + "\n" +
                "Поворотник: " + signal.showTitle() + "\n" +
                "Окна: " + windows.showTitle() + "\n" +
                "Передача: " + gear + "\n" +
                "Прицеп: " + trailer.showTitle() + "\n";
    }
}

// Вертолет
final class Helicopter extends Vehicle {
    private States.Rotor rotor = States.Rotor.STOPPED;
    private int altitude = 0;
    private final Random random = new Random();

    public Helicopter(String brand, int maxSpeed) {
        super(brand, maxSpeed, new JetEngine(), VehicleType.HELICOPTER);
    }

    @Override
    public void start() {
        StatusValidator result = VehicleValidator.tryEngineStart(engine.engineState);
        if (result == StatusValidator.ALLOWED) {
            System.out.println("Запуск двигателей вертолета.");
            engine.startEngine();
        } else {
            System.out.println(VehicleValidator.ERROR + result.getMessage());
        }
    }

    @Override
    public void stop() {
        StatusValidator result = VehicleValidator.tryEngineStop(engine.engineState);
        if (result == StatusValidator.ALLOWED) {
            System.out.println("Остановка двигателей вертолета.");
            engine.stopEngine();
            resetSpeed();
        } else {
            System.out.println(VehicleValidator.ERROR + result.getMessage());
        }
    }

    @Override
    public void move() {
        StatusValidator result = VehicleValidator.tryMove(engine.engineState);
        if (result == StatusValidator.ALLOWED) {
            speedManager.randomizeSpeed();
            System.out.println(vehicleType.showTitle() + " начинает движение со скоростью " + getCurrentSpeed() + " км/ч.");
        } else {
            System.out.println(VehicleValidator.ERROR + result.getMessage());
        }
    }

    @Override
    public void left() {
        System.out.println(vehicleType.showTitle() + " поворачивает налево.");
    }

    @Override
    public void right() {
        System.out.println(vehicleType.showTitle() + " поворачивает направо.");
    }

    @Override
    public void brake() {
        StatusValidator result = VehicleValidator.tryBrake(engine.engineState);
        if (result == StatusValidator.ALLOWED) {
            int currentSpeed = getCurrentSpeed();
            System.out.println(vehicleType.showTitle() + " снижает скорость на скорости " + currentSpeed + " км/ч.");
            resetSpeed();
        } else {
            System.out.println(VehicleValidator.ERROR + result.getMessage());
        }
    }

    public void rise() {
        StatusValidator result = VehicleValidator.tryRise(rotor, engine.engineState);
        if (result == StatusValidator.ALLOWED) {
            rotor = States.Rotor.SPINNING;
            altitude = 100;
            System.out.println(vehicleType.showTitle() + " поднимается на высоту " + altitude + " метров.");
        } else {
            System.out.println(VehicleValidator.ERROR + result.getMessage());
        }
    }

    public void land() {
        StatusValidator result = VehicleValidator.tryLand(rotor);
        if (result == StatusValidator.ALLOWED) {
            rotor = States.Rotor.STOPPED;
            altitude = 0;
            System.out.println(vehicleType.showTitle() + " начинает посадку с высоты: " + altitude + " метров.");
        } else {
            System.out.println(VehicleValidator.ERROR + result.getMessage());
        }
    }

    public void altitude() {
        StatusValidator result = VehicleValidator.tryAltitudeChange(engine.engineState);
        if (result == StatusValidator.ALLOWED) {
            int d = random.nextInt(100) + 10;
            altitude += d;
            System.out.println(vehicleType.showTitle() + " меняет высоту: " + altitude + " метров (+" + d + ").");
        } else {
            System.out.println(VehicleValidator.ERROR + result.getMessage());
        }
    }

    public void rotate() {
        System.out.println(vehicleType.showTitle() + " делает разворот.");
    }

    @Override
    public void performAction(Action action) {
        switch (action) {
            case START -> start();
            case STOP -> stop();
            case MOVE -> move();
            case LEFT -> left();
            case RIGHT -> right();
            case BRAKE -> brake();
            case RISE -> rise();
            case LAND -> land();
            case ALTITUDE -> altitude();
            case ROTATE -> rotate();
            default -> System.out.println("Действие '" + action.getDescription() + "' недоступно для вертолета.");
        }
    }

    @Override
    public List<Action> getAvailableActions() {
        return List.of(
                Action.START, Action.STOP, Action.MOVE, Action.LEFT, Action.RIGHT, Action.BRAKE,
                Action.RISE, Action.LAND, Action.ALTITUDE, Action.ROTATE
        );
    }

    @Override
    public String detailedInfo() {
        return super.detailedInfo() +
                "Высота: " + altitude + " метров\n" +
                "Ротор: " + rotor.showTitle() + "\n";
    }
}

// Корабль
final class Ship extends Vehicle {
    private States.Anchor anchor = States.Anchor.RISE;
    private States.Sensor radar = States.Sensor.OFF;

    public Ship(String brand, int maxSpeed) {
        super(brand, maxSpeed, new CombustionEngine(FuelType.DIESEL), VehicleType.SHIP);
    }

    @Override
    public void start() {
        StatusValidator result = VehicleValidator.tryEngineStart(engine.engineState);
        if (result == StatusValidator.ALLOWED) {
            System.out.println("Запуск корабельных двигателей.");
            engine.startEngine();
        } else {
            System.out.println(VehicleValidator.ERROR + result.getMessage());
        }
    }

    @Override
    public void stop() {
        StatusValidator result = VehicleValidator.tryEngineStop(engine.engineState);
        if (result == StatusValidator.ALLOWED) {
            System.out.println("Остановка корабельных двигателей.");
            engine.stopEngine();
            resetSpeed();
        } else {
            System.out.println(VehicleValidator.ERROR + result.getMessage());
        }
    }

    @Override
    public void move() {
        StatusValidator result = VehicleValidator.tryMove(engine.engineState);
        if (result == StatusValidator.ALLOWED) {
            speedManager.randomizeSpeed();
            System.out.println(vehicleType.showTitle() + " начинает движение по волнам со скоростью " + getCurrentSpeed() + " км/ч.");
        } else {
            System.out.println(VehicleValidator.ERROR + result.getMessage());
        }
    }

    @Override
    public void left() {
        System.out.println(vehicleType.showTitle() + " поворачивает налево.");
    }

    @Override
    public void right() {
        System.out.println(vehicleType.showTitle() + " поворачивает направо.");
    }

    @Override
    public void brake() {
        StatusValidator result = VehicleValidator.tryBrake(engine.engineState);
        if (result == StatusValidator.ALLOWED) {
            int currentSpeed = getCurrentSpeed();
            System.out.println(vehicleType.showTitle() + " замедляется на скорости " + currentSpeed + " км/ч.");
            resetSpeed();
        } else {
            System.out.println(VehicleValidator.ERROR + result.getMessage());
        }
    }

    public void anchorDrop() {
        StatusValidator result = VehicleValidator.tryAnchorDrop(anchor);
        if (result == StatusValidator.ALLOWED) {
            anchor = States.Anchor.DROP;
            System.out.println(vehicleType.showTitle() + " бросает якорь.");
        } else {
            System.out.println(VehicleValidator.ERROR + result.getMessage());
        }
    }

    public void anchorRise() {
        StatusValidator result = VehicleValidator.tryAnchorRise(anchor);
        if (result == StatusValidator.ALLOWED) {
            anchor = States.Anchor.RISE;
            System.out.println(vehicleType.showTitle() + " поднимает якорь.");
        } else {
            System.out.println(VehicleValidator.ERROR + result.getMessage());
        }
    }

    public void radar() {
        StatusValidator result = VehicleValidator.tryRadarUse(engine.engineState);
        if (result == StatusValidator.ALLOWED) {
            radar = States.Sensor.ON;
            System.out.println("Радар корабля включен.");
        } else {
            System.out.println(VehicleValidator.ERROR + result.getMessage());
        }
    }

    public void distress() {
        System.out.println("Отправлен сигнал бедствия корабля.");
    }

    public void horn() {
        System.out.println("Корабль подает гудок.");
    }

    @Override
    public void performAction(Action action) {
        switch (action) {
            case START -> start();
            case STOP -> stop();
            case MOVE -> move();
            case LEFT -> left();
            case RIGHT -> right();
            case BRAKE -> brake();
            case ANCHOR_DROP -> anchorDrop();
            case ANCHOR_RISE -> anchorRise();
            case RADAR -> radar();
            case DISTRESS -> distress();
            case HORN -> horn();
            default -> System.out.println("Действие '" + action.getDescription() + "' недоступно для корабля.");
        }
    }

    @Override
    public List<Action> getAvailableActions() {
        return List.of(
                Action.START, Action.STOP, Action.MOVE, Action.LEFT, Action.RIGHT, Action.BRAKE,
                Action.ANCHOR_DROP, Action.ANCHOR_RISE, Action.RADAR, Action.DISTRESS, Action.HORN
        );
    }

    @Override
    public String detailedInfo() {
        return super.detailedInfo() +
                "Якорь: " + anchor.showTitle() + "\n" +
                "Радар: " + radar.showTitle() + "\n";
    }
}

// Подводная лодка
final class Submarine extends Vehicle {
    private States.Submersion submersion = States.Submersion.SURFACED;
    private States.Sensor hydrolocator = States.Sensor.OFF;
    private States.Ballast ballast = States.Ballast.EMPTIED;
    private final Random random = new Random();

    public Submarine(String brand, int maxSpeed) {
        super(brand, maxSpeed, new NuclearEngine(), VehicleType.SUBMARINE);
    }

    @Override
    public void start() {
        StatusValidator result = VehicleValidator.tryEngineStart(engine.engineState);
        if (result == StatusValidator.ALLOWED) {
            System.out.println("Запуск ядерного реактора подводной лодки.");
            engine.startEngine();
        } else {
            System.out.println(VehicleValidator.ERROR + result.getMessage());
        }
    }

    @Override
    public void stop() {
        StatusValidator result = VehicleValidator.tryEngineStop(engine.engineState);
        if (result == StatusValidator.ALLOWED) {
            System.out.println("Остановка ядерного реактора подводной лодки.");
            engine.stopEngine();
            resetSpeed();
        } else {
            System.out.println(VehicleValidator.ERROR + result.getMessage());
        }
    }

    @Override
    public void move() {
        StatusValidator result = VehicleValidator.tryMove(engine.engineState);
        if (result == StatusValidator.ALLOWED) {
            speedManager.randomizeSpeed();
            System.out.println(vehicleType.showTitle() + " начинает движение " +
                    (submersion == States.Submersion.SUBMERGED ? "под водой" : "на поверхности") +
                    " со скоростью " + getCurrentSpeed() + " км/ч.");
        } else {
            System.out.println(VehicleValidator.ERROR + result.getMessage());
        }
    }

    @Override
    public void left() {
        System.out.println(vehicleType.showTitle() + " маневрирует налево.");
    }

    @Override
    public void right() {
        System.out.println(vehicleType.showTitle() + " маневрирует направо.");
    }

    @Override
    public void brake() {
        StatusValidator result = VehicleValidator.tryBrake(engine.engineState);
        if (result == StatusValidator.ALLOWED) {
            int currentSpeed = getCurrentSpeed();
            System.out.println(vehicleType.showTitle() + " замедляется на скорости " + currentSpeed + " км/ч.");
            resetSpeed();
        } else {
            System.out.println(VehicleValidator.ERROR + result.getMessage());
        }
    }

    public void dive() {
        StatusValidator result = VehicleValidator.tryDive(submersion, engine.engineState);
        if (result == StatusValidator.ALLOWED) {
            int d = random.nextInt(200) + 50;
            submersion = States.Submersion.SUBMERGED;
            System.out.println(vehicleType.showTitle() + " погружается под воду на " + d + " метров.");
        } else {
            System.out.println(VehicleValidator.ERROR + result.getMessage());
        }
    }

    public void surface() {
        StatusValidator result = VehicleValidator.trySurface(submersion, engine.engineState);
        if (result == StatusValidator.ALLOWED) {
            int d = random.nextInt(200) + 50;
            submersion = States.Submersion.SURFACED;
            System.out.println(vehicleType.showTitle() + " всплывает на поверхность (подъем на " + d + " метров).");
        } else {
            System.out.println(VehicleValidator.ERROR + result.getMessage());
        }
    }

    public void hydrolocator() {
        StatusValidator result = VehicleValidator.tryHydrolocatorUse(engine.engineState);
        if (result == StatusValidator.ALLOWED) {
            hydrolocator = States.Sensor.ON;
            System.out.println("Гидролокатор подводной лодки включен.");
        } else {
            System.out.println(VehicleValidator.ERROR + result.getMessage());
        }
    }

    public void fire() {
        System.out.println(vehicleType.showTitle() + " выпускает торпеду.");
    }

    public void fillBallast() {
        StatusValidator result = VehicleValidator.tryFillBallast(ballast);
        if (result == StatusValidator.ALLOWED) {
            ballast = States.Ballast.FILLED;
            System.out.println("Балластные цистерны заполнены. Подготовка к погружению.");
        } else {
            System.out.println(VehicleValidator.ERROR + result.getMessage());
        }
    }

    public void emptyBallast() {
        StatusValidator result = VehicleValidator.tryEmptyBallast(ballast);
        if (result == StatusValidator.ALLOWED) {
            ballast = States.Ballast.EMPTIED;
            System.out.println("Балластные цистерны осушены. Подготовка к всплытию.");
        } else {
            System.out.println(VehicleValidator.ERROR + result.getMessage());
        }
    }

    @Override
    public void performAction(Action action) {
        switch (action) {
            case START -> start();
            case STOP -> stop();
            case MOVE -> move();
            case LEFT -> left();
            case RIGHT -> right();
            case BRAKE -> brake();
            case DIVE -> dive();
            case SURFACE -> surface();
            case HYDROLOCATOR -> hydrolocator();
            case FIRE -> fire();
            case FILL_BALAST -> fillBallast();
            case EMPTY_BALAST -> emptyBallast();
            default -> System.out.println("Действие '" + action.getDescription() + "' недоступно для подводной лодки.");
        }
    }

    @Override
    public List<Action> getAvailableActions() {
        return List.of(
                Action.START, Action.STOP, Action.MOVE, Action.LEFT, Action.RIGHT, Action.BRAKE,
                Action.DIVE, Action.SURFACE, Action.HYDROLOCATOR, Action.FIRE,
                Action.FILL_BALAST, Action.EMPTY_BALAST
        );
    }

    @Override
    public String detailedInfo() {
        return super.detailedInfo() +
                "Состояние: " + submersion.showTitle() + "\n" +
                "Гидролокатор: " + hydrolocator.showTitle() + "\n" +
                "Балласт: " + ballast.showTitle() + "\n";
    }
}