package com.upb.agripos;

import java.util.ArrayList;

public class ShoppingCart {

    private final ArrayList<Product> items = new ArrayList<>();

    // tambah produk ke keranjang
    public void addProduct(Product p) {
        items.add(p);
    }

    // hapus produk dari keranjang
    public void removeProduct(Product p) {
        items.remove(p);
    }

    // hitung total harga
    public double getTotal() {
        double sum = 0;
        for (Product p : items) {
            sum += p.getPrice();
        }
        return sum;
    }

    // tampilkan isi keranjang
    public void printCart() {
        System.out.println("Isi Keranjang:");
        for (Product p : items) {
            System.out.println("- " + p.getCode() + " "
                    + p.getName() + " = " + p.getPrice());
        }
        System.out.println("Total: " + getTotal());
    }
}
