package main.java.com.upb.agripos.service;

import main.java.com.upb.agripos.dao.ProductRepository;
import main.java.com.upb.agripos.dao.SqlProductRepository;
import main.java.com.upb.agripos.model.CartItem;
import main.java.com.upb.agripos.model.Produk;
import java.util.List;

public class ProductService {
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public void addProduct(Produk p) throws Exception {
        if (repository.findByCode(p.getKode()) != null) throw new Exception("Kode sudah ada!");
        repository.save(p);
    }

    public void updateProduct(Produk p) throws Exception {
        if (repository.findByCode(p.getKode()) == null) throw new Exception("Produk tidak ditemukan!");
        repository.update(p);
    }

    public void deleteProduct(String code) throws Exception {
        repository.delete(code);
    }

    public List<Produk> getAllProducts() throws Exception {
        return repository.findAll();
    }

    // Support method lama
    public void addProduct(String k, String n, double h, int s) throws Exception {
        addProduct(new Produk(k, n, h, s));
    }

    // --- FITUR PENTING: KURANGI STOK (WAJIB ADA) ---
    public void processStockReduction(List<CartItem> items) {
        // Cek apakah repository mendukung pengurangan stok
        if (repository instanceof SqlProductRepository) {
            SqlProductRepository sqlRepo = (SqlProductRepository) repository;
            for (CartItem item : items) {
                try {
                    sqlRepo.reduceStock(item.getProduk().getKode(), item.getQty());
                } catch (Exception e) {
                    System.err.println("Gagal update stok: " + e.getMessage());
                }
            }
        }
    }
}