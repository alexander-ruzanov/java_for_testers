package ru.stqa.geometry.figures;

import static java.lang.Math.sqrt;

public record Triangle(double a, double b, double c) {

    public static void printTriangleArea(Triangle t) {
        String text = String.format("Площадь треугольника со сторонами %f, %f, %f = %f", t.a, t.b, t.c, t.area());
        System.out.println(text);
    }
    public static void printTrianglePerimeter(Triangle t) {
        String text = String.format("Периметр треугольника со сторонами %f, %f, %f = %f", t.a, t.b, t.c, t.perimeter());
        System.out.println(text);
    }

    private double area() {
        double semiperimeter;
        semiperimeter = (a+b+c)/2;
        return sqrt((semiperimeter-a)*(semiperimeter-b)*(semiperimeter-c)*semiperimeter);
    }
    public double perimeter() {
        return a + b + c;
    }
}