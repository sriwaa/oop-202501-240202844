package com.upb.agripos;

import java.util.function.Supplier;

public class HelloFunctional {
    public static void main(String[] args) {
        // Paradigma fungsional: gunakan lambda expression
        Supplier<String> hello = () -> "Hello World, I am sriwaa-240202844";

        System.out.println(hello.get());
    }
}

