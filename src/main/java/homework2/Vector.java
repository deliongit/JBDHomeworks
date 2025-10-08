package main.java.homework2;

import java.util.Random;

public class Vector {
    private final double x;
    private final double y;
    private final double z;

    // Параметры
    public Vector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    // Геттеры
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    // Вычисление длины вектора
    public double length() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    // Вычисление скалярного произведения
    public double dotProduct(Vector other) {
        return this.x * other.x + this.y * other.y + this.z * other.z;
    }

    // Вычисление векторного произведения
    public Vector crossProduct(Vector other) {
        double newX = this.y * other.z - this.z * other.y;
        double newY = this.z * other.x - this.x * other.z;
        double newZ = this.x * other.y - this.y * other.x;
        return new Vector(newX, newY, newZ);
    }

    // Вычисление угла между векторами
    public double angle(Vector other) {
        double dot = this.dotProduct(other);
        double lengths = this.length() * other.length();
        if (lengths == 0) {
            throw new IllegalArgumentException("Нельзя вычислить угол с нулевым вектором");
        }
        double cosAngle = dot / lengths;
        // Ограничиваем значение [-1, 1] для избежания ошибок округления
        cosAngle = Math.max(-1, Math.min(1, cosAngle));
        return Math.acos(cosAngle);
    }

    // Вычисление косинуса угла между векторами
    public double cosineAngle(Vector other) {
        double dot = this.dotProduct(other);
        double lengths = this.length() * other.length();
        if (lengths == 0) {
            throw new IllegalArgumentException("Нельзя вычислить угол с нулевым вектором!");
        }
        return dot / lengths;
    }

    // Сумма векторов
    public Vector add(Vector other) {
        return new Vector(this.x + other.x, this.y + other.y, this.z + other.z);
    }

    // Разность векторов
    public Vector difference(Vector other) {
        return new Vector(this.x - other.x, this.y - other.y, this.z - other.z);
    }

    // Возврат массива случайных векторов
    public static Vector[] randomizationVectors(int n) {
        Random random = new Random();
        Vector[] vectors = new Vector[n];

        for (int i = 0; i < n; i++) {
            double x = random.nextDouble() * 20 - 10; // случайное число от -10 до 10
            double y = random.nextDouble() * 20 - 10;
            double z = random.nextDouble() * 20 - 10;
            vectors[i] = new Vector(x, y, z);
        }

        return vectors;
    }

    // Формат вывода
    @Override
    public String toString() {
        return String.format("%.2f, %.2f, %.2f", x, y, z);
    }

    // Сравнение векторов
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Vector vector3D = (Vector) obj;
        return Double.compare(vector3D.x, x) == 0 &&
                Double.compare(vector3D.y, y) == 0 &&
                Double.compare(vector3D.z, z) == 0;
    }

    // Переопределение метода hashCode
    @Override
    public int hashCode() {
        return java.util.Objects.hash(x, y, z);
    }

     public static void main(String[] args) {
        // Создание векторов
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(4, 5, 6);

        System.out.println("Вектор 1: " + v1);
        System.out.println("Вектор 2: " + v2);

        // Длина векторов
        System.out.println("Длина вектора 1: " + v1.length());
        System.out.println("Длина вектора 2: " + v2.length());

        // Скалярное произведение
        System.out.println("Скалярное произведение: " + v1.dotProduct(v2));

        // Векторное произведение
        Vector cross = v1.crossProduct(v2);
        System.out.println("Векторное произведение: " + cross);

        // Угол между векторами
        System.out.println("Угол между векторами (радианы): " + v1.angle(v2));
        System.out.println("Косинус угла: " + v1.cosineAngle(v2));

        // Сумма и разность
        System.out.println("Сумма векторов: " + v1.add(v2));
        System.out.println("Разность векторов: " + v1.difference(v2));

        // Генерация случайных векторов
        System.out.println("\nСлучайные векторы:");
        Vector[] randomVectors = randomizationVectors(3);
        for (int i = 0; i < randomVectors.length; i++) {
            System.out.println("Вектор " + (i + 1) + ": " + randomVectors[i]);
        }
    }
}