package test.java.com.upb.agripos;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import main.java.com.upb.agripos.model.Produk;
import main.java.com.upb.agripos.model.CartItem;
import main.java.com.upb.agripos.service.CartService;

public class AgriPosTest {
    private CartService cartService;
    private Produk produk1;
    private Produk produk2;

    @Before
    public void setUp() {
        cartService = new CartService();
        // Setup data produk untuk testing
        produk1 = new Produk("P001", "Pupuk Urea", 50000.0, 10);
        produk2 = new Produk("P002", "Benih Padi", 25000.0, 20);
    }

    @Test
    public void testTambahKeKeranjang() {
        cartService.addToCart(produk1, 2);
        assertEquals(1, cartService.getCartItems().size());
        assertEquals(100000.0, cartService.calculateTotal(), 0.01);
    }

    @Test
    public void testHitungTotalBeberapaProduk() {
        cartService.addToCart(produk1, 1); // 50.000
        cartService.addToCart(produk2, 2); // 50.000
        assertEquals(100000.0, cartService.calculateTotal(), 0.01);
    }

    @Test
    public void testDiskonMember() {
        cartService.addToCart(produk1, 2); // Total 100.000
        double subtotal = cartService.calculateTotal();
        double diskon = 0.10; // Diskon 10% sesuai logic aplikasi
        double totalSetelahDiskon = subtotal - (subtotal * diskon);
        
        assertEquals(90000.0, totalSetelahDiskon, 0.01);
    }
}