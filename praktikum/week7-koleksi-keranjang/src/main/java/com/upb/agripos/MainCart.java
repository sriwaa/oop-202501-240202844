package com.upb.agripos;

public class MainCart {
    public static void main(String[] args) {

        System.out.println("Hello, I am Sriwaa Nim:240202844 (Week7)" );

        Product p1 = new Product("WWA-002", "Benih Strobewryy AW55", 80000);
        Product p2 = new Product("SSR-005", "Pupuk Hayati 25kg", 90000);
        Product p3 = new Product("SRW-025", "Sekop Tangan", 50000);
        Product p4 = new Product("AWA-555", "Obat Hama Ulat Grayak", 60000);

        ShoppingCart cart = new ShoppingCart();

        cart.addProduct(p1);
        cart.addProduct(p2);
        cart.addProduct(p3);
        cart.addProduct(p4);

        cart.printCart();

        System.out.println("\nMenghapus produk Sekop Tangan...\n");
        cart.removeProduct(p3);

        cart.printCart();
    }
}
