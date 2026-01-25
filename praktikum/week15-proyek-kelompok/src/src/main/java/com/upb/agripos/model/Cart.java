package main.java.com.upb.agripos.model;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<CartItem> items;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void addItem(CartItem newItem) {
        // Cek apakah produk sudah ada di keranjang?
        for (CartItem item : items) {
            if (item.getProduk().getKode().equals(newItem.getProduk().getKode())) {
                item.addQty(newItem.getQty()); // Jika ada, update jumlahnya
                return;
            }
        }
        // Jika belum ada, tambahkan baru
        items.add(newItem);
    }

    public void removeItem(CartItem item) {
        items.remove(item);
    }

    public void clear() {
        items.clear();
    }

    public double calculateTotal() {
        return items.stream().mapToDouble(CartItem::getSubtotal).sum();
    }
}