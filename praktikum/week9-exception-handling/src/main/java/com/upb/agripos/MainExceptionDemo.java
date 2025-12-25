package com.upb.agripos;

public class MainExceptionDemo {

    public static void main(String[] args) {

        System.out.println("Hello, I am Sriwa-240202844 (Week9)");

        ShoppingCart cart = new ShoppingCart();

        Product sekop = new Product(
                "SRW-025",
                "Sekop Tangan",
                50000,
                5   
        );

        // Uji quantity tidak valid
        try {
            cart.addProduct(sekop, -1);
        } catch (InvalidQuantityException e) {
            System.out.println("Kesalahan: " + e.getMessage());
        } finally {
            System.out.println("Validasi quantity selesai.\n");
        }

        // Uji hapus produk yang tidak ada di keranjang
        try {
            cart.removeProduct(sekop);
        } catch (ProductNotFoundException e) {
            System.out.println("Kesalahan: " + e.getMessage());
        }

        // Uji stok tidak cukup saat checkout
        try {
            cart.addProduct(sekop, 10); // lebih besar dari stok (5)
            cart.checkout();
        } catch (InvalidQuantityException | InsufficientStockException e) {
            System.out.println("Kesalahan: " + e.getMessage());
        }

        System.out.println("\nProgram selesai.");
    }
}
