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

    public double area() {
        return sqrt((perimeter()/2-a)*(perimeter()/2-b)*(perimeter()/2-c)*perimeter()/2);
    }
    public double perimeter() {
        return a + b + c;
    }
}