package com.upb.agripos;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCartMap {

    private final Map<Product, Integer> items = new HashMap<>();

    // tambah produk (qty +1)
    public void addProduct(Product p) {
        items.put(p, items.getOrDefault(p, 0) + 1);
    }

    // hapus produk (qty -1)
    public void removeProduct(Product p) {
        if (!items.containsKey(p)) return;

        int qty = items.get(p);
        if (qty > 1) {
            items.put(p, qty - 1);
        } else {
            items.remove(p);
        }
    }

    // hitung total harga
    public double getTotal() {
        double total = 0;
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            total += entry.getKey().getPrice() * entry.getValue();
        }
        return total;
    }

    // tampilkan isi keranjang
    public void printCart() {
        System.out.println("Isi Keranjang (Map):");
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            Product p = entry.getKey();
            int qty = entry.getValue();
            System.out.println(
                "- " + p.getCode() + " | " +
                p.getName() + " x" + qty +
                " | Rp" + (p.getPrice() * qty)
            );
        }
        System.out.println("Total: Rp" + getTotal());
    }
}
