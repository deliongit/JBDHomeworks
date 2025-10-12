package main.java.homework2;

import java.util.Random;

interface Vector {
    double length();
    double dotProduct(Vector other);
    Vector add(Vector other);
    Vector difference(Vector other);
    @Override
    boolean equals(Object obj);
    @Override
    int hashCode();
}

class Vector3D implements Vector {
    private final double x;
    private final double y;
    private final double z;

    // Параметры
    public Vector3D(double x, double y, double z) {
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
    @Override
    public double length() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    // Вычисление скалярного произведения
    @Override
    public double dotProduct(Vector other) {
        if (!(other instanceof Vector3D)) {
            throw new IllegalArgumentException("Операция поддерживается только для Vector3D");
        }
        Vector3D v = (Vector3D) other;
        return this.x * v.x + this.y * v.y + this.z * v.z;
    }

    // Вычисление векторного произведения
    public Vector3D crossProduct(Vector3D other) {
        double newX = this.y * other.z - this.z * other.y;
        double newY = this.z * other.x - this.x * other.z;
        double newZ = this.x * other.y - this.y * other.x;
        return new Vector3D(newX, newY, newZ);
    }

    // Вычисление угла между векторами
    public double angle(Vector3D other) {
        double dot = this.dotProduct(other);
        double lengths = this.length() * other.length();
        if (lengths == 0) {
            throw new IllegalArgumentException("Нельзя вычислить угол с нулевым вектором");
        }
        double cosAngle = dot / lengths;
        cosAngle = Math.max(-1, Math.min(1, cosAngle));
        return Math.acos(cosAngle);
    }

    // Вычисление косинуса угла между векторами
    public double cosineAngle(Vector3D other) {
        double dot = this.dotProduct(other);
        double lengths = this.length() * other.length();
        if (lengths == 0) {
            throw new IllegalArgumentException("Нельзя вычислить угол с нулевым вектором!");
        }
        return dot / lengths;
    }

    // Сумма векторов
    @Override
    public Vector3D add(Vector other) {
        if (!(other instanceof Vector3D)) {
            throw new IllegalArgumentException("Операция поддерживается только для Vector3D");
        }
        Vector3D v = (Vector3D) other;
        return new Vector3D(this.x + v.x, this.y + v.y, this.z + v.z);
    }

    // Разность векторов
    @Override
    public Vector3D difference(Vector other) {
        if (!(other instanceof Vector3D)) {
            throw new IllegalArgumentException("Операция поддерживается только для Vector3D");
        }
        Vector3D v = (Vector3D) other;
        return new Vector3D(this.x - v.x, this.y - v.y, this.z - v.z);
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
        Vector3D vector3D = (Vector3D) obj;
        return Double.compare(vector3D.x, x) == 0 &&
                Double.compare(vector3D.y, y) == 0 &&
                Double.compare(vector3D.z, z) == 0;
    }

    // Переопределение метода hashCode
    @Override
    public int hashCode() {
        return java.util.Objects.hash(x, y, z);
    }
}

// Утилиты для векторов
class VectorUtils {

    public static Vector3D[] generateRandomVectors(int n, Random random) {
        Vector3D[] vectors = new Vector3D[n];

        for (int i = 0; i < n; i++) {
            double x = random.nextDouble() * 20 - 10; // случайное число от -10 до 10
            double y = random.nextDouble() * 20 - 10;
            double z = random.nextDouble() * 20 - 10;
            vectors[i] = new Vector3D(x, y, z);
        }

        return vectors;
    }

    public static Vector3D[] generateRandomVectors(int n) {
        return generateRandomVectors(n, new Random());
    }
}

class VectorDemo {
    public static void main(String[] args) {
        // Создание векторов
        Vector3D v1 = new Vector3D(1, 2, 3);
        Vector3D v2 = new Vector3D(4, 5, 6);

        System.out.println("Вектор 1: " + v1);
        System.out.println("Вектор 2: " + v2);

        // Длина векторов
        System.out.println("Длина вектора 1: " + v1.length());
        System.out.println("Длина вектора 2: " + v2.length());

        // Скалярное произведение
        System.out.println("Скалярное произведение: " + v1.dotProduct(v2));

        // Векторное произведение
        Vector3D cross = v1.crossProduct(v2);
        System.out.println("Векторное произведение: " + cross);

        // Угол между векторами
        System.out.println("Угол между векторами (радианы): " + v1.angle(v2));
        System.out.println("Косинус угла: " + v1.cosineAngle(v2));

        // Сумма и разность
        System.out.println("Сумма векторов: " + v1.add(v2));
        System.out.println("Разность векторов: " + v1.difference(v2));

        // Генерация случайных векторов
        System.out.println("\nСлучайные векторы:");
        Vector3D[] randomVectors = VectorUtils.generateRandomVectors(3);
        for (int i = 0; i < randomVectors.length; i++) {
            System.out.println("Вектор " + (i + 1) + ": " + randomVectors[i]);
        }
    }
}