package main.java.com.upb.agripos.model;

public class CartItem {
    private Produk produk;
    private int qty;

    public CartItem(Produk produk, int qty) {
        this.produk = produk;
        this.qty = qty;
    }

    // Getter Produk (Penting untuk CartService)
    public Produk getProduk() { return produk; }
    
    // Getter & Setter Qty
    public int getQty() { return qty; }
    public void setQty(int qty) { this.qty = qty; }

    // Hitung Subtotal (Harga x Qty)
    public double getSubtotal() {
        return produk.getHarga() * qty;
    }

    @Override
    public String toString() {
        // Format tampilan di List Keranjang
        return produk.getNama() + " (x" + qty + ") - Rp " + String.format("%,.0f", getSubtotal());
    }

    // --- PERBAIKAN DI SINI ---
    // Method ini berfungsi untuk menambah jumlah barang jika barang yang sama dipilih lagi
    public void addQty(int tambahan) {
        this.qty += tambahan; // Artinya: qty sekarang = qty lama + tambahan
    }
}