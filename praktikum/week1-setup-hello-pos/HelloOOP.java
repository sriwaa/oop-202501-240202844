package com.upb.agripos;

// Paradigma OOP: buat class dan objek
class Person {
    String nama;
    String nim;

    Person(String nama, String nim) {
        this.nama = nama;
        this.nim = nim;
    }

    void sayHello() {
        System.out.println("Hello World, I am " + nama + "-" + nim);
    }
}

public class HelloOOP {
    public static void main(String[] args) {
        Person p = new Person("sriwaa", "240202844");
        p.sayHello();
    }
}
