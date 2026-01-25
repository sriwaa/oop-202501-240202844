package main.java.com.upb.agripos.service;

import main.java.com.upb.agripos.model.Cart;
import main.java.com.upb.agripos.model.CartItem;
import main.java.com.upb.agripos.model.Produk;
import java.util.List;

public class CartService {
    // Service sekarang mengelola objek Cart
    private Cart cart;

    public CartService() {
        this.cart = new Cart();
    }

    public void addToCart(Produk p, int qty) {
        // Validasi stok bisa ditambahkan di sini jika perlu
        if (p.getStok() < qty) {
            throw new IllegalArgumentException("Stok tidak cukup!");
        }
        
        // Buat item baru dan masukkan ke Cart
        CartItem item = new CartItem(p, qty);
        cart.addItem(item);
    }

    public void clearCart() {
        cart.clear();
    }

    public List<CartItem> getCartItems() {
        return cart.getItems();
    }

    public double calculateTotal() {
        return cart.calculateTotal();
    }
}